package tianyuan.common.utils;

import org.apache.commons.lang3.StringUtils;



/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/29 17:32.
 * @Describution:  level格式: 0.1.1.2
 */
public class LevelUtil {
    public final static String SEPARATOR = ".";

    public final static String ROOT = "0";

    public static String calculateLevel(String parentLevel, int parentId){

        if (StringUtils.isBlank(parentLevel)){
            return ROOT;
        } else {

         return StringUtils.join(parentLevel,SEPARATOR,parentId);
        }

    }
}
