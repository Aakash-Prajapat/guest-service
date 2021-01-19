package com.epam.incubation.service.guestprofile.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

	@Bean
	public Docket api() {

		SecurityReference securityReference = SecurityReference.builder()
				.reference("basicAuth")
				.scopes(new AuthorizationScope[0])
				.build();

		List<SecurityReference> reference = new ArrayList<>(1);
		reference.add(securityReference);

		List<SecurityContext> securityContexts = new ArrayList<>(1);
		securityContexts.add(SecurityContext.builder().securityReferences(reference).build());

		List<SecurityScheme> auth = new ArrayList<>(1);
		auth.add(new BasicAuth("basicAuth"));
		
		return new Docket(DocumentationType.SWAGGER_2)
				.securitySchemes(auth)
				.securityContexts(securityContexts)
				.select()
				.apis(RequestHandlerSelectors
						.basePackage("com.epam.incubation.service.guestprofile"))
				.paths(PathSelectors.any()).build().apiInfo(apiEndPointsInfo());
	}
	
	private ApiInfo apiEndPointsInfo() {
		return new ApiInfoBuilder().title("Guest Profile Service")
				.description("Guest Profile Service")
				.contact(new Contact("Aakash Prajapat", "", "aakash_prajapat@epam.com"))
				.version("1.0.0")
				.build();
	}
}
