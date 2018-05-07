package tianyuan.rbac.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tianyuan.rbac.dao.SystemRoleDao;
import tianyuan.rbac.dao.SystemUserDao;
import tianyuan.rbac.dao.UserRoleDao;
import tianyuan.rbac.model.admin.SystemRole;
import tianyuan.rbac.model.admin.SystemUser;
import tianyuan.rbac.model.admin.UserRole;
import tianyuan.rbac.service.SystemLogService;
import tianyuan.rbac.service.SystemRoleAclService;
import tianyuan.rbac.service.UserRoleService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/31 16:27.
 * @Describution:
 */
@Service
@Slf4j
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private SystemUserDao userDao;
    @Autowired
    private SystemRoleDao roleDao;

    @Autowired
    private SystemRoleAclService roleAclService;

    private SystemLogService logService;

    /**
     * 角色的用户列表
     * @param roleId
     * @return
     */
    @Override
    public List<SystemUser> listByRoleId(Integer roleId) {

        return userDao.findAllById(listUserIdByRoleId(roleId));

    }

    /**
     * 修改角色的用户
     * @param roleId
     * @param userIdList
     */
    @Override
    public void changeRoleUser(Integer roleId, List<Integer> userIdList) {
        //核对是否需要更新        //获取该角色的所有用户
        List<Integer> oldUserList = listUserIdByRoleId(roleId);
        //set取所有权限的-需要修改的set，如果为空，则不必修改，否则先删除再修改
        if (listUserIdByRoleId(roleId).size() == userIdList.size()){
            Set<Integer> userIdSet = Sets.newHashSet(userIdList);
            Set<Integer> oldUserIdSet = Sets.newHashSet(oldUserList);
            oldUserIdSet.remove(userIdSet);
            if (CollectionUtils.isEmpty(oldUserIdSet)){
                return ;
            }
        }
        //先删除   //更新
        updateRoleUserList(roleId,userIdList);

        logService.saveRoseUserLog(roleId,oldUserList,userIdList);
    }

    /**
     * 先删除角色的所有用户，再更新加上用户
     * @param roleId
     * @param userIdList
     */
    @Override
    @Transactional
    public void updateRoleUserList(Integer roleId, List<Integer> userIdList) {
        //先删除，再更新
        userRoleDao.deleteAllByRoleId(roleId);

        if (CollectionUtils.isEmpty(userIdList)){
            return ;
        }
        List<UserRole> roleUserList = Lists.newArrayList();
        for (Integer ids : userIdList){
            UserRole userRole = UserRole.builder().roleId(roleId).userId(ids).build();
            //增加操作员信息
            roleUserList.add(userRole);
        }
        //批量更新
        userRoleDao.saveAll(roleUserList);
    }

    /**
     * 用户的角色ID列表
     * @param userId
     * @return
     */
    @Override
    public List<Integer> listRoleIdByUserId(Integer userId) {
        List<UserRole> userRoleList = userRoleDao.findByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleList)){
            return Lists.newArrayList();
        }

        List<Integer> roleIdList = userRoleList.stream().map(u -> u.getRoleId()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(roleIdList)){
            return Lists.newArrayList();
        }
        return roleIdList;
    }

    /**
     * 角色的用户Id列表
     * @param roleId
     * @return
     */
    @Override
    public List<Integer> listUserIdByRoleId(Integer roleId) {
        List<UserRole> userRoleList = userRoleDao.findByRoleId(roleId);
        if (CollectionUtils.isEmpty(userRoleList)){
            return Lists.newArrayList();
        }

        List<Integer> userIdList = userRoleList.stream().map(u -> u.getUserId()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(userIdList)){
            return Lists.newArrayList();
        }
        return userIdList;
    }

    /**
     * 用户列表的所有角色id
     * @param userIdList
     * @return
     */
    @Override
    public List<Integer> listRoleIdListByUserIdList(List<Integer> userIdList) {
        if (CollectionUtils.isEmpty(userIdList)){
            return Lists.newArrayList();
        }

         return userRoleDao.getRoleIdByUserIdList(userIdList);
    }

    /**
     * 角色列表的所有用户ID
     * @param roleIdList
     * @return
     */
    @Override
    public List<Integer> listUserIdListByRoleIdList(List<Integer> roleIdList) {
        if (CollectionUtils.isEmpty(roleIdList)){
            return Lists.newArrayList();
        }
        return userRoleDao.getUserIdByRoleIdList(roleIdList);
    }

    /**
     * 用户的角色详情列表
     * @param userId
     * @return
     */
    @Override
    public List<SystemRole> listAllByUserId(Integer userId) {
        List<Integer> roleIdList = listRoleIdByUserId(userId);
        if (CollectionUtils.isEmpty(roleIdList)){
            return Lists.newArrayList();
        }

        return roleDao.findAllById(roleIdList);
    }

    /**
     * 权限的所属角色详情
     * @param aclId
     * @return
     */
    @Override
    public List<SystemRole> listAllByAclId(Integer aclId) {
        List<Integer> roleIdList = roleAclService.listRoleIdByAclId(aclId);
        if (CollectionUtils.isEmpty(roleIdList)){
            return Lists.newArrayList();
        }

        return roleDao.findAllById(roleIdList);

    }

    /**
     * 角色列表的所有用户详情列表
     * @param roleList
     * @return
     */
    @Override
    public List<SystemUser> listUserListByRoleList(List<Integer> roleList) {
        if (CollectionUtils.isEmpty(roleList)){
            return Lists.newArrayList();
        }
        List<Integer> userIdList = listUserIdListByRoleIdList(roleList);
        return userDao.findAllById(userIdList);

    }

    /**
     * 用户列表的素有角色
     * @param userIdList
     * @return
     */
    @Override
    public List<SystemRole> listRoleListByUserList(List<Integer> userIdList) {
        if (CollectionUtils.isEmpty(userIdList)){
            return Lists.newArrayList();
        }
        List<Integer> roleIdList = listRoleIdListByUserIdList(userIdList);
        return roleDao.findAllById(roleIdList);

    }


}
