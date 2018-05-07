package tianyuan.rbac.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tianyuan.common.enums.ResultEnum;
import tianyuan.common.exception.ResponseStatusException;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018-04-16 14:22.
 * @Describution:
 */
@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = 5448538569298302617L;

    private static final String CLAIM_KEY_USERNAME = "username";
    private static final String CLAIM_KEY_CREATED = "created";
//    private static final String CLAIM_KEY_EMAIL = "email";
//    private static final String CLAIM_KEY_PHONE = "phone";
    private static final String CLAIM_KEY_ID = "id";
    private static final String CLAIM_NON_LOCKED = "locked";
    private static final String CLAIM_NON_ENABLED = "enabled";
    private static final String CLAIM_ROLES = "roles";
    private static final String CLAIM_KEY_REFRESH = "refresh";
    @Value("${jwt.secret}")
    private  String secret;

    @Value("${jwt.expiration}")
    private  Long expiration;

    @Value("${jwt.refresh}")
    private  Long refresh;

    /**
     * 生成token
     * @param user
     * @return String token
     */
    public String generateToken(JwtUser user){
        Map<String,Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,user.getUsername());
        claims.put(CLAIM_KEY_ID,user.getId());
        claims.put(CLAIM_KEY_CREATED,new Date());
        claims.put(CLAIM_KEY_REFRESH,generateRefreshDate());
//        claims.put(CLAIM_KEY_EMAIL,user.getEmail());
//        claims.put(CLAIM_KEY_PHONE,user.getPhone());
        claims.put(CLAIM_NON_LOCKED,user.isAccountNonLocked());
        claims.put(CLAIM_NON_ENABLED,user.isEnabled());
        claims.put(CLAIM_ROLES,user.getAuthorities());
        return generateToken(claims);
    }
    private String generateToken(Map<String,Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(io.jsonwebtoken.SignatureAlgorithm.ES256,secret)
                .compact();
    }

    private Claims getClaimsFromToken(String token){
            Claims claims;
            try {
                claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            }catch (Exception e){
                throw new ResponseStatusException(ResultEnum.TOKEN_IS_VALID);
            }
            return claims;
        }
    /**获取用户名*/
    public String getUsernameFromToken(String token){

        final Claims claims = getClaimsFromToken(token);
        String   username = (String)claims.get(CLAIM_KEY_USERNAME);
        if(username == null || username.isEmpty()){
            throw new ResponseStatusException(ResultEnum.USER_NOT_EXIST);
        }

        return username;
    }
    /**获取用户Id*/
    public String getUserIdFromToken(String token){

        final Claims claims = getClaimsFromToken(token);
        String   userId = (String)claims.get(CLAIM_KEY_ID);
        if(userId == null || userId.isEmpty()){
            throw new ResponseStatusException(ResultEnum.USER_NOT_EXIST);
        }

        return userId;
    }
//    /**获取邮件*/
//    public String getClaimKeyEmailFromToken(String token){
//        String email;
//        final Claims claims = getClaimsFromToken(token);
//        email = (String)claims.get(CLAIM_KEY_EMAIL);
//        return email;
//    }
//    /**获取电话*/
//    public String getClaimKeyPhone(String token){
//        String phone;
//        final Claims claims = getClaimsFromToken(token);
//        phone = (String)claims.get(CLAIM_KEY_PHONE);
//
//        return phone;
//    }

    /**
     * 检查是否有效
     * @param token
     * @return
     */
    public Boolean isTokenEnabled(String token){
        Boolean isTokenEnabled = false;
        try {
            final Claims claims = getClaimsFromToken(token);
            isTokenEnabled = (Boolean)claims.get(CLAIM_NON_ENABLED);

        } catch (Exception e) {
            throw new ResponseStatusException(ResultEnum.TOKEN_IS_VALID);
        }
        return isTokenEnabled;
    }

    /**
     * 获取创建时间
     * @param token
     * @return
     */
    public Date getCreatedDateFromToken(String token){
        Date createdDate;
        try {
            final Claims claims = getClaimsFromToken(token);
            createdDate = new Date((Long)claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            throw new ResponseStatusException(ResultEnum.TOKEN_IS_VALID);
        }
        return createdDate;
    }

    /**
     * 获取过期时间
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            throw new ResponseStatusException(ResultEnum.TOKEN_IS_VALID);
        }
        return expiration;
    }
    /**
     * 获取shuaxin时间
     * @param token
     * @return
     */
    public Date getRefreshDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = (Date) claims.get(CLAIM_KEY_REFRESH);
        } catch (Exception e) {
            throw new ResponseStatusException(ResultEnum.TOKEN_IS_VALID);
        }
        return expiration;
    }
    /**
     * 计算过期时间
     * @return
     */
    private  Date generateExpirationDate(){
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }
    /**
     * 计算shuaxin时间
     * @return
     */
    private  Date generateRefreshDate(){
        return new Date(System.currentTimeMillis() + refresh * 1000);
    }
    /**
     * 计算是否过期
     * @param token
     * @return
     */
    private Boolean isNonTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean canTokenBeRefreshed(String token) {
        final Date refreshed = getRefreshDateFromToken(token);
        if (isNonTokenExpired(token)){
          return false;
        }
        return refreshed.after(new Date()) ;
    }
    /**
     * 验证是否有效
     * @param token
     * @return
     */
   public Boolean validateToken(String token){
        if (token != null){
            final String userName = getUsernameFromToken(token);
            final Boolean nonEnabled = !isTokenEnabled(token);
            final Boolean nonExpired = isNonTokenExpired(token);
            return (null != userName) && nonEnabled && nonExpired ;
        }else{
            throw new ResponseStatusException(ResultEnum.TOKEN_IS_VALID);
        }

    }
}

