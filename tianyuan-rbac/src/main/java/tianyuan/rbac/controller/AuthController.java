package tianyuan.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tianyuan.common.baseVO.ResultVO;
import tianyuan.common.utils.ResultVOUtil;
import tianyuan.rbac.model.admin.SystemUser;
import tianyuan.rbac.service.AuthService;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018-04-20 9:56.
 * @Describution: JWt token 的验证以及注册等
 */
@RestController
@RequestMapping("/sys")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/auth")
    public ResultVO getToken(SystemUser user){
         String username = user.getUserName();
         String password = user.getUserPassword();
         authService.getToken(username,password);

        return ResultVOUtil.success();
    }
}
