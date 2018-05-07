package tianyuan.rbac.service;

import tianyuan.rbac.model.admin.DepartmentInfo;
import tianyuan.rbac.param.DepartmentParam;

import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/29 15:49.
 * @Describution:
 */

public interface DepartmentInfoService {

    void save(DepartmentParam param);

    void update(DepartmentParam param);

    void delete(Integer departId);

    List<DepartmentInfo> findAll();

    /**
     * 部门机构同一级下 名称 昵称不能相同
     * @param parentId
     * @param deptName
     * @param deptNickName
     * @param deptId
     * @return
     */
    boolean checkExist(Integer parentId,String deptName,String deptNickName,Integer deptId);

    /**
     * 不允许重复的名称和简称
     * @param deptName
     * @param deptNickName
     * @param deptId
     * @return
     */
    boolean checkExist(String deptName,String deptNickName,Integer deptId);

    boolean checkNameExist(String deptName);

    boolean checkNickExist(String deptNick);

    /**
     * 获取层级
     * @param departmentId
     * @return
     */
    String getLevel(Integer departmentId);

    /**
     * 是否有子部门
     * @param parentId
     * @return
     */
    boolean checkHasChild(Integer parentId);

    /**
     * 是否有成员
     * @param parentId
     * @return
     */
    boolean checkHasUser(Integer parentId);

    /**
     * 详情
     * @param departmentInfo
     * @return
     */
    DepartmentInfo getOne(Integer departmentInfo);

}
