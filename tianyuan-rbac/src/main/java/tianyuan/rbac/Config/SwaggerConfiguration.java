package tianyuan.rbac.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/4/2 10:48.
 * @Describution:
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

        @Bean
        public Docket createRestApi() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage(""))
                    .paths(PathSelectors.any())
                    .build();
        }

        private ApiInfo apiInfo() {
            return new ApiInfoBuilder()
                    .title("TianYuan Tech co. APIs")
                    .description("RBAC Apis")
                    .termsOfServiceUrl("http://localhost:8999/")
                    .contact("acuier@163.com")
                    .version("1.0")
                    .build();
        }
}
