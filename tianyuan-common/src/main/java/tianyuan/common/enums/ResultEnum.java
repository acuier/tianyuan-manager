package tianyuan.common.enums;

import lombok.Getter;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/29 16:09.
 * @Describution: 返回错误码（需要分离）
 */
@Getter
public enum ResultEnum {

    SUCCESS(0,"请求成功！"),
    TOKEN_IS_VALID(2000,"令牌解析错误！可能被篡改或者失效！"),
    /**
     * 权限验证返回值
     *2000
     */
    USER_NOT_ALLOWED(2001,"该用户没有被授权操作！"),
    BAD_REQUEST(400,"无效的请求"),
    NOT_FOUND(404,"请求不存在"),
    METHOD_NOT_ALLOWED(405,"请求的方法错误"),
    INTERNAL_SERVER_ERROR(500,"未知的内部错误"),
    UNSUPPORTED_MEDIA_TYPE(415,"错误的媒体格式"),

    //RBAC参数
    NOT_ALLOWED(1060,"您没有权限使用该功能！"),
    NEED_ADMIN(1061,"需要Admin（管理员）角色才能使用！"),


    //部门参数1010
    DUPLICATE_DEPT_NAMES(1010,"该机构/部门名称或简称已被占用！"),
    DUPLICATE_DEPT_NICKNAME(1011,"该机构/部门简称已被占用！"),
    DUPLICATE_DEPT_PHONE(1012,"该电话号码已被占用！"),
    DUPLICATE_DEPT_EMAIL(1013,"该邮箱地址已被占用！"),
    DEPT_NOT_EXIST(1014,"该机构/部门不存在！"),
    DEPART_HAS_USER(1015,"该机构/部门下有用户数据！"),
    DEPART_HAS_CHILD(1016,"该机构/部门拥有子机构/部门！"),

    //用户参数
    DUPLICATE_USER_NAME(1020,"该用户名称已被占用！"),
    DUPLICATE_USER_PHONE(1022,"该电话号码已被占用！"),
    DUPLICATE_USER_EMAIL(1023,"该邮箱地址已被占用！"),
    USER_NOT_EXIST(1024,"该用户不存在！"),

    //角色参数
    DUPLICATE_ROLE_NAME(1030,"该角色名称已被占用！"),
    ROLE_NOT_EXIST(1031,"该角色不存在！"),
    //权限参数
    //1000 参数相关错误码
    PARAM_ERROR(1040, "参数不正确!未能通过数据校验！"),
    RETURN_NULL(1041,"返回值为空，请求的数据不存在！"),
    DUPLICATE_ACL_NAME(1042,"该权限名称已被占用！"),
    ACL_NOT_EXIST(1044,"该权限不存在！"),

    //模块参数
    DUPLICATE_MODULE_NAME(1050,"该模块名称已被占用！"),
    MODULE_HAS_CHILD(1051,"该模块存在子模块！"),
    MODULE_HAS_ACL(1052,"该模块存在权限点！"),
    MODULE_NOT_EXIST(1054,"该模块不存在！"),
    ;
    private Integer code;
    private String message;


    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
