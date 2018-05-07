package tianyuan.rbac.service;


import org.springframework.data.domain.Page;
import tianyuan.common.baseDTO.SortDTO;
import tianyuan.rbac.model.admin.*;
import tianyuan.rbac.param.SearchLogParam;

import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/4/2 8:05.
 * @Describution:
 */
public interface SystemLogService {

    /**分页查询复杂条件的数据*/
    Page<LogSystemWithBLOBs> searchPageList(SearchLogParam param, Integer currentPage, SortDTO... sortDTOS );

    void recoverLog(Integer id);

    void saveDepLog(DepartmentInfo oldDept, DepartmentInfo newDept);

    void saveUserLog(SystemUser oldUser, SystemUser newUser);

    void saveAcLModuleLog(SystemPermission oldPermission, SystemPermission newPermission);

    void saveAclLog(SystemAcl oldAcl, SystemAcl newAcl);

    void saveRoleLog(SystemRole oldRole, SystemRole newRole);

    void saveRoleAclLog(Integer roleId,List<Integer> oldRoleAclList,List<Integer> newRoleAclList);

    void saveRoseUserLog(Integer roleId,List<Integer>  oldUserRoleList,List<Integer>  newUserRoleList);


}
