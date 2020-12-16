package com.tefloncode.azure;



import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class AzureApplication {
	
	

	public static void main(String[] args) {
		SpringApplication.run(AzureApplication.class, args);
	}
	
	@Bean
	public Docket swaggerConfiguration() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("/api/*"))
				.apis(RequestHandlerSelectors.basePackage("com.tefloncode.azure"))
				.build()
				.apiInfo(apiDetails());
	}
	
	
	private ApiInfo apiDetails() {
		return new ApiInfo(
				"Azure API's",
				"API's used to put in orders and products for Azure's accounting",
				"1..0",
				"Azure",
				new springfox.documentation.service.Contact("Codigators", "http://google.com", "asudam43@gmail.com"),
				"",
				"azure.org",
				Collections.emptyList()
				);
	}

}
