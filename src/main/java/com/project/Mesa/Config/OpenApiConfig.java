package com.project.Mesa.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {
	
	@Bean
	public OpenAPI openApi() {
		final String securitySchemeName = "bearerAuth";
		
		Contact contact = new Contact();
        contact.setName("Paulo Daniel");
        contact.setEmail("daniel.026@hotmail.com");

        License mitLicense = new License();
        mitLicense.name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info();
        info.title("Ferramenta Administrativa");
        info.version("2.0.0");
        info.contact(contact);
        info.description("API Rest para mostrar campanhas de uma planilha online. ");
        info.license(mitLicense);

    return new OpenAPI()
                .info(info)

                // 🔐 diz que a API usa autenticação
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))

                // 🔐 define como é o token (Bearer JWT)
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
	}
}
