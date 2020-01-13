package org.lucasbernardo.healthcareinstitution;

import com.google.common.base.Predicates;
import static io.swagger.annotations.ApiKeyAuthDefinition.ApiKeyLocation.HEADER;
import static java.util.Collections.singletonList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("healthcareinstitutions")
        .securitySchemes(singletonList(new ApiKey("JWT", AUTHORIZATION, HEADER.name())))
        .securityContexts(singletonList(
            SecurityContext.builder()
                .securityReferences(
                    singletonList(SecurityReference.builder()
                        .reference("JWT")
                        .scopes(new springfox.documentation.service.AuthorizationScope[0])
                        .build()
                    )
                )
                .build())
        )
        .select()
        .apis(RequestHandlerSelectors.basePackage("org.lucasbernardo.healthcareinstitution.controller"))
        .paths(Predicates.or(PathSelectors.ant("/healthcareinstitutions/**"), PathSelectors.ant("/exams/**")))
        .build();
  }
}
