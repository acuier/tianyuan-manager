package tianyuan.rbac.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tianyuan.common.baseVO.ResultVO;
import tianyuan.common.enums.NormalStatusEnum;
import tianyuan.common.enums.ResultEnum;
import tianyuan.common.exception.ParamException;
import tianyuan.common.utils.ResultVOUtil;
import tianyuan.common.utils.StringUtil;
import tianyuan.rbac.model.admin.SystemRole;
import tianyuan.rbac.model.admin.SystemUser;
import tianyuan.rbac.param.RoleParam;
import tianyuan.rbac.service.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/31 9:59.
 * @Describution:
 */
@RestController
@Slf4j
@RequestMapping("/sys/role")
@Api(value = "角色相关")
public class SystemRoleController {

    @Autowired
    private SystemRoleService roleService;

    @Autowired
    private RoleTreeService roleTreeService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private SystemUserService userService;

    @Autowired
    private SystemRoleAclService roleAclService;

    /**
     * 新增
     * @param param
     * @param bindingResult
     * @return
     */
    @PostMapping("")
    @ApiOperation(value = "新增角色")
    public ResultVO saveRole(@Valid @RequestBody RoleParam param,
                             BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            log.error("【创建角色信息】参数不正确, param={}", param);
            throw new ParamException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        roleService.save(param);
        return ResultVOUtil.success();
    }

    /**
     * 更新
     * @param param
     * @param bindingResult
     * @return
     */
    @PutMapping("/")
    @ApiOperation(value = "更新角色")
    public ResultVO updateRole(@Valid @RequestBody RoleParam param,
                             BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            log.error("【更新角色信息】参数不正确, param={}", param);
            throw new ParamException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        roleService.save(param);
        return ResultVOUtil.success();
    }

    /**
     * 列表
     * @return
     */
    @GetMapping("/")
    @ApiOperation(value = "角色列表")
    public ResultVO listRole(){
        return ResultVOUtil.success(roleService.listAll());
    }

    /**
     * 详情
     * @param roleId
     * @return
     */
    @GetMapping("/{roleId}")
    @ApiOperation(value = "角色详情")
    public ResultVO listRole(@PathVariable(value = "roleId") int roleId){
        return ResultVOUtil.success(roleService.getByID(roleId));
    }

    /**
     * 某用户的角色列表
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    @ApiOperation(value = "user的角色")
    public ResultVO listRoleByUserId(@PathVariable(value = "userId") int userId){
        //用户的角色列表
        List<SystemRole> roleList = roleService.listAllByUserId(userId);
        return ResultVOUtil.success(roleList);
    }
    /**
     * 权限树:
     * @param roleId
     * @return
     */
    @GetMapping("/{roleId}/tree")
    @ApiOperation(value = "角色的权限树")
    public ResultVO treeRole(@PathVariable(value = "roleId") Integer roleId){

        return ResultVOUtil.success(roleTreeService.roleTree(roleId));
    }

    /**
     * 获取当前role的用户，分开：包含的和不包含的
     * @param roleId
     * @return
     */
    @GetMapping("/{roleId}/user")
    @ApiOperation(value = "角色的用户列表")

    public ResultVO users(@PathVariable(value = "roleId") Integer roleId){
        //已选的用户
        List<SystemUser> usedList =  userRoleService.listByRoleId(roleId);
        //全部用户
        List<SystemUser> userList =  userService.listAll();
        //未选择用户
        List<SystemUser> unUsedList = Lists.newArrayList();
        Set<Integer> usedSet = usedList.stream().map(e -> e.getUserId()).collect(Collectors.toSet());

        for (SystemUser user : userList){
           if (user.getUserStatus() == NormalStatusEnum.USED.getCode() && !usedSet.contains(user.getUserId())){

               unUsedList.add(user);

           }
        }
        //如果已选中的不再显示，可增加过滤，如下：
        //usedList = unUsedList.stream().filter(user -> user.getUserStatus() !=1 ).collect(Collectors.toList());
        Map<String,List<SystemUser>> map = Maps.newHashMap();

        map.put("selected",usedList);
        map.put("unselected",unUsedList);

        return ResultVOUtil.success(map);
    }

    //角色的权限改变
    @PutMapping("/acls")
    @ApiOperation(value = "修改角色的权限")
    public ResultVO changeAcls(@RequestParam("roleId") int roleId, @RequestParam(value = "aclIds", required = false, defaultValue = "") String aclIds) {
        //获取Id,前端传来的字符串，解析为列表
        List<Integer> aclIdList = StringUtil.splitToListInt(aclIds);
        //批量操作
        roleAclService.changeRoleAcl(roleId,aclIdList);

        return ResultVOUtil.success();
    }
    //用户的角色改变

    /**
     *
     * @param roleId
     * @param userIds
     * @return
     */
    @PutMapping("/users")
    @ApiOperation(value = "修改角色的用户")
    public ResultVO changeUsers(@RequestParam("roleId") int roleId, @RequestParam(value = "userIds", required = false, defaultValue = "") String userIds) {
        //获取Id
        List<Integer> userIdList = StringUtil.splitToListInt(userIds);
        //批量操作
        userRoleService.updateRoleUserList(roleId,userIdList);

        return ResultVOUtil.success();
    }


}
