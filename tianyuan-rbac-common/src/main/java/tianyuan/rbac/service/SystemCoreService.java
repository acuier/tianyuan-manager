package tianyuan.rbac.service;

import tianyuan.rbac.model.admin.SystemAcl;

import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/31 10:58.
 * @Describution:
 */
public interface SystemCoreService {

    /**
     * 获取当前用户的权限列表
     * @return
     */
    List<SystemAcl> listCurrentUserAcl();

    /**
     * 获取角色的权限列表
     * @return
     */
    List<SystemAcl> listAclListByRoleId(Integer roleId);

    /**
     * 获取用户的权限列表
     * @return
     */
    List<SystemAcl> listAclListByUserId(Integer userId);
    /**
     * 是否超级管理员
     * @return
     */
    boolean isSuperAdmin();

    /**
     * 去除所有的权限
     * @return
     */
    List<SystemAcl> getAll();


    /**
     * 获取角色的权限列表
     * @param roleId
     * @return
     */
    List<Integer> listAclIdsByRoleId(Integer roleId);

    /**
     * 获取多个角色的所有权限
     * @param roleList
     * @return
     */
    List<Integer> listAclIdsByRoleIdList(List<Integer> roleList);
}
