package com.adley.oauth.client;

@SpringBootApplication
@Slf4j
public class Oauth2ClientApp {
    public static void main(String[] args) {StringApplication.run(Oauth2ClientApp.class, args);}

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {return builder.build();}
}
