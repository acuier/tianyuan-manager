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
import tianyuan.rbac.dto.PermissionLevelDto;
import tianyuan.rbac.param.PermissionParam;
import tianyuan.rbac.service.PermissionTreeService;
import tianyuan.rbac.service.SystemPermissionService;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/30 17:00.
 * @Describution:
 */
@RestController
@Slf4j
@RequestMapping("/sys/permission")
@Api(value = "模块相关")
public class PermissionController {


    @Autowired
    private SystemPermissionService permissionService;
    @Autowired
    private PermissionTreeService treeService;

    @PostMapping("/")
    @ApiOperation(value = "新增模块")
    public ResultVO savePermission(@Valid @RequestBody PermissionParam param,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("【创建权限模块信息】参数不正确, param={}", param);
            throw new ParamException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        permissionService.save(param);
        return ResultVOUtil.success();
    }

    @GetMapping("/tree")
    @ApiOperation(value = "模块树")
    public ResultVO tree(){
        List<PermissionLevelDto> permissionLevelDtoList = treeService.permissionTree();
        return ResultVOUtil.success(permissionLevelDtoList);
    }

    @PutMapping("/")
    @ApiOperation(value = "更新模块")
    public ResultVO updatePermission(@Valid @RequestBody PermissionParam param,
                               BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("【修改权限模块信息】参数不正确, param={}", param);
            throw new ParamException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        permissionService.update(param);
        return ResultVOUtil.success();
    }

    @DeleteMapping("/{permissionId}")
    @ApiOperation(value = "删除模块")
    public ResultVO delete(@PathVariable(value = "permissionId") Integer permissionId){

        permissionService.delete(permissionId);
        return ResultVOUtil.success();
    }

}
