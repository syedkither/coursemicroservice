package com.example.demo;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
//@Profile({ "dev", "qa" }) // move to api gateway // turn off swagger-ui in production
// https://blog.madadipouya.com/2019/12/08/spring-boot-disable-swagger-ui-in-production/
public class SwaggerConfig {
	@Autowired
	private ServletContext servletContext;
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.pathProvider(new CustomPathProvider(servletContext))
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)).paths(PathSelectors.any())
				.build()
				.tags(new Tag("Course", "These endpoints are used to manage the course details of the library", 1));

	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact(
	               "Syed Kither",
	               "https://github.com/syedkither", 
	               "syedrifai81@gmail.com"
	       );
		return new ApiInfoBuilder()
				.title("Course Register API")
				.contact(contact)
				.license("Apache 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")				
				.description("This is the mock Library API can be used to practise the swagger documentation")
				.version("1.0.0").build();
	}   
	//https://gitlab.com/johnjvester/jpa-spec-with-quartz-and-api/commit/b08af5ec6c9f743c5c1438ce6e89b51418857d7c
	@Bean
   	UiConfiguration uiConfig() {
   		return UiConfigurationBuilder.builder()				
   				.defaultModelsExpandDepth(-1).build();				
   	}

}