package tianyuan.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tianyuan.common.enums.ResultEnum;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/22 18:42.
 * @Describution:
 * 抛出此类异常，用于替换 系统的返回码 200
 */
@Data
@EqualsAndHashCode
public class ResponseStatusException extends RuntimeException {
    private Integer code;

    public ResponseStatusException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
