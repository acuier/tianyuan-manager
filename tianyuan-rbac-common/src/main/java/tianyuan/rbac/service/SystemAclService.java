package tianyuan.rbac.service;

import org.springframework.data.domain.Page;
import tianyuan.common.baseDTO.SortDTO;
import tianyuan.rbac.model.admin.SystemAcl;
import tianyuan.rbac.param.AclParam;

import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/31 8:20.
 * @Describution:
 */
public interface SystemAclService {

    void save(AclParam param);

    void update(AclParam param);

    boolean checkExist(int aclModuleId,String aclName,Integer aclId);

    Page<SystemAcl> listByAclModuleID (Integer aclModuleId, Integer currentPage, SortDTO... dtos);

    boolean checkModule(Integer aclModuleId);

    List<SystemAcl> listAll();


}
