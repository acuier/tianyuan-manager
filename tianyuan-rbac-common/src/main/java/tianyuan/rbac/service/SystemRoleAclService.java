package tianyuan.rbac.service;

import tianyuan.rbac.model.admin.RoleAcl;

import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/31 17:15.
 * @Describution:
 */
public interface SystemRoleAclService {

    /**批量更新 角色的权限*/
    void changeRoleAcl(Integer roleID,List<Integer> aclIdList);

    void updateRoleAclList(Integer roleId,List<Integer> aclIdList);

    /**获取角色的权限列表.*/
    List<Integer> listAclIdByRoleId(Integer roleId);

    List<RoleAcl> listByRoleId(Integer roleId);

    List<Integer> listAclIdByRoleIdList(List<Integer> roleIdList);

    List<RoleAcl> listByRoleIdList(List<Integer> roleIdList);
    /**获取权限的角色列表.*/
    List<Integer> listRoleIdByAclId(Integer aclId);

    List<Integer> listRoleIdByAclIdList(List<Integer> aclIdList);

    List<RoleAcl> listByAclId(Integer aclId);

    List<RoleAcl> listByAclIdList(List<Integer> aclIdList);
}
