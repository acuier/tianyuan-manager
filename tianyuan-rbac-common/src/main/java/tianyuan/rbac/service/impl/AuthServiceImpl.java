package tianyuan.rbac.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tianyuan.rbac.jwt.JwtTokenUtil;
import tianyuan.rbac.jwt.JwtUser;
import tianyuan.rbac.jwt.JwtUserDetailsServiceImpl;
import tianyuan.rbac.service.AuthService;

import javax.annotation.Resource;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018-04-23 16:52.
 * @Describution:
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

//    @Autowired
//    private SystemUserService userService;

    @Autowired
    private JwtUserDetailsServiceImpl userDetailsService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public String getToken(String userName, String password) {
        UsernamePasswordAuthenticationToken upToken =new UsernamePasswordAuthenticationToken
                (userName,password);
        final Authentication authentication = authenticationManager.authenticate(upToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(userName);
        return  jwtTokenUtil.generateToken(user);
    }

    @Override
    public String refreshToken(String token) {
        final String newToken = token.substring(tokenHead.length());
        String userName = jwtTokenUtil.getUsernameFromToken(newToken);
        if (jwtTokenUtil.canTokenBeRefreshed(newToken)){
            //刷新token，并返回
            return newToken;
        } else {
            return newToken;
        }
    }
}
