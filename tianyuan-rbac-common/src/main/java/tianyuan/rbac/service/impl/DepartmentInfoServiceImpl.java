package tianyuan.rbac.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tianyuan.common.enums.ResultEnum;
import tianyuan.common.exception.ParamException;
import tianyuan.rbac.model.admin.DepartmentInfo;
import tianyuan.common.utils.BeanValidator;
import tianyuan.common.utils.LevelUtil;
import tianyuan.rbac.config.RedisCacheConfig;
import tianyuan.rbac.dao.DepartmentInfoDao;
import tianyuan.rbac.param.DepartmentParam;
import tianyuan.rbac.service.DepartmentInfoService;
import tianyuan.rbac.service.SystemLogService;
import tianyuan.rbac.service.SystemUserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/29 15:50.
 * @Describution:
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "dept")
public class DepartmentInfoServiceImpl implements DepartmentInfoService {

    @Autowired
    private DepartmentInfoDao departmentInfoDao;

    @Autowired
    private SystemUserService userService;

    @Autowired
    private SystemLogService logService;

   @Resource
   private RedisCacheConfig redisCacheConfig;
    /**
     * 添加/更新部门信息
     * @param param
     */
    @Override
    public void save(DepartmentParam param) {
        //1检查参数
        BeanValidator.check(param);
        //2部门机构同一级下 名称 昵称不能相同
        if (checkExist(param.getDepartmentParentId(),param.getDepartmentName(),param.getDepartmentNickName(),param.getDepartmentId())) {
            log.error("【创建部门信息】参数不正确, param={}", param);
            throw new ParamException(ResultEnum.DUPLICATE_DEPT_NAMES);
        }
        ////2有些时候，任何情况下 名称 昵称不能相同
/*        if (checkExist(param.getDepartmentName(),param.getDepartmentNickName(),param.getDepartmentId())){
            log.error("【创建部门信息】参数不正确, param={}", param);
            throw new ParamException(ResultEnum.DUPLICATE_NAME.getCode(),
                    ResultEnum.DUPLICATE_NAME.getMessage());
        }*/
        //3创建类
        DepartmentInfo departmentInfo = DepartmentInfo.builder().build();
        BeanUtils.copyProperties(param,departmentInfo);
        //计算level
        departmentInfo.setDepartmentLevel(LevelUtil.calculateLevel(
                getLevel(param.getDepartmentParentId()),param.getDepartmentParentId()));
        //对text字段进行静态化处理
        if (StringUtils.isNoneBlank(param.getDepartmentDescription())) {
            //todo:
        }
        //添加text字段的url
        departmentInfo.setDepartmentDescriptionUrl("");//todo:
        //添加操作人员信息
        departmentInfo.setOperator("1");;//todo:
        departmentInfo.setOperatorIp("");//todo:

        departmentInfoDao.save(departmentInfo);

        logService.saveDepLog(null,departmentInfo);
    }

    /**
     * 门机构同一级下 名称 昵称不能相同
     * @param parentId
     * @param deptName
     * @param deptNickName
     * @return
     */
    @Override
    public boolean checkExist(Integer parentId, String deptName, String deptNickName, Integer deptId){
        if (deptId == null){
            deptId = -1;
        }
       return departmentInfoDao.countByDepartmentParentIdAndDepartmentNameOrDepartmentNickName
               (parentId,deptName,deptNickName,deptId) > 0;
    }


    /**
     * 计算层次
     * @param departmentId
     * @return
     */
    public String getLevel(Integer departmentId){
        return  departmentInfoDao.findById(departmentId)
                    .map(e -> e.getDepartmentLevel())
                    .orElse("");

    }

    /**
     * 转换一下 list<DepartmentInfo> ---list<DepartmentInfoDto>
     * @return
     */
    @Override
    @Cacheable
    public List<DepartmentInfo> findAll() {
        return departmentInfoDao.findAll();

//        List<DepartmentInfoDto> deptInoDtoList = new ArrayList<>();
//        for (DepartmentInfo departmentInfo : deptInfoList){
//            DepartmentInfoDto departmentInfoDto = DepartmentInfoDto.departmentInfo2DepartmentInfoDto(departmentInfo);
//            deptInoDtoList.add(departmentInfoDto);
//        }
//        return deptInfoList;//deptInoDtoList;
    }

