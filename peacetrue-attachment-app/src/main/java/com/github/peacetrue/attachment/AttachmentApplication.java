package com.github.peacetrue.attachment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.spring.formatter.date.AutomaticDateFormatter;
import com.github.peacetrue.spring.formatter.date.AutomaticLocalDateFormatter;
import com.github.peacetrue.spring.formatter.date.AutomaticLocalDateTimeFormatter;
import com.github.peacetrue.spring.formatter.date.AutomaticTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.ReactivePageableHandlerMethodArgumentResolver;
import org.springframework.data.web.ReactiveSortHandlerMethodArgumentResolver;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author xiayx
 */
@SpringBootApplication
public class AttachmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttachmentApplication.class, args);
    }

    @ControllerAdvice
    public static class StringTrimmerControllerAdvice {
        @InitBinder
        public void registerCustomEditors(WebDataBinder binder) {
            binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        }
    }

    @Configuration
    @EnableWebFlux
    public static class WebConfig implements WebFluxConfigurer {

        @Autowired
        private ObjectMapper objectMapper;

        @Override
        public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
            configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
            configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
        }

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("*")
                    .allowedHeaders("*")
                    .allowCredentials(true)
                    .maxAge(3600);
        }

        @Override
        public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
            configurer.addCustomResolver(new ReactivePageableHandlerMethodArgumentResolver());
            configurer.addCustomResolver(new ReactiveSortHandlerMethodArgumentResolver());
        }

        @Override
        public void addFormatters(FormatterRegistry registry) {
            registry.addFormatter(new AutomaticDateFormatter());
            registry.addFormatter(new AutomaticTimeFormatter());
            registry.addFormatter(new AutomaticLocalDateFormatter());
            registry.addFormatter(new AutomaticLocalDateTimeFormatter());
        }
    }

    @EnableWebFluxSecurity
    public static class SecurityConfig {
        @Bean
        public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
            http
                    .authorizeExchange(exchanges ->
                            exchanges
                                    .pathMatchers("/attachments/**").hasAuthority("SCOPE_attachment")
                                    .anyExchange().authenticated()
                    )
                    .oauth2ResourceServer(oauth2ResourceServer ->
                            oauth2ResourceServer.jwt(withDefaults())
                    );
            return http.build();
        }
    }
}
