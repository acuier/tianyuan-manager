package tianyuan.common.exception;

import tianyuan.common.enums.ResultEnum;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/29 17:04.
 * @Describution:
 */
public class ParamException extends RuntimeException {


    private Integer code;

    public Integer getCode() {
        return code;
    }

    public ParamException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public ParamException(Integer code, String message) {
        super(message);
        this.code = code;
    }


    public ParamException() {
        super();
    }

    public ParamException(String message) {
        super(message);
    }

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamException(Throwable cause) {
        super(cause);
    }

    protected ParamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
