package tianyuan.rbac.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/4/2 14:12.
 * @Describution:
 */
@Getter
@Setter
@ToString
public class SearchLogParam {

    private Integer type; // LogType

    private String oldValue;

    private String newValue;

    private String operator;

    private String fromTime;//yyyy-MM-dd HH:mm:ss

    private String toTime;
}