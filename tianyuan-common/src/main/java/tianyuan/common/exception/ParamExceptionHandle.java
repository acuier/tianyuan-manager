package tianyuan.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import tianyuan.common.baseVO.ResultVO;
import tianyuan.common.utils.ResultVOUtil;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/30 10:21.
 * @Describution:
 */
@ControllerAdvice
public class ParamExceptionHandle {


    @ExceptionHandler(value = ParamException.class)
    @ResponseBody
    public ResultVO handlerParamException(ParamException e){
        return ResultVOUtil.failure(e.getCode(),e.getMessage());
    }

}
