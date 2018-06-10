package com.iw.fsaapi.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfiguration implements WebMvcConfigurer {

    private static final String[] EXPOSED_HEADERS = {"origin","content-type","accept"};

    private static final long maxAge = 36000;

    @Override
    public void addCorsMappings(final CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedMethods("GET","OPTIONS","HEAD")
                .exposedHeaders(EXPOSED_HEADERS)
                .allowedHeaders(EXPOSED_HEADERS)
                .maxAge(maxAge)
                .allowedOrigins("*");
    }

}
