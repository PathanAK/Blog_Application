package com.blog.project.Blogproject;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring boot Blog App Rest APIs",
				description = "Spring boot Blog App Rest APIs Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Asif",
						email = "thisispathanak@gmail.com",
						url = "https://github.com/PathanAK/Blog_Application"
						),
				license = @License(
						name = "Apache 2.0",
						url = "https://github.com/PathanAK/Blog_Application"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring boot Blog App Rest APIs ExternalDocumentation",
				url = "https://github.com/PathanAK/Blog_Application"
		)
)
public class BlogProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogProjectApplication.class, args);
	}

}
