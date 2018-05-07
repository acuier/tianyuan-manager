package tianyuan;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/29 18:22.
 * @Describution:
 */
@SpringBootApplication
@EnableSwagger2
@EnableCaching
public class RbacApplication {
    public static void main(String[] args) {
        SpringApplication.run(RbacApplication.class, args);
    }

}
