package tianyuan.rbac.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import tianyuan.common.enums.NormalStatusEnum;
import tianyuan.rbac.dto.SystemUserDto;
import tianyuan.rbac.service.SystemCoreService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018-04-13 10:54.
 * @Describution:
 */
@Data
@NoArgsConstructor
public final class  JwtUserFactory {

    @Resource
    private SystemCoreService coreService;

    public static JwtUser create(SystemUserDto user){
        //处理一下 过期 锁定等boolean
        Boolean nonExpired = user.getUserStatus() == NormalStatusEnum.NON_ACTIVE.getCode();
        Boolean nonLocked = user.getUserStatus() == NormalStatusEnum.LOCKED.getCode();
        Boolean enabled = !(user.getUserStatus() == NormalStatusEnum.NON_ENABLED.getCode());
//        // 取到权限列表
        List<String> aclList = user.getUserAcl();
        return JwtUser.builder()
                .id(user.getUserId())
                .password(user.getUserPassword())
//                .phone(user.getUserPhone())
//                .email(user.getUserEmail())
                .enabled(enabled)
                .accountNonExpired(nonExpired)
                .accountNonLocked(nonLocked)
                .credentialsNonExpired(true)
                .authorities(mapToGrantedAuthorities(aclList))
                .build();

    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
