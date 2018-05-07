package tianyuan.rbac.service;

import tianyuan.rbac.dto.DepartmentLevelDto;

import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/29 19:40.
 * @Describution:
 */
public interface DepartmentTreeService {
    List<DepartmentLevelDto> deptTree();
}
