package tianyuan.rbac.dao;

import tianyuan.rbac.model.admin.SystemRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/31 10:00.
 * @Describution:
 */
public interface SystemRoleDao extends JpaRepository<SystemRole,Integer>,JpaSpecificationExecutor {

    /**角色名的数量，杜绝重复*/
    Integer countByRoleNameAndRoleIdNot(String roleName,Integer roleId);


}
