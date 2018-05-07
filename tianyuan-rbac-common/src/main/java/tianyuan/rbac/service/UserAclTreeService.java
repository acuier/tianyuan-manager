package tianyuan.rbac.service;


import tianyuan.rbac.dto.PermissionLevelDto;

import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/4/2 16:47.
 * @Describution:
 */

public interface UserAclTreeService  {
    List<PermissionLevelDto> userAclTree(int userId);
}
