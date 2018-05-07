package tianyuan.rbac.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tianyuan.rbac.dto.SystemUserDto;
import tianyuan.rbac.service.SystemCoreService;
import tianyuan.rbac.service.SystemUserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018-04-16 11:17.
 * @Describution:
 */
@Service
@Slf4j
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SystemUserService userService;

    @Resource
    private SystemCoreService coreService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        SystemUser user = userService.findOne(s);
//        log.info("验证并获取权限");
//        List<String> aclList = coreService.listAclListByUserId(user.getUserId()).stream()
//                .map(acl -> acl.getAclUrl()).collect(Collectors.toList());
//
        SystemUserDto userDto = SystemUserDto.builder().build();
//        BeanUtils.copyProperties(user,userDto);
        userDto.setUserName("1");
        userDto.setUserPassword("1");
        List<String> aclList = new ArrayList<>();
        aclList.add("user");
        userDto.setUserStatus(1);

        userDto.setUserAcl(aclList);
//        userDto.builder().userAcl(aclList).build();
        return JwtUserFactory.create(userDto);
    }
}
