package app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.builders.PathSelectors;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(getApiInfo())	  
        .select()
        .apis(RequestHandlerSelectors.basePackage("app"))
        .paths(PathSelectors.any())
        .build();
    }
    
    private ApiInfo getApiInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "API REST",
                "Sistema de Processos",
                "1.1",
                "",
                new Contact("Java-Spring-Boot", "", ""),
                "Danilo Meneghel",
                "http://danilomeneghel.github.io", new ArrayList<>()
        );

        return apiInfo;
    }
}