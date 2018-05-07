package tianyuan.rbac.service.impl;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tianyuan.common.enums.ResultEnum;
import tianyuan.common.exception.ParamException;
import tianyuan.rbac.model.admin.SystemRole;
import tianyuan.rbac.model.admin.SystemUser;
import tianyuan.common.utils.BeanValidator;
import tianyuan.rbac.dao.SystemRoleDao;
import tianyuan.rbac.param.RoleParam;
import tianyuan.rbac.service.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/31 10:01.
 * @Describution:
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "role")//,keyGenerator = "wiselyKeyGenerator")
public class SystemRoleServiceImpl implements SystemRoleService {

    @Autowired
    private SystemRoleDao roleAdo;

    @Autowired
    private SystemRoleAclService roleAclService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private SystemUserService userService;

    @Autowired
    private SystemLogService logService;

    @Override
    @CacheEvict(allEntries = true)
    public void save(RoleParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getRoleName(),param.getRoleId())){
            log.error("【创建角色信息】参数不正确, param={}", param);
            throw new ParamException(ResultEnum.DUPLICATE_ROLE_NAME);
        }
        SystemRole role = SystemRole.builder().build();
        BeanUtils.copyProperties(param,role);
        //填写操作员信息
        role.setOperator("1");//todo:
        role.setOperatorIp("1212");
        //保存
        roleAdo.save(role);

        logService.saveRoleLog(null,role);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void update(RoleParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getRoleName(),param.getRoleId())){
            log.error("【更新角色信息】参数不正确, param={}", param);
            throw new ParamException(ResultEnum.DUPLICATE_ROLE_NAME);
        }

//        Optional<SystemRole> oldRole = roleAdo.findById(param.getRoleId());
//        if (!oldRole.isPresent()){
//                log.error("【更新角色信息】参数不正确, param={}", param);
//                throw new ParamException(ResultEnum.ROLE_NOT_EXIST);
//        }
        SystemRole oldRole = roleAdo.findById(param.getRoleId())
                .orElseThrow(() -> new ParamException(ResultEnum.DUPLICATE_USER_EMAIL));
        SystemRole newRole = SystemRole.builder().build();
        BeanUtils.copyProperties(param,newRole);
        //填写操作员信息
        newRole.setOperator("1");//todo:
        newRole.setOperatorIp("1212");
        //保存

       roleAdo.save(newRole);

       logService.saveRoleLog(oldRole,newRole);

    }

    @Override
    public   boolean checkExist(String roleName,Integer roleId){
        if (roleId == null){roleId = -1;}
        return roleAdo.countByRoleNameAndRoleIdNot(roleName,roleId)>0;
    }

    @Override
    @Cacheable
    public List<SystemRole> listAll() {
        return roleAdo.findAll();
    }

    /**
     * 角色详情
     * @param roleId
     * @return
     */
    @Override
    public SystemRole getByID(Integer roleId) {
         Optional<SystemRole> roles = roleAdo.findById(roleId);
        return  roles.orElse(null);

    }

    /**
     * 根据用户获取角色信息列表
     * @param userId
     * @return
     */
    @Override
    @Cacheable
    public List<SystemRole> listAllByUserId(Integer userId) {
        List<Integer> roleIdList = userRoleService.listRoleIdByUserId(userId) ;
        if (CollectionUtils.isEmpty(roleIdList)){
            return Lists.newArrayList();
        }

        return roleAdo.findAllById(roleIdList);
    }

    /**
     * 权限获取角色列表
     * @param aclId
     * @return
     */
    @Override
    public List<SystemRole> listAllByAclId(Integer aclId) {
        List<Integer> roleIdList = roleAclService.listRoleIdByAclId(aclId);
        if (CollectionUtils.isEmpty(roleIdList)){
            return Lists.newArrayList();
        }
        return roleAdo.findAllById(roleIdList);
    }

    /**
     * 角色列表获取拥用户信息列表
     * @param roleList
     * @return
     */
    @Override
    public List<SystemUser> listUserListByRoleList(List<Integer> roleList) {
        if (CollectionUtils.isEmpty(roleList)){
            return Lists.newArrayList();
        }

        List<Integer> userIdList =  userRoleService.listUserIdListByRoleIdList(roleList);
        if (CollectionUtils.isEmpty(userIdList)){
            return Lists.newArrayList();
        }
        List<SystemUser> userList = userService.listAllByUserIdIn(userIdList);
        if (CollectionUtils.isEmpty(userList)){
            return Lists.newArrayList();
        }

        return userList;
    }

    /**
     * 通过用户获取角色Id列表
     * @param userId
     * @return
     */
    @Override
    public List<Integer> listRoleIdByUserId(Integer userId) {
        return userRoleService.listRoleIdByUserId(userId);
    }

    /**
     * 通过权限获取角色Id列表
     * @param aclId
     * @return
     */
    @Override
    public List<Integer> listRoleIdByAclId(Integer aclId) {
        return roleAclService.listRoleIdByAclId(aclId);
    }

    /**
     * 获取角色Id列表
     * @param roleList
     * @return
     */
    @Override
    public List<Integer> listRoleIdByRoleList(List<Integer> roleList) {
        if (CollectionUtils.isEmpty(roleList)){
            return Lists.newArrayList();
        }
        List<SystemRole> roles = roleAdo.findAllById(roleList);
        if (CollectionUtils.isEmpty(roles)){
            return Lists.newArrayList();
        }
        return roles.stream().map(r -> r.getRoleId()).collect(Collectors.toList());

    }
}
