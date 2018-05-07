package tianyuan.rbac.service;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018-04-23 16:52.
 * @Describution:
 */
public interface AuthService {

 String getToken(String userName,String password);

 String refreshToken(String token);
}
