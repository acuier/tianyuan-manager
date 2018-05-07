package tianyuan.rbac.jwt;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tianyuan.common.enums.ResultEnum;
import tianyuan.common.exception.ResponseStatusException;
import tianyuan.rbac.service.SystemUserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Created by Administrator
 * @Date: Create on 2017/12/20 11:32.
 * @Describution:
 */
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailsServiceImpl userDetailsService;

    @Autowired
    private SystemUserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.header}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
    String authHeader = httpServletRequest.getHeader(this.tokenHeader);
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            final String authToken = authHeader.substring(tokenHead.length()); // The part after "Bearer "
            if (!jwtTokenUtil.validateToken(authToken)) {
                throw new ResponseStatusException(ResultEnum.TOKEN_IS_VALID);
                //应跳转至login
            }

            String username = jwtTokenUtil.getUsernameFromToken(authToken);

            log.info("checking authentication " + username);
            //如果是管理员，则从数据库中读取管理员权限。否则，验证是否需要刷新token？
            if (username != null ) {

                //todo: 查询缓存redis，确定是否查询数据库
                //1、更新token，主要更新过期时间，也就是创建时间，好的用户体验

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    //2、缓存中获取权限
                }
                // 缓存不存在，则，我们还是通过Spring Security的 @UserDetailsService 进行了数据查询

                //删除token，重新登录，给出提示
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                //生成token，放入缓存
                UsernamePasswordAuthenticationToken authentication = new
                        UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                            httpServletRequest));

                log.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
