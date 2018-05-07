package tianyuan.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import tianyuan.common.baseVO.ResultVO;
import tianyuan.common.enums.ResultEnum;
import tianyuan.common.utils.ResultVOUtil;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018-04-14 10:37.
 * @Describution:
 */
@ControllerAdvice
@Slf4j
public class ResponseExceptionHandle {

    /**
     *
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResultVO responseStatusException(
            ResultEnum resultEnum) {
        log.error("JWT", resultEnum.getMessage());
        return ResultVOUtil.failure(resultEnum);
    }
    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultVO handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        log.error("could_not_read_json...", e);
//        return new Response().failure("could_not_read_json");
        return ResultVOUtil.failure(ResultEnum.BAD_REQUEST.getCode(),e.getMessage());
    }

    /**
     * 405 - Method Not Allowed。HttpRequestMethodNotSupportedException
     * 是ServletException的子类,需要Servlet API支持
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultVO handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        log.error("request_method_not_supported...", e);
        return ResultVOUtil.failure(ResultEnum.METHOD_NOT_ALLOWED.getCode(),e.getMessage());
    }

    /**
     * 415 - Unsupported Media Type。HttpMediaTypeNotSupportedException
     * 是ServletException的子类,需要Servlet API支持
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
    public ResultVO handleHttpMediaTypeNotSupportedException(Exception e) {
        log.error("content_type_not_supported...", e);
        return ResultVOUtil.failure(ResultEnum.UNSUPPORTED_MEDIA_TYPE.getCode(),e.getMessage());
    }



    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResultVO handleException(Exception e) {
        log.error("Internal Server Error...", e);
        return ResultVOUtil.failure(ResultEnum.INTERNAL_SERVER_ERROR.getCode(),e.getMessage());
    }
}
