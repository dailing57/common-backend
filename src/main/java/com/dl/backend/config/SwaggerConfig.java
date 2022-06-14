package com.dl.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//访问测试 ：http://localhost:10001/swagger-ui.html ，可以看到swagger的界面；
@Configuration
@EnableSwagger2
@EnableWebMvc //加这个解决集成swagger后启动失败的bug
public class SwaggerConfig {
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("博客系统API接口文档")
                .description("描述")
                .version("1.0.0")
                .build();
    }
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dl.backend.controller")) //这里写的是API接口所在的包位置
                .paths(PathSelectors.any())
                .build();
    }
}