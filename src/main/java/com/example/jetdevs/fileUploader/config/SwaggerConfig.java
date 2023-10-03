package com.example.jetdevs.fileUploader.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@SecurityScheme(
		name = "Bearer Authentication",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
		)
public class SwaggerConfig {

	@Bean
	public OpenAPI usersOpenAPI() {
		return new OpenAPI().info(new Info().title("Excel Upload API")
				.description("This APIs are designed to manage excel file operations").version("1.0"));
	}
//	
//	@Bean
//	public Docket api() {
//		return new Docket(DocumentationType.SWAGGER_2).select()
//				.apis(RequestHandlerSelectors.basePackage("com.example.jetdevs.fileUploader.controller"))
//				.paths(PathSelectors.any()).build();
//	}
//	
//	@Bean
//    UiConfiguration uiConfig() {
//        return UiConfigurationBuilder.builder()
//          .deepLinking(true)
//          .displayOperationId(false)
//          .defaultModelsExpandDepth(1)
//          .defaultModelExpandDepth(1)
//          .defaultModelRendering(ModelRendering.EXAMPLE)
//          .displayRequestDuration(false)
//          .docExpansion(DocExpansion.NONE)
//          .filter(false)
//          .maxDisplayedTags(null)
//          .operationsSorter(OperationsSorter.ALPHA)
//          .showExtensions(false)
//          .tagsSorter(TagsSorter.ALPHA)
//          .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
//          .validatorUrl(null)
//          .build();
//    }
}
