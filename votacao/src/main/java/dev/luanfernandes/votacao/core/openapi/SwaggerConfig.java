package dev.luanfernandes.votacao.core.openapi;

import com.fasterxml.classmate.TypeResolver;
import dev.luanfernandes.votacao.api.exceptions.handler.StandardError;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket apiDocketV1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("V1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("dev.luanfernandes.votacao.api.v1"))
                .build()
                .useDefaultResponseMessages(false)
                .additionalModels(new TypeResolver().resolve(StandardError.class))
                .globalResponses(HttpMethod.GET, getResponseMessages())
                .apiInfo(apiInfoV1())
                .tags(
                        new Tag("Associado", "Serviço para Associado",0),
                        new Tag("Pauta", "Serviço para Pauta", 1),
                        new Tag("Sessão", "Serviço de Sessão", 2),
                        new Tag("Voto", "Serviço de Voto", 3)
                );
    }

    private List<Response> getResponseMessages() {
        return List.of(
                new ResponseBuilder()
                        .code("400")
                        .description("Requisição inválida. Erro do cliente")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(r -> r.model(m -> m.referenceModel(rm -> rm.key(k -> k.qualifiedModelName(
                                q -> q.namespace(StandardError.class.getPackageName())
                                        .name("Problema")))))).build()
        );
    }

    private ApiInfo apiInfoV1() {
        return new ApiInfo(
                "Sistema Votação Api", "", "v1.0", "",
                new Contact("Luan Fernandes", "https://linkedin.com/in/souluanf",
                        "souluanf@icloud.com"), "MIT",
                "https://github.com/souluanf/sistema-votacao/blob/main/LICENSE",
                Collections.emptyList());
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/swagger-ui/index.html");
        registry.addRedirectViewController("/swagger-ui", "/swagger-ui/index.html");
    }

}