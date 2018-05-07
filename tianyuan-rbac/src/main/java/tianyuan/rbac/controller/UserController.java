package tianyuan.rbac.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tianyuan.common.baseVO.ResultVO;
import tianyuan.rbac.service.SystemUserService;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/30 16:28.
 * @Describution:
 */

@RestController
@RequestMapping("/user")
@Api(value = "用户登录登出")
public class UserController {

    @Autowired
    private SystemUserService userService;

    /**
     * 登录
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public ResultVO login(){
        return null;
    }

    @GetMapping("/me")
    @ApiOperation(value = "当前用户")
    public Object getCurrentUser(Authentication authentication){
//            , @AuthenticationPrincipal UserDetails user){
//        SecurityContextHolder.getContext().getAuthentication();
//        return user;
        return authentication;
    }
    /**
     * 注销
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "登出")
    public ResultVO logout(){
        return null;
    }
    /**
     * 获取自己的详情
     * @return
     */
    @PostMapping("/")
    @ApiOperation(value = "用户个人详情")
    public ResultVO getUser(){
        return null;
    }

    /**
     * 更新个人信息
     * @return
     */
    @PutMapping("/")
    @ApiOperation(value = "用户个人详情更新")
    public ResultVO update(){
        return null;
    }


    //重置密码
    //修改密码
    //绑定手机和邮件
}
