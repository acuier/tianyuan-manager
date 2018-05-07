package tianyuan.rbac.jwt;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018-04-13 10:46.
 * @Describution:
 */
@Data
@Builder
public class JwtUser implements UserDetails {

    private final Integer id;
    private final String username;
    private final String password;
//    private final String email;
//    private final String phone;
    private final Collection<GrantedAuthority> authorities;

    private final Boolean accountNonExpired;
    private final Boolean accountNonLocked;
    private final Boolean credentialsNonExpired;
    private final Boolean enabled;

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override //用户过期，适用于开放试用期的应用
    public boolean isAccountNonExpired() {
        return accountNonExpired ;
    }
    @Override //冻结
    public boolean isAccountNonLocked() {
        return accountNonLocked ;
    }

    @Override//密码是否没有过期，适用于要求定期修改密码的安全机制，如不需要返回 true
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired ;
    }

    @Override //删除标记
    public boolean isEnabled() {
        return enabled;
    }
}
