package tianyuan.common.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;
import tianyuan.common.enums.ResultEnum;


/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/20 19:24.
 * @Describution:
 */
@Data
@EqualsAndHashCode
class AcmException extends RuntimeException {

    private Integer code;


    public AcmException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public AcmException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