    /**
     * 更新
     * @param param
     */
    @Override
    @CacheEvict(allEntries=true)
    public void update(DepartmentParam param) {
        //1检查参数
        BeanValidator.check(param);
        //2部门机构同一级下 名称 昵称不能相同
//        if (checkExist(param.getDepartmentParentId(),param.getDepartmentName(),param.getDepartmentNickName())){
//            log.error("【创建部门信息】参数不正确, param={}", param);
//            throw new ParamException(ResultEnum.DUPLICATE_DATA.getCode(),
//                    ResultEnum.DUPLICATE_DATA.getMessage());
//        }
        if (checkExist(param.getDepartmentParentId(),param.getDepartmentName(),param.getDepartmentNickName(),param.getDepartmentId())){
            log.error("【创建部门信息】参数不正确, param={}", param);
            throw new ParamException(ResultEnum.DUPLICATE_DEPT_NAMES);
        }
        //3更新的是否存在
        DepartmentInfo oldDept = departmentInfoDao.findById(param.getDepartmentId())
                .orElseThrow(() -> new ParamException(ResultEnum.DEPT_NOT_EXIST));

        DepartmentInfo newDept = DepartmentInfo.builder().build();
        BeanUtils.copyProperties(param,newDept);
        //计算level
        newDept.setDepartmentLevel(LevelUtil.calculateLevel(
                getLevel(param.getDepartmentParentId()),param.getDepartmentParentId()));
        //对text字段进行静态化处理
        if (StringUtils.isNoneBlank(param.getDepartmentDescription())) {
            //todo:
        }
        //添加text字段的url
        newDept.setDepartmentDescriptionUrl("");//todo:
        //添加操作人员信息
        newDept.setOperator("1");//todo:
        newDept.setOperatorIp("");//todo:
        //更新所有子部门
        updateWithChinld(oldDept,newDept);

        logService.saveDepLog(oldDept,newDept);
    }

    /**
     * 更新自己和子部们数据
     * @param oldDept
     * @param newDept
     */

    @Transactional
    void updateWithChinld(DepartmentInfo oldDept, DepartmentInfo newDept){
        //1、是否需要更新子部们
        String newLevelPrefix = newDept.getDepartmentLevel();
        String oldLevelPrefix = oldDept.getDepartmentLevel();
        //如果level一样，就不必更新，否则更新
        if( !newLevelPrefix.equals(oldLevelPrefix)){
            List<DepartmentInfo> deptList = departmentInfoDao.findAllByDepartmentLevelIsStartingWith(oldLevelPrefix);
            if (CollectionUtils.isNotEmpty(deptList)){
                for (DepartmentInfo departmentInfo : deptList){
                    String level = departmentInfo.getDepartmentLevel();
                    if (level.indexOf(oldLevelPrefix) == 0){
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        departmentInfo.setDepartmentLevel(level);
                    }
                }
                //批量更新
            departmentInfoDao.saveAll(deptList);
            }
        }
        //2.更新当前的数据
        departmentInfoDao.save(newDept);
    }

    /**
     * 不允许相同的名称
     * @param deptName
     * @return
     */
    @Override
    public boolean checkNameExist(String deptName) {
        return departmentInfoDao.countByDepartmentName(deptName) > 0;
    }

    /**
     * 昵称不能相同
     * @param deptNick
     * @return
     */
    @Override
    public boolean checkNickExist(String deptNick) {
        return departmentInfoDao.countByDepartmentNickName(deptNick) > 0;
    }

    /**
     * 不允许存在相同的名称和昵称的机构
     * @param deptName
     * @param deptNickName
     * @return
     */
    @Override
    public boolean checkExist(String deptName, String deptNickName,Integer deptId) {
        if (deptId == null){
            deptId = -1;
        }
        return departmentInfoDao.countByDepartmentNameOrDepartmentNickName(deptName,deptNickName,
                deptId) > 0;
    }

    @Override
    public void delete(Integer departId) {
        //是否存在？
        departmentInfoDao.findById(departId)
                .orElseThrow(() -> new ParamException(ResultEnum.DEPT_NOT_EXIST));
        //是否有子部门？
        if (checkHasChild(departId)){
            log.error("【删除部门信息】, param={}", departId);
            throw new ParamException(ResultEnum.DEPART_HAS_CHILD);
        }
        //是否有人员？
        if (userService.countUserWithDeptId(departId)>0){
            log.error("【删除部门信息】, param={}", departId);
            throw new ParamException(ResultEnum.DEPART_HAS_USER);
        }
        departmentInfoDao.deleteById(departId);
    }

    @Override
    public boolean checkHasChild(Integer parentId) {
        return departmentInfoDao.countByDepartmentParentId(parentId) > 0;
    }

    @Override//推广此用法
    public DepartmentInfo getOne(Integer departmentInfoId) {
        return  departmentInfoDao.findById(departmentInfoId)
                .orElseThrow(() -> new ParamException(ResultEnum.DEPT_NOT_EXIST));

    }

    @Override
    public boolean checkHasUser(Integer parentId) {
        return userService.countUserWithDeptId(parentId)>0;
    }
}
