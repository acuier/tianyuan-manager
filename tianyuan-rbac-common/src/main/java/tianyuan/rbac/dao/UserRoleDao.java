package tianyuan.rbac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tianyuan.rbac.model.admin.UserRole;

import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/31 11:12.
 * @Describution:
 */
public interface UserRoleDao extends JpaRepository<UserRole,Integer> {

    List<UserRole> findByUserId(Integer userId);

    List<UserRole> findByRoleId(Integer roleId);

    @Override
    List<UserRole> findAll();

    /**删除角色的用户*/
    void deleteAllByRoleId(Integer roleId);

    @Query(value = "select DISTINCT user_id from system_User_Role where role_id in( ?1 ) ",nativeQuery = true)
    List<Integer> getUserIdByRoleIdList(List<Integer> roleIdList);


    @Query(value = "select DISTINCT role_id from system_User_Role where user_id in( ?1 ) ",nativeQuery = true)
    List<Integer> getRoleIdByUserIdList(List<Integer> userIdList);
}
