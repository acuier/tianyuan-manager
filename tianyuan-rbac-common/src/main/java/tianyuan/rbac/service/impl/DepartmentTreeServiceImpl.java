package tianyuan.rbac.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tianyuan.common.utils.LevelUtil;
import tianyuan.rbac.dto.DepartmentLevelDto;
import tianyuan.rbac.model.admin.DepartmentInfo;
import tianyuan.rbac.service.DepartmentInfoService;
import tianyuan.rbac.service.DepartmentTreeService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 机构部门树生成逻辑
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/29 19:41.
 * @Describution:
 */

@Service
@Slf4j
public class DepartmentTreeServiceImpl implements DepartmentTreeService  {

//    @Autowired
//    private DepartmentInfoDao departmentInfoDao;

    @Autowired
    private DepartmentInfoService departmentInfoService;


    /**
     * 机构部门树生成
     * @return
     */
    @Override
    public List<DepartmentLevelDto> deptTree() {
        //获取所有数据
        List<DepartmentInfo> departmentInfoList = departmentInfoService.findAll();

        //封装：数据转换为dto
        List<DepartmentLevelDto> dtoList = Lists.newArrayList();
        for (DepartmentInfo departmentInfo : departmentInfoList) {
            DepartmentLevelDto dto = DepartmentLevelDto.departmentInfo2DepartmentInfoDto(departmentInfo);
            dtoList.add(dto);
        }


        return deptList2Tree(dtoList);
    }

    //核心组装
    private List<DepartmentLevelDto> deptList2Tree(List<DepartmentLevelDto> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Lists.newArrayList();
        }
        //k:level-> [dept,dept2....]
        Multimap<String, DepartmentLevelDto> levelDTOMultiMap = ArrayListMultimap.create();

        List<DepartmentLevelDto> rootList = Lists.newArrayList();

        for (DepartmentLevelDto dto : dtoList) {
            levelDTOMultiMap.put(dto.getDepartmentLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getDepartmentLevel())) {
                rootList.add(dto);
            }
        }
        //排序，seq
        Collections.sort(rootList, departmentLevelDTOComparator);
//        Collections.sort(rootList, new Comparator<DepartmentLevelDto>() {
//            @Override
//            public int compare(DepartmentLevelDto o1, DepartmentLevelDto o2) {
//                return o1.getDepartmentSeq() - o2.getDepartmentSeq();
//            }
//        });
        //递归处理，生成树
        transformDeptTree(rootList, LevelUtil.ROOT, levelDTOMultiMap);

        return rootList;
    }

    //递归处理，生成树
    public void transformDeptTree(List<DepartmentLevelDto> deptLeveList, String level, Multimap<String, DepartmentLevelDto> multimap) {
        for (int i = 0; i < deptLeveList.size(); i++) {
            //遍历每个元素
            DepartmentLevelDto dto = deptLeveList.get(i);
            //处理当前层及的数据
            String nextLevel = LevelUtil.calculateLevel(level, dto.getDepartmentId());
            //处理下一层
            List<DepartmentLevelDto> tempList = (List<DepartmentLevelDto>) multimap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempList)) {
                //排序
                Collections.sort(tempList, departmentLevelDTOComparator);
                //设置下一层部门
                dto.setDepartmentInfoList(tempList);
                //进入下一层处理
                transformDeptTree(tempList, nextLevel, multimap);
            }
        }
    }

    public Comparator<DepartmentLevelDto> departmentLevelDTOComparator = new Comparator<DepartmentLevelDto>() {
        @Override
        public int compare(DepartmentLevelDto o1, DepartmentLevelDto o2) {
            return o1.getDepartmentSeq() - o2.getDepartmentSeq();
        }
    };
}
