package com.blog.project.Blogproject;

import com.blog.project.Blogproject.entity.Role;
import com.blog.project.Blogproject.respository.RoleRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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

public class BlogProjectApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BlogProjectApplication.class, args);
    }

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        roleRepository.save(userRole);
    }
}
