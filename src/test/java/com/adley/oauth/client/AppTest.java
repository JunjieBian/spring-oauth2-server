package com.adley.oauth.client;

@Slf4j
public class AppTest {
    @Test
    public void test() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
        log.info("{}",bCryptPasswordEncoder.encode("secret"));
    }
}
