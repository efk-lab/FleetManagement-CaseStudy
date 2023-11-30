package com.mycomp.customermanager.conf.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	
	private final String API_TITLE = "CustomerManager";
	
	private final String API_DESCRIPTION = "CustomerManager Document";
	
	private final String API_LICENSE = "Apache 2.0";
	
	private final String API_LICENSE_URL = "http://www.apache.org/licenses/LICENSE-2.0.html";
	
	private final String API_VERSION ="1.12.3";
	

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.mycomp.customermanager"))
                .paths(PathSelectors.regex("/.*"))
                .build().apiInfo(apiEndPointsInfo());

    }
    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title(API_TITLE)
                .description(API_DESCRIPTION)
                .license(API_LICENSE)
                .licenseUrl(API_LICENSE_URL)
                .version(API_VERSION)
                .build();
    }
}
