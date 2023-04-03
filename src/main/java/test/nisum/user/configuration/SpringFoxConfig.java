package test.nisum.user.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/** SpringFox configuration class.
 * Configures SpringFox to produce the api documentation automatically when
 * calling an endpoint.
 */
@Configuration
@EnableSwagger2
public class SpringFoxConfig {
    /**
     * Bean definition used to configure the components in the project to be
     * mapped in the documentation.
     * @return {@link Docket} object instance with the configuration to
     * generate the api documentation.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("test.nisum.user.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}