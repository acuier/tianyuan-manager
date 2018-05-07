package tianyuan.rbac.service;

import tianyuan.rbac.model.admin.SystemRole;
import tianyuan.rbac.model.admin.SystemUser;
import tianyuan.rbac.param.RoleParam;

import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/31 10:00.
 * @Describution:
 */
public interface SystemRoleService {

    void save(RoleParam param);

    void update(RoleParam param);

    List<SystemRole> listAll();

    boolean checkExist(String roleName,Integer roleId);

    SystemRole getByID(Integer roleId);

    List<SystemRole> listAllByUserId(Integer userId);

    List<SystemRole> listAllByAclId(Integer aclId);

    List<SystemUser> listUserListByRoleList(List<Integer> roleList);

    List<Integer> listRoleIdByUserId(Integer userId);

    List<Integer> listRoleIdByAclId(Integer aclId);

    List<Integer> listRoleIdByRoleList(List<Integer> roleList);
}
