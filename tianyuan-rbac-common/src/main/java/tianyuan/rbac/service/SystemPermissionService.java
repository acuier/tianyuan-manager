package tianyuan.rbac.service;

import tianyuan.rbac.dto.PermissionDto;

import tianyuan.rbac.param.PermissionParam;

import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/30 17:03.
 * @Describution:
 */
public interface SystemPermissionService {

    void save(PermissionParam param);

    void update(PermissionParam param);

    void delete(Integer permissionId);

    List<PermissionDto> findAll();

    boolean checkExist(Integer parentId,String permissionName,Integer permissionId);

    boolean checkExist(String permissionName,Integer permissionId);

    boolean checkNameExist(String permissionName);

    String getLevel(Integer permissionId);

    boolean checkHasAcl(Integer permissionId);

    boolean checkHasChild(Integer permissionId);
}
