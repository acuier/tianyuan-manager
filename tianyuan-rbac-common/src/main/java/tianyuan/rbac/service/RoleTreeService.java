package tianyuan.rbac.service;

import tianyuan.rbac.dto.AclDto;
import tianyuan.rbac.dto.PermissionLevelDto;

import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/31 10:47.
 * @Describution:
 */
public interface RoleTreeService {

    List<PermissionLevelDto> roleTree(Integer roleId);

    List<PermissionLevelDto> aclList2tree(List<AclDto> aclDtoList);
}
