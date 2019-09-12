package com.adley.oauth.client.config;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigure {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**","/css/**")
            .addResourcesLocations("/webjars/","classpath:/static/css/");
    }
}
