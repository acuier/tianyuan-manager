package tianyuan.common.baseVO;

import lombok.Data;
import tianyuan.common.baseDTO.PaginationDTO;

import java.io.Serializable;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/29 11:05.
 * @Describution:统一返回格式
 */
@Data
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = 1729919442887193648L;


    /**返回码*/
    private Integer code;

    /**返回信息*/
    private String msg;

    private PaginationDTO page;

    /**返回的具体内容对象*/
    private T data;


}
