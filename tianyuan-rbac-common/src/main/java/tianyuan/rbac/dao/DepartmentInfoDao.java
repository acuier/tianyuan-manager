package tianyuan.rbac.dao;

import tianyuan.rbac.model.admin.DepartmentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/29 15:25.
 * @Describution:
 */
public interface DepartmentInfoDao extends JpaRepository<DepartmentInfo,Integer> {

    /**获取level的所有子部门*/
    List<DepartmentInfo> findAllByDepartmentLevelIsStartingWith(String level);

    //同一层下不允许有重复的名称（机构）
    @Query(value = "select count(*)  from department_info where department_parent_id = ?1 and (department_name = ?2  or department_nick_name = ?3 )and department_id <> ?4", nativeQuery = true)
    Integer countByDepartmentParentIdAndDepartmentNameOrDepartmentNickName(Integer departmentParentId,String departmentName,String departmentNickName,Integer departmentId);

    //不允许有重复的名称（机构）//    Integer countByDepartmentNameOrDepartmentNickName(String departmentName,String departmentNickName);
    @Query(value = "select count(*)  from department_info where (department_name = ?1  or department_nick_name = ?2 ) and department_id <> ?3", nativeQuery = true)
    Integer countByDepartmentNameOrDepartmentNickName(String departmentName,String departmentNickName,Integer DepartmentId);

    //名称是否存在
    Integer countByDepartmentName(String departmentName);

    //简称是否存在
    Integer countByDepartmentNickName(String departmentNickName);

    //是否有子部们
    Integer countByDepartmentParentId(Integer departmentParentId);

}
