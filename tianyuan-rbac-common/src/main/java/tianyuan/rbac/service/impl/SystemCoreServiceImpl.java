package tianyuan.rbac.service.impl;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tianyuan.rbac.model.admin.SystemAcl;
import tianyuan.rbac.dao.SystemAclDao;
import tianyuan.rbac.dao.SystemUserDao;
import tianyuan.rbac.service.SystemCoreService;
import tianyuan.rbac.service.SystemRoleAclService;
import tianyuan.rbac.service.UserRoleService;

import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/31 11:23.
 * @Describution:用来获取相关角色权限或用户权限
 */

@Service
@Slf4j
public class SystemCoreServiceImpl implements SystemCoreService {

    @Autowired
    private SystemAclDao aclAdo;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private SystemRoleAclService roleAclService;
    @Autowired
    private SystemUserDao userDao;

    /**
     * 获取当前用户的权限列表
     * @return
     */
    @Override
    public List<SystemAcl> listCurrentUserAcl() {
        //1获取当前UserID todo:
        int userId = 1;

        //2 通过userId查询listUserAclListByUserId
        return listAclListByUserId(userId);
    }

    /**
     * 获取当前角色的权限列表
     * @return
     */
    @Override
    public List<SystemAcl> listAclListByRoleId(Integer roleId) {
        List<Integer> aclListByRoleId = roleAclService.listAclIdByRoleId(roleId);
        if (CollectionUtils.isEmpty(aclListByRoleId)){
           return Lists.newArrayList();
        }
        return aclAdo.findAllById(aclListByRoleId);
    }

    /**
     * 根据useid获取用户的权限列表
     * @return
     */
    @Override
    public List<SystemAcl> listAclListByUserId(Integer userId) {
        //1是否超级管理员，是则取出来所有权限 todo:
        if (isSuperAdmin()){
            return aclAdo.findAll();
        }
        //2取用户的角色列表
        List<Integer> roleIdList = userRoleService.listRoleIdByUserId(userId);
        if (CollectionUtils.isEmpty(roleIdList)){
            return Lists.newArrayList();
        }
        //3取用户的所有权限（取角色列表中所有角色的权限）
        List<Integer> userAclIdList = roleAclService.listAclIdByRoleIdList(roleIdList);
        List<SystemAcl> userAclList = aclAdo.findAllById(userAclIdList);

        if (CollectionUtils.isEmpty(userAclList)){
            return Lists.newArrayList();
        }
        //返回

        return userAclList;
    }

    @Override
    public boolean isSuperAdmin() {
        return false;
    }

    @Override
    public List<SystemAcl> getAll() {
        return aclAdo.findAll();
    }


    @Override
    public List<Integer> listAclIdsByRoleId(Integer roleId) {
        return null;//aclAdo.findAclIdByRoleId(roleId);

    }

    @Override
    public List<Integer> listAclIdsByRoleIdList(List<Integer> roleList) {
        return null;
    }
}
