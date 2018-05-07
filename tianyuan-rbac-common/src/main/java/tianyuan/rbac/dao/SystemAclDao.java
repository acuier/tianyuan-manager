package tianyuan.rbac.dao;

import tianyuan.rbac.model.admin.SystemAcl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/31 8:22.
 * @Describution:
 */
public interface SystemAclDao extends JpaRepository<SystemAcl,Integer> {

    /**分页显示 某模块下的权限*/
    Page<SystemAcl> findAllByAclModuleId(Integer aclModuleId,Pageable pageable);
    /**统计 模块下的权限数量*/
    int countByAclModuleId(Integer aclModuleId);
    /**统计某模块下权限名称数，杜绝相同*/
    int countByAclModuleIdAndAclNameAndAclIdNot(Integer aclModuleId,String aclName,Integer aclId);

}
