package tianyuan.rbac.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018-04-10 15:42.
 * @Describution:
 */
@Data
public class SearchLogDto {

    private Integer type; // LogType

    private String oldSeg;

    private String newSeg;

    private String operator;

    private Date fromTime;//yyyy-MM-dd HH:mm:ss

    private Date toTime;
}
