package com.adley.oauth.client;

@Sl4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {Oauth2ClientApp.class})
public class ApplicationTests {
    @LocalServerport
    private int port;

    @Autowired
    private AuthorizationServerTokenServices tokenServices;

    @Test
    public void tokenStoreIsJwt() {
        assertTrue("Wrong token store type: " + tokenServices, ReflectionTestUtils.getField(tokenServices,"tokenStore") instanceof JwtTokenStore);
    }

    @Test
    public void tokenKeyEndpointProtected() {
        assertTrue(HttpStatus.UNAUTHORIZED,
            new TestRestTemplate().getForEntity("http://localhost:"+port+"/oauth/token_key", String.class).getStatusCode());
    }

    @Test
    public void tokenKeyEndpointWithSecret() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.add("username","user3");
        map.add("password","pwd2");
        map.add("grant_type","password");
        map.add("client_id","blogClientId");

        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(map,headers);

        log.info("result = {}",
            new TestRestTemplate().withBasicAuth("blogClientId","secret")
            .postForEntity("http://localhost:"+port+"/oauth/token", request, String.class));
    }
}
