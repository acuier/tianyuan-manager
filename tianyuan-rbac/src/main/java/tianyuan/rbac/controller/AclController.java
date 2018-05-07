package tianyuan.rbac.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tianyuan.common.baseDTO.PaginationDTO;
import tianyuan.common.baseDTO.SortDTO;
import tianyuan.common.baseVO.ResultVO;
import tianyuan.common.enums.ResultEnum;
import tianyuan.common.exception.ParamException;
import tianyuan.rbac.model.admin.SystemAcl;
import tianyuan.rbac.model.admin.SystemRole;
import tianyuan.common.utils.ResultVOUtil;
import tianyuan.rbac.param.AclParam;
import tianyuan.rbac.service.SystemAclService;
import tianyuan.rbac.service.SystemRoleService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/31 8:20.
 * @Describution:
 */
@RestController
@Slf4j
@RequestMapping("/sys/acl")
@Api(value = "权限点")
public class AclController {

    @Resource
    private SystemAclService aclService;

    @Resource
    private SystemRoleService roleService;

    @PostMapping("/")
    @ApiOperation(value = "新增权限点")
    public ResultVO saveAcl(@Valid @RequestBody AclParam param,
                            BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("【创建权限点信息】参数不正确, param={}", param);
            throw new ParamException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        aclService.save(param);

        return ResultVOUtil.success();
    }

    @PutMapping("/")
    @ApiOperation(value = "更新权限点")
    public ResultVO updateAcl(@Valid @RequestBody AclParam param,
                            BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("【创建权限点信息】参数不正确, param={}", param);
            throw new ParamException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        aclService.update(param);

        return ResultVOUtil.success();
    }

    @GetMapping("/{aclModuleId}/{currentPage}")
    @ApiOperation(value = "分页显示模块下权限点")
    public ResultVO<SystemAcl> list(HttpServletRequest request, @PathVariable(
            value = "currentPage") Integer currentPage ,@PathVariable("aclModuleId") Integer aclModuleId){

        SortDTO sortDTOS = new SortDTO("ASC","aclSeq");
        Page<SystemAcl> aclPage =aclService.listByAclModuleID(aclModuleId,currentPage,sortDTOS);

        PaginationDTO page =PaginationDTO.builder()
                .currentPage(currentPage)
                .totalPages(aclPage.getTotalPages())
                .has_next(aclPage.hasNext())
                .has_prev(aclPage.hasPrevious())
                .total(aclPage.getTotalElements())
                .build();

        return ResultVOUtil.success(aclPage.getContent(),page);
    }

    @GetMapping("/{aclId}")
    @ApiOperation(value = "权限点用户列表")
    public ResultVO listUserAcl(HttpServletRequest request,@PathVariable("aclId") Integer aclId){
        Map<String,Object> map = new HashMap<>();
        //查询权限所属的角色
        List<SystemRole> roleList = roleService.listAllByAclId(aclId);
        map.put("roles",roleList );
        //查到权限分配到的用户
        List<Integer> roleIdList = roleService.listRoleIdByAclId(aclId);
        map.put("users",roleService.listUserListByRoleList(roleIdList));
        //3,返回map中
        return ResultVOUtil.success(map);

    }
}
