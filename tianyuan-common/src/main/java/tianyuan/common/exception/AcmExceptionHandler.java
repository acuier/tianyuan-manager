package tianyuan.common.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import tianyuan.common.baseVO.ResultVO;
import tianyuan.common.utils.ResultVOUtil;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/22 18:27.
 * @Describution:
 */
@ControllerAdvice
public class AcmExceptionHandler {
//    @Autowired
//    private ProjectUrlConfig projectUrlConfig;
//
//    //拦截登录异常
//    //http://sell.natapp4.cc/sell/wechat/qrAuthorize?returnUrl=http://sell.natapp4.cc/sell/seller/login
//    @ExceptionHandler(value = SellerAuthorizeException.class)
//    public ModelAndView handlerAuthorizeException() {
//        return new ModelAndView("redirect:"
//                .concat(projectUrlConfig.getWechatOpenAuthorize())
//                .concat("/sell/wechat/qrAuthorize")
//                .concat("?returnUrl=")
//                .concat(projectUrlConfig.getSell())
//                .concat("/sell/seller/login"));
//    }

    @ExceptionHandler(value = AcmException.class)
    @ResponseBody
   public ResultVO handlerAcmException(AcmException e){
        return ResultVOUtil.failure(e.getCode(),e.getMessage());
    }

    /**
     * 捕获异常，替换系统返回值
     */
    @ExceptionHandler(value = ResponseStatusException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handlerResponseStatusException(){

    }
}
