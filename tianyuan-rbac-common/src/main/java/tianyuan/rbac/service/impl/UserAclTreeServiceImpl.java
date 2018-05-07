package tianyuan.rbac.service.impl;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tianyuan.rbac.dto.AclDto;
import tianyuan.rbac.dto.PermissionLevelDto;
import tianyuan.rbac.model.admin.SystemAcl;
import tianyuan.rbac.service.RoleTreeService;
import tianyuan.rbac.service.SystemCoreService;
import tianyuan.rbac.service.UserAclTreeService;

import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/4/2 16:47.
 * @Describution:
 */
@Service
@Slf4j
public class UserAclTreeServiceImpl implements UserAclTreeService {

    @Autowired
    private SystemCoreService coreService;
    @Autowired
    private RoleTreeService roleTreeService;

    @Override
    public List<PermissionLevelDto> userAclTree(int userId) {
        //当前用户已分配的权限点
        List<SystemAcl> userAclList = coreService.listAclListByUserId(userId);

        List<AclDto> aclDtoList = Lists.newArrayList();
        for (SystemAcl acl : userAclList) {
            AclDto dto = AclDto.acl2Acldto(acl);
            dto.setHasAcl(true);
            dto.setChecked(true);
            aclDtoList.add(dto);
        }
        return roleTreeService.aclList2tree(aclDtoList);
    }
}
