package tianyuan.rbac.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tianyuan.common.baseVO.ResultVO;
import tianyuan.common.enums.ResultEnum;
import tianyuan.common.exception.ParamException;
import tianyuan.common.utils.ResultVOUtil;
import tianyuan.rbac.dto.DepartmentLevelDto;
import tianyuan.rbac.param.DepartmentParam;
import tianyuan.rbac.service.DepartmentInfoService;
import tianyuan.rbac.service.DepartmentTreeService;

import javax.validation.Valid;
import java.util.List;


/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/29 15:44.
 * @Describution:
 */
@RestController
@RequestMapping("/sys/dept")
@Slf4j
@Api(value = "机构部门相关的Api")
public class DepartmentInfoController {

    @Autowired
    private DepartmentInfoService departmentInfoService;
    @Autowired
    private DepartmentTreeService treeService;



    //新增一个部门
    @ApiOperation(value="新增一个部门", notes="新增一个部门")
    @PostMapping("/")
    public ResultVO saveDept(@Valid @RequestBody DepartmentParam param,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("【创建基地信息】参数不正确, param={}", param);
            throw new ParamException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        departmentInfoService.save(param);
        return ResultVOUtil.success();
    }


    @ApiOperation(value="获取机构部门树", notes="获取机构部门树")
    @GetMapping("/tree")
    public ResultVO tree(){
        List<DepartmentLevelDto> departmentLevelDtoList = treeService.deptTree();

        return ResultVOUtil.success(departmentLevelDtoList);
    }


    @ApiOperation(value="更新机构部门", notes="更新机构部门")
    @PutMapping("/")
    public ResultVO updateDept(@Valid @RequestBody DepartmentParam param,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("【修改基地信息】参数不正确, param={}", param);
            throw new ParamException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        departmentInfoService.update(param);

        return ResultVOUtil.success();
    }

    @GetMapping("/{departmentId}")
    @ResponseBody
    public ResultVO deptInfo(@PathVariable(value = "departmentId") int departmentId) {
        departmentInfoService.getOne(departmentId);
        return ResultVOUtil.success(departmentInfoService.getOne(departmentId));
    }


    @DeleteMapping("/{departmentId}")
    @ResponseBody
    public ResultVO delete(@PathVariable(value = "departmentId") int departmentId) {
        departmentInfoService.delete(departmentId);
        return ResultVOUtil.success();
    }
}
