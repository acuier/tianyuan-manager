package tianyuan.rbac.service;

import tianyuan.rbac.model.admin.SystemRole;
import tianyuan.rbac.model.admin.SystemUser;

import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/31 16:25.
 * @Describution:
 */
public interface UserRoleService {

    List<SystemUser> listByRoleId(Integer roleId);

    /**批量更新 角色的用户*/
    void changeRoleUser(Integer roleId,List<Integer> userIdList);

    void updateRoleUserList(Integer roleId,List<Integer> userIdList);
    /**
     * 获取用户的所有角色列表
     * @param userId
     * @return
     */
    List<Integer> listRoleIdByUserId(Integer userId);

    List<Integer> listUserIdByRoleId(Integer roleId);

    List<Integer> listRoleIdListByUserIdList(List<Integer> userIdList);

    List<Integer> listUserIdListByRoleIdList(List<Integer> roleIdList);

    List<SystemRole> listAllByUserId(Integer userId);

    List<SystemRole> listAllByAclId(Integer aclId);

    List<SystemUser> listUserListByRoleList(List<Integer> roleList);

    List<SystemRole> listRoleListByUserList(List<Integer> userIdList);
}
