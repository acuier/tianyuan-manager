package tianyuan.rbac.controller;

import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tianyuan.common.baseDTO.PaginationDTO;
import tianyuan.common.baseDTO.SortDTO;
import tianyuan.common.baseVO.ResultVO;
import tianyuan.common.enums.ResultEnum;
import tianyuan.common.exception.ParamException;
import tianyuan.rbac.model.admin.SystemUser;
import tianyuan.common.utils.ResultVOUtil;
import tianyuan.rbac.param.UserParam;
import tianyuan.rbac.service.SystemRoleService;
import tianyuan.rbac.service.SystemUserService;
import tianyuan.rbac.service.UserAclTreeService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/30 10:44.
 * @Describution:
 */
@RestController
@RequestMapping("/sys/user")
@Slf4j
@Api(value = "系统用户")
public class SystemUserController {

    @Autowired
    private SystemUserService userService;

    @Autowired
    private UserAclTreeService userAclTreeService;

    @Autowired
    private SystemRoleService roleService;

    @PostMapping("/")
    @ApiOperation(value = "添加用户")
    public ResultVO saveDept(@Valid @RequestBody UserParam param,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("【创建用户信息】参数不正确, param={}", param);
            throw new ParamException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        userService.save(param);
        return ResultVOUtil.success();
    }

    @PutMapping("/")
    @ApiOperation(value = "更新用户")
    public ResultVO updateDept(@Valid @RequestBody UserParam param,
                               BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("【修改用户信息】参数不正确, param={}", param);
            throw new ParamException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        userService.update(param);
        return ResultVOUtil.success();
    }

    @GetMapping("/{departmentId}/{currentPage}")
    @ApiOperation(value = "部门用户")
    @Cacheable(cacheNames = "user",key = "11111")
    public ResultVO listUsersByDept(HttpServletRequest request, @PathVariable(value = "currentPage")Integer currentPage ,@PathVariable(value = "departmentId") Integer departmentId){

        SortDTO sortDTOS = new SortDTO("DESC","updateTime");
        Page<SystemUser> userPage =userService.listAllByDeptId(departmentId,currentPage,sortDTOS);

        PaginationDTO page =PaginationDTO.builder()
                .currentPage(currentPage)
                .totalPages(userPage.getTotalPages())
                .has_next(userPage.hasNext())
                .has_prev(userPage.hasPrevious())
                .total(userPage.getTotalElements())
                .build();

        return ResultVOUtil.success(userPage.getContent(),page);
    }

    @GetMapping("/{userId}/acls")
    @ApiOperation(value = "用户权限")
    public ResultVO listUserAcl(HttpServletRequest request, Integer currentPage , @PathVariable("userId") Integer userId){
        Map<String, Object> map = Maps.newHashMap();
        //1查到用户的权限 todo://
        map.put("acls",userAclTreeService.userAclTree(userId));
        //2查询用户的角色
        map.put("roles", roleService.listAllByUserId(userId));
        //3,返回map中
        return ResultVOUtil.success(map);
    }
}
