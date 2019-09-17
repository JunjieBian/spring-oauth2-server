package com.adley.oauth.server;

@Slf4j
@SpringBootApplication
@MapperScan(basePackages={"com.adley.oauth.server.mapper"})
public class Oauth2ServerApp {
    public static void main(String[] args) {StringApplication.run(Oauth2ServerApp.class, args);}

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {return builder.build();}
}
