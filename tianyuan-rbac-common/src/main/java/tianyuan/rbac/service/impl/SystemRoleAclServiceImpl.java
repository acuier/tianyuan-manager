package tianyuan.rbac.service.impl;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;


import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import tianyuan.rbac.dao.RoleAclDao;
import tianyuan.rbac.model.admin.RoleAcl;
import tianyuan.rbac.service.SystemLogService;
import tianyuan.rbac.service.SystemRoleAclService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/31 17:15.
 * @Describution:
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "roleAcl")//,,keyGenerator = "wiselyKeyGenerator")
public class SystemRoleAclServiceImpl implements SystemRoleAclService {

    @Autowired
    private RoleAclDao roleAclDao;
    @Autowired
    private SystemLogService logService;


    @Override
    public void changeRoleAcl(Integer roleId, List<Integer> aclIdList) {
        //核对是否需要更新
        //获取该角色的所有权限
        List<Integer> oldAclList = listAclIdByRoleId(roleId);
        //set取所有权限的-需要修改的set，如果为空，则不必修改，否则先删除再修改
        if (listAclIdByRoleId(roleId).size() == aclIdList.size()){
            Set<Integer> oldAclIdsSet = Sets.newHashSet(oldAclList);
            Set<Integer> aclIdsSet = Sets.newHashSet(aclIdList);
            oldAclIdsSet.remove(aclIdsSet);
            if (CollectionUtils.isEmpty(oldAclIdsSet)){
                return ;
            }
        }
        //先删除 //更新
        updateRoleAclList(roleId,aclIdList);

        logService.saveRoleAclLog(roleId,oldAclList,aclIdList);
    }

    @Transactional
    public void updateRoleAclList(Integer roleId,List<Integer> aclIdList){
        //先删除，再更新
        roleAclDao.deleteAllByRoleId(roleId);

        if (CollectionUtils.isEmpty(aclIdList)){
            return ;
        }
        List<RoleAcl> roleAclList = Lists.newArrayList();
        for (Integer ids : aclIdList){
            RoleAcl roleAcl = RoleAcl.builder().roleId(roleId).aclId(ids).build();
            //增加操作员信息 todo:
            roleAclList.add(roleAcl);
        }
      //批量更新
        roleAclDao.saveAll(roleAclList);
    }

    /**
     * 根据角色ID获取拥有的权限Id列表
     * @param roleId
     * @return
     */
    @Override
    @Cacheable
    public List<Integer> listAclIdByRoleId(Integer roleId) {
        List<RoleAcl> roleAclList  = listByRoleId(roleId);
        if (CollectionUtils.isEmpty(roleAclList)){
            return Lists.newArrayList();
        }
        return   roleAclList.stream()
                .map(e -> e.getAclId()).collect(Collectors.toList());

    }

    /**
     * 获取角色列表的所有权限ID
     * @param roleIdList
     * @return
     */
    @Override
    public List<Integer> listAclIdByRoleIdList(List<Integer> roleIdList) {
        List<RoleAcl> roleAclList  = listByRoleIdList(roleIdList);
        if (CollectionUtils.isEmpty(roleAclList)){
            return Lists.newArrayList();
        }
        Set<Integer> aclIdSet  =  roleAclList.stream()
                .map(e -> e.getAclId()).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(aclIdSet)){
           return Lists.newArrayList();
        }

        return new ArrayList<>(aclIdSet);
    }

    /**
     * 根据权限获取拥有此权限的角色Id列表
     * @param aclId
     * @return
     */
    @Override
    @Cacheable
    public List<Integer> listRoleIdByAclId(Integer aclId) {
        List<RoleAcl> roleAclList  = listByAclId(aclId);
        if (CollectionUtils.isEmpty(roleAclList)){
            return Lists.newArrayList();
        }
        return  roleAclList.stream()
                .map(e -> e.getRoleId()).collect(Collectors.toList());

    }

    @Override
    public List<Integer> listRoleIdByAclIdList(List<Integer> aclIdList) {
        List<RoleAcl> roleAclList = listByAclIdList(aclIdList);
        if (CollectionUtils.isEmpty(roleAclList)){
            return Lists.newArrayList();
        }
        Set<Integer> roleIdSet = roleAclList.stream()
                .map(roleIdList -> roleIdList.getRoleId())
                .collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(roleIdSet)){
            return Lists.newArrayList();
        }
        return new ArrayList<>(roleIdSet);
    }

    @Override
    public List<RoleAcl> listByRoleId(Integer roleId) {
        return roleAclDao.findByRoleId(roleId);
    }

    @Override
    public List<RoleAcl> listByRoleIdList(List<Integer> roleIdList) {
        return roleAclDao.findByRoleIdIn(roleIdList);
    }

    @Override
    public List<RoleAcl> listByAclId(Integer aclId) {
        return roleAclDao.findByAclId(aclId);
    }

    @Override
    public List<RoleAcl> listByAclIdList(List<Integer> aclIdList) {
        return roleAclDao.findByAclIdIn(aclIdList);
    }
}
