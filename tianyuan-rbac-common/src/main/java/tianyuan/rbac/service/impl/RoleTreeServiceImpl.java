package tianyuan.rbac.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tianyuan.common.enums.NormalStatusEnum;
import tianyuan.rbac.model.admin.SystemAcl;
import tianyuan.rbac.dto.AclDto;
import tianyuan.rbac.dto.PermissionLevelDto;
import tianyuan.rbac.service.PermissionTreeService;
import tianyuan.rbac.service.RoleTreeService;
import tianyuan.rbac.service.SystemAclService;
import tianyuan.rbac.service.SystemCoreService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色权限树的逻辑
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/31 10:47.
 * @Describution:
 */

@Service
@Slf4j
public class RoleTreeServiceImpl implements RoleTreeService {

    @Autowired
    private SystemCoreService coreService;

    @Autowired
    private PermissionTreeService permissionTreeService;

    @Autowired
    private SystemAclService aclService;
    /**
     * 指定角色，返回权限树的层级结构
     * @param roleId
     * @return
     */
    @Override
    public List<PermissionLevelDto> roleTree(Integer roleId) {
        //获取所有的权限点
        List<SystemAcl> aclAllList = aclService.listAll();
        //1当前用户已分配的权限点
        List<SystemAcl> userAclList = coreService.listCurrentUserAcl();
        //2当前角色已分配的权限点
        List<SystemAcl> roleAclList = coreService.listAclListByRoleId(roleId);

        //3set 存储用户已分配的权限ID，lamda流表达式
        Set<Integer> userAclIdSet= userAclList.stream()
                .map(e -> e.getAclId()).collect(Collectors.toSet());
        //4set 存储角色分配的权限ID
        Set<Integer> roleAclIdSet= roleAclList.stream()
                .map(e -> e.getAclId()).collect(Collectors.toSet());

        // 定义一个list，存储当前角色权限的标记
        List<AclDto> aclDtoList = Lists.newArrayList();

        //6遍历所有权限,核对并集并设置-->（1、包含用户set的，标记 dto的has属性 2、包含角色set的，标记checked属性）
        for (SystemAcl acl : aclAllList){
            AclDto aclDto = AclDto.acl2Acldto(acl);
            if (userAclIdSet.contains(acl.getAclId())){
                aclDto.setChecked(true);
            }
            if (roleAclIdSet.contains(acl.getAclId())){
                aclDto.setHasAcl(true);
            }
            aclDtoList.add(aclDto);
        }
        //转换aclDtoList-->权限模块和树

        return aclList2tree(aclDtoList);
    }

    /**
     * 权限列表转为树状
     * @param aclDtoList
     * @return
     */
    @Override
    public List<PermissionLevelDto> aclList2tree(List<AclDto> aclDtoList) {
        if (CollectionUtils.isEmpty(aclDtoList)){
            return Lists.newArrayList();
        }
        //权限树
        //1调用模块树,获取系统的模块树
        List<PermissionLevelDto> permissionLevelDtoList =  permissionTreeService.permissionTree();

        //遍历aclDtoList，生成一个key为模块id，值为一个权限列表的list key：permissionId--.【acl1,acl2>...】
        Multimap<Integer,AclDto> permissionIdoMultimap = ArrayListMultimap.create();
        for (AclDto acl:aclDtoList){
        //是否有效,有效则封装如map
            if (acl.getAclStatus() == NormalStatusEnum.USED.getCode()){
               permissionIdoMultimap.put(acl.getAclModuleId(),acl);
            }
        }
        //绑定权限点到模块树上
        bindAclWithOrder(permissionLevelDtoList,permissionIdoMultimap);
        return permissionLevelDtoList;
    }

    /**
     *
     * @param permissionLevelDtoList
     * @param map
     */
    public void bindAclWithOrder(List<PermissionLevelDto> permissionLevelDtoList,Multimap<Integer,AclDto> map){
        if (CollectionUtils.isEmpty(permissionLevelDtoList)){
            return ;
        }
        for (PermissionLevelDto dto : permissionLevelDtoList){
            List<AclDto> aclDtoList = (List<AclDto>) map.get(dto.getPermissionId());
            if (CollectionUtils.isNotEmpty(aclDtoList)){
                //排序
                Collections.sort(aclDtoList,aclDtoComparator);
                dto.setAclDtoList(aclDtoList);
            }
            bindAclWithOrder(dto.getPermissionLevelDtoList(),map);
        }
    }

    /**
     * 排序
     */
    public Comparator<AclDto> aclDtoComparator = new Comparator<AclDto>() {
        @Override
        public int compare(AclDto o1, AclDto o2) {
            return o1.getAclSeq() - o2.getAclSeq();
        }
    };
}
