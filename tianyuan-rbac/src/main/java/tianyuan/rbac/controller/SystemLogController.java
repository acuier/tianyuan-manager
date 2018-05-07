package tianyuan.rbac.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tianyuan.rbac.service.SystemLogService;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/4/1 11:15.
 * @Describution:
 */

@RestController
@RequestMapping("/sys/log")
@Slf4j
@Api(value = "权限操作日志")
public class SystemLogController {

    @Autowired
    private SystemLogService logService;

    //分页获取日志
//    @GetMapping("/")
//    public ResultVO


}
