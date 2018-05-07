package tianyuan.rbac.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @Author: Created by Administrator
 * @Date: Create on 2017/12/20 11:26.
 * @Describution: 安全配置类
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;

//    @Resource
//    private TianyuanAuthenticationSuccessHandler tianyuanAuthenticationSuccessHandler;
//    @Resource
//    private TianyuanAuthenticationFailureHandler tianyuanAuthenticationFailureHandler;

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() {
        return new JwtAuthenticationTokenFilter();
    }
//    @Resource
//    private DataSource dataSource;
//    @Bean
//    public PersistentTokenRepository persistentTokenRepository(){
//        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
//        tokenRepository.setDataSource(dataSource);
//        return tokenRepository;
//    }

    @Override//主要的配置
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic()
                .disable()
                .authorizeRequests()
////                .antMatchers(HttpMethod.GET,"/","/acm","/*.html","/favicon.ico","/**/*.css",
////                        "/**/*.js")
////                .permitAll()
//                .antMatchers(HttpMethod.POST,"/sys/user/*").permitAll()
//
//                .antMatchers("/sys/user")
//                .permitAll()
//                .antMatchers("/acm/sys/user/**")
//                .permitAll()
                .anyRequest().permitAll()

//                .and()
//                .loginPage("")
//                .loginProcessingUrl("")
//                .successHandler(tianyuanAuthenticationSuccessHandler)
//                .failureHandler(tianyuanAuthenticationFailureHandler)
;

       http.addFilterBefore(authenticationTokenFilterBean(),UsernamePasswordAuthenticationFilter.class);
  http.headers().cacheControl();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("1").password("1").roles("USER");
    }
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder
                                                    authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean //密码机制，相当安全
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
