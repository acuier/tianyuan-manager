package tianyuan.rbac.dao;

import tianyuan.rbac.model.admin.SystemPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/30 17:04.
 * @Describution:
 */
public interface SystemPermissionDao extends JpaRepository<SystemPermission,Integer > {

    List<SystemPermission> findByPermissionLevelIsStartingWith(String level);

    //同一层下不允许有重复的
   // @Query(value ="select count(1) from system_permission where parent_id=?1 and permission_name = ?2 and permission_id <> ?3" ,nativeQuery = true)
    Integer countByParentIdAndPermissionNameAndPermissionIdNot(Integer parentId,String permissionName,Integer permissionId);

    //不允许有重复的//    Integer countByDepartmentNameOrDepartmentNickName(String departmentName,String departmentNickName);
    Integer countByPermissionNameAndPermissionIdNot(String permissionName,Integer permissionId);

    //名称是否存在
    Integer countByPermissionName(String permissionName);

    //名称是否存在
    Integer countByParentId(Integer parentId);
}
