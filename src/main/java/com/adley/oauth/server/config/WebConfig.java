package com.adley.oauth.server.config;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigure {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**","/css/**")
            .addResourcesLocations("/webjars/","classpath:/static/css/");
    }
}
