package tianyuan.rbac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tianyuan.common.enums.ResultEnum;
import tianyuan.common.exception.ParamException;
import tianyuan.common.utils.BeanValidator;
import tianyuan.common.utils.LevelUtil;
import tianyuan.rbac.dao.SystemAclDao;
import tianyuan.rbac.dao.SystemPermissionDao;
import tianyuan.rbac.dto.PermissionDto;
import tianyuan.rbac.model.admin.SystemPermission;
import tianyuan.rbac.param.PermissionParam;
import tianyuan.rbac.service.SystemLogService;
import tianyuan.rbac.service.SystemPermissionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/30 17:05.
 * @Describution:
 */
@Service
@Slf4j
@CacheConfig(cacheNames = {"permission"})//,,keyGenerator = "wiselyKeyGenerator")
public class SystemPermissionServiceImpl implements SystemPermissionService {

    @Autowired
    private SystemPermissionDao permissionDao;

    @Autowired
    private SystemAclDao aclAdo;
    @Autowired
    private SystemLogService logService;


    @Override
    @CacheEvict(allEntries = true)
    public void save(PermissionParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getParentId(), param.getPermissionName(), param.getPermissionId())) {
            log.error("【创建权限模块信息】参数不正确, param={}", param);
            throw new ParamException(ResultEnum.DUPLICATE_MODULE_NAME);
        }
        SystemPermission permission = SystemPermission.builder().build();
        BeanUtils.copyProperties(param,permission);
        //计算层级
        permission.setPermissionLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        //todo: 添加操作员信息
        permission.setOperator("1");
        permission.setOperatorIp("1112");
        //保存
        permissionDao.save(permission);
        // 添加日志
        logService.saveAcLModuleLog(null,permission);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void update(PermissionParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getParentId(), param.getPermissionName(), param.getPermissionId())) {
            log.error("【创建权限模块信息】参数不正确, param={}", param);
            throw new ParamException(ResultEnum.DUPLICATE_MODULE_NAME);
        }

        //更新的模块是否存在
//        Optional<SystemPermission> oldPermission= permissionDao.findById(param.getPermissionId());
//        if (!oldPermission.isPresent()){
//            log.error("【创建权限模块信息】参数不正确, param={}", param);
//            throw new ParamException(ResultEnum.MODULE_NOT_EXIST);
//        }
        SystemPermission oldPermission = permissionDao.findById(param.getPermissionId())
                .orElseThrow(() -> new ParamException(ResultEnum.MODULE_NOT_EXIST));
        //
        SystemPermission newPermission = SystemPermission.builder().build();
        BeanUtils.copyProperties(param,newPermission);
        //计算层级
        newPermission.setPermissionLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        //todo: 添加操作员信息
        newPermission.setOperator("1");
        //保存\
        updateWithChild(oldPermission,newPermission);

        logService.saveAcLModuleLog(oldPermission,newPermission);
    }

    @Transactional
    void updateWithChild(SystemPermission oldPermission, SystemPermission newPermission){
        //1、是否需要更新子部们
        String newLevelPrefix = newPermission.getPermissionLevel();
        String oldLevelPrefix = oldPermission.getPermissionLevel();
        //如果level一样，就不必更新，否则更新
        if( !newLevelPrefix.equals(oldLevelPrefix)){
            List<SystemPermission> permissionList = permissionDao.findByPermissionLevelIsStartingWith(oldLevelPrefix);
            if (CollectionUtils.isNotEmpty(permissionList)){
                for (SystemPermission permission : permissionList){
                    String level = permission.getPermissionLevel();
                    if (level.indexOf(oldLevelPrefix) == 0){
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        permission.setPermissionLevel(level);
                    }
                }
                //批量更新
                permissionDao.saveAll(permissionList);
            }
        }
        //2.更新当前的数据
        permissionDao.save(newPermission);
    }

    @Override
    public boolean checkExist(Integer parentId, String permissionName, Integer permissionId) {
        if (permissionId == null){
            permissionId = -1;
        }
        return  (permissionDao.countByParentIdAndPermissionNameAndPermissionIdNot(parentId,
                permissionName,permissionId)> 0);
    }

    @Override
    public boolean checkExist(String permissionName, Integer permissionId) {
        if (permissionId == null){
            permissionId = -1;
        }
        return  (permissionDao.countByPermissionNameAndPermissionIdNot(permissionName,permissionId)>
                0);
    }

    @Override
    public boolean checkNameExist(String permissionName) {
        return  (permissionDao.countByPermissionName(permissionName) > 0);
    }

    @Override
    public String getLevel(Integer permissionId) {
        Optional<SystemPermission> permission = permissionDao.findById(permissionId);
        return  permission.map(f -> f.getPermissionLevel()).orElse("");
    }

    @Override
    @Cacheable
    public List<PermissionDto> findAll() {
        List<SystemPermission> permissionList = permissionDao.findAll();

        List<PermissionDto> permissionDtoList = new ArrayList<>();
        for (SystemPermission permission : permissionList){
            PermissionDto permissionDto = PermissionDto.permission2PermissionDto(permission);
            permissionDtoList.add(permissionDto);
        }
        return permissionDtoList;
    }

    @Override
    @CacheEvict(allEntries = true)
    public void delete(Integer permissionId) {
        //是否存在？
        permissionDao.findById(permissionId)
                .orElseThrow(() -> new ParamException(ResultEnum.DEPT_NOT_EXIST));
        //是否有子模块？
        if (checkHasChild(permissionId)){
            log.error("【删除模块信息】, param={}", permissionId);
            throw new ParamException(ResultEnum.MODULE_HAS_CHILD);
        }
        //是否有权限点？
        if (checkHasAcl(permissionId)){
            log.error("【删除模块信息】, param={}", permissionId);
            throw new ParamException(ResultEnum.MODULE_HAS_ACL);
        }
        permissionDao.deleteById(permissionId);
    }

    @Override
    public boolean checkHasAcl(Integer permissionId) {
        return aclAdo.countByAclModuleId(permissionId)>0;
    }

    @Override
    public boolean checkHasChild(Integer permissionId) {
        return permissionDao.countByParentId(permissionId) >0;
    }
}
