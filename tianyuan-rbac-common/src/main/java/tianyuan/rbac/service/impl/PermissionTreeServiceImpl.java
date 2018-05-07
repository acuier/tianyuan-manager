package tianyuan.rbac.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tianyuan.common.utils.LevelUtil;
import tianyuan.rbac.dto.PermissionDto;
import tianyuan.rbac.dto.PermissionLevelDto;
import tianyuan.rbac.service.PermissionTreeService;
import tianyuan.rbac.service.SystemPermissionService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/29 19:41.
 * @Describution:
 */

@Service
@Slf4j
public class PermissionTreeServiceImpl implements PermissionTreeService {


    @Autowired
    private SystemPermissionService permissionService;

    @Override
    public List<PermissionLevelDto> permissionTree() {
        //获取所有数据
        List<PermissionDto> permissionList = permissionService.findAll();

        //封装：数据转换为dto
        List<PermissionLevelDto> dtoList = Lists.newArrayList();
        for (PermissionDto permission : permissionList) {
            PermissionLevelDto dto = PermissionLevelDto.permissionDto2PermissionLevelDto(permission);
            dtoList.add(dto);
        }


        return permissionList2Tree(dtoList);
    }

    //核心组装
    public List<PermissionLevelDto> permissionList2Tree(List<PermissionLevelDto> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Lists.newArrayList();
        }
        //k:level-> [dept,dept2....]
        Multimap<String, PermissionLevelDto> levelDTOMultiMap = ArrayListMultimap.create();

        List<PermissionLevelDto> rootList = Lists.newArrayList();

        for (PermissionLevelDto dto : dtoList) {
            levelDTOMultiMap.put(dto.getPermissionLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getPermissionLevel())) {
                rootList.add(dto);
            }
        }
        //排序，seq
        Collections.sort(rootList, permissionLevelDTOComparator);

        //递归处理，生成树
        transformPermissionTree(rootList, LevelUtil.ROOT, levelDTOMultiMap);

        return rootList;
    }

    public void transformPermissionTree(List<PermissionLevelDto> permissionLeveList, String level, Multimap<String, PermissionLevelDto> multimap) {
        for (int i = 0; i < permissionLeveList.size(); i++) {
            //遍历每个元素
            PermissionLevelDto dto = permissionLeveList.get(i);
            //处理当前层及的数据
            String nextLevel = LevelUtil.calculateLevel(level, dto.getPermissionId());
            //处理下一层
            List<PermissionLevelDto> tempList = (List<PermissionLevelDto>) multimap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempList)) {
                //排序
                Collections.sort(tempList, permissionLevelDTOComparator);
                //设置下一层部门
                dto.setPermissionLevelDtoList(tempList);
                //进入下一层处理
                transformPermissionTree(tempList, nextLevel, multimap);
            }
        }
    }

    public Comparator<PermissionLevelDto> permissionLevelDTOComparator = new Comparator<PermissionLevelDto>() {
        @Override
        public int compare(PermissionLevelDto o1, PermissionLevelDto o2) {
            return o1.getPermissionSort() - o2.getPermissionSort();
        }
    };
}
