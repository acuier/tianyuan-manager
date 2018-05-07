package tianyuan.rbac.dao;

import tianyuan.rbac.model.admin.SystemUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/30 10:58.
 * @Describution:
 */
public interface SystemUserDao extends JpaRepository<SystemUser,Integer> {

    /** 检查重名的数量，杜绝重名*/
    Integer countByUserNameAndUserIdNot(String userName,Integer userId);
    /** 检查相同的电话的数量，杜绝重复*/
    Integer countByUserPhoneAndUserIdNot(String userPhone,Integer userId);
    /** 检查相同的邮件的数量，杜绝重复*/
    Integer countByUserEmailAndUserIdNot(String userEmail,Integer userId);
    /** 统计某部门下的人员的数量*/
    Integer countByDepartmentId(Integer departmentId);
    /** 用户名 邮件 电话 检索*/
    @Query(value = "Select * from SYSTEM_USER where user_name = ?1 or user_phone = ?1 or user_email =?1",nativeQuery = true)
    SystemUser findByKeyWord(String keyWord);
    /** 分页显示某部门下的人员*/
    Page<SystemUser> findAllByDepartmentId(Integer departmentId,Pageable pageable);

    Optional<SystemUser> findByUserName(String userName);
}
