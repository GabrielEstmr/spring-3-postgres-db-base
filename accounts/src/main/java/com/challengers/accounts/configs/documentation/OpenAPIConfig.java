package com.challengers.accounts.configs.documentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"!test", "!test-container"})
public class OpenAPIConfig {

  @Value("${app-account-challenger.openapi.dev-url}")
  private String devUrl;

  @Value("${app-account-challenger.openapi.prod-url}")
  private String prodUrl;

  @Bean
  public OpenAPI myOpenAPI() {
    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Server URL in Development environment");

    Server prodServer = new Server();
    prodServer.setUrl(prodUrl);
    prodServer.setDescription("Server URL in Production environment");

    Contact contact = new Contact();
    contact.setEmail("gabriel.estmr@gmail.com");
    contact.setName("Gabriel Rodrigues");
    contact.setUrl("https://github.com/GabrielEstmr");
    License mitLicense =
        new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");
    Info info =
        new Info()
            .title("App-Account-Challenger")
            .version("1.0")
            .contact(contact)
            .description("Application Manage Account and its Transactions.")
            .termsOfService("https://github.com/GabrielEstmr")
            .license(mitLicense);

    return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
  }
}
