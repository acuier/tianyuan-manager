package tianyuan.rbac.dao;

import tianyuan.rbac.model.admin.RoleAcl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/31 11:12.
 * @Describution:
 */
public interface RoleAclDao extends JpaRepository<RoleAcl,Integer> {

    /**获取角色的权限.*/
    List<RoleAcl> findByRoleId(Integer roleId);

    /**获取角色的权限.*/
    List<RoleAcl> findByRoleIdIn(List<Integer> roleIdList);

    /**获取权限的角色.*/
    List<RoleAcl> findByAclId(Integer aclId);

    /**取权限的角色.*/
    List<RoleAcl> findByAclIdIn(List<Integer> aclIdList);

    /**删除角色的权限*/
    void deleteAllByRoleId(Integer roleId);

}
