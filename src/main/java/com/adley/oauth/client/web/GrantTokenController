package com.adley.oauth.client.web;

@Slf4j
@Controller
public class GrantTokenController {
    @Autowired
    private Environment env;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    private static final String STATEKEYPREFIX = "oauth2:client:state:";

    @RequestMapping(method = RequestMethod.GET, value = "/weibo/auth")
    @ResponseBody
    public String getAuthUrl() {
        Map<String,String> urlParam = new HashMap<>();
        urlParam.put("client_id",env.getProperty("auth.client.id"));
        urlParam.put("response_type","code");
        urlParam.put("scope","read");
        urlParam.put("redirect_url",env.getProperty("auth.redirect.url"));
        urlParam.put("state",Base64.encodeBase64String(UUID.randomUUID().toString().getBytes()));
        redisTemplate.opsForValue().set(STATEKEYPREFIX+urlParam.get("state"),urlParam.get("state"),10,TimeUnit.MINUTES);
        return env.getProperty("auth.server.url")+"/oauth/authoriza?"+urlParam.entrySet().stream().map(Objects::toString).collect(Collectors.joining("&"));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/granttoken")
    @ResponseBody
    public String getAccessToken(String code, String state,String error,HttpServletRequest httpServletRequest,HttpServletResponse response) {
        assert state.equalsIgnoreCase(redisTemplate.opsForValue().get(STATEKEYPREFIX+state).toString());
        redisTemplate.delete(STATEKEYPREFIX+state);
        if (StringUtils.isNoneBlank(error) {
            return "error";
        }
        MultiValueMap<String,String> urlParam = new LinkedMultiValueMap<>();
        urlParam.add("client_id",env.getProperty("auth.client.id"));
        urlParam.add("client_secret",env.getProperty("auth.client.secret"));
        urlParam.put("grant_type","authorization_code");
        urlParam.put("code",code);
        urlParam.put("redirect_url",env.getProperty("auth.redirect.url"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization","Basic "+Base64.encodeBase64String((env.getProperty("auth.client.id")+":"+env.getProperty("auth.client.secret")).getBytes()));

        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(urlParam,headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(env.getProperty("auth.server.url")+"/oauth/token",request,String.class,urlParam);
        if (null != httpServletRequest.getCookies()) {
            Optional<Cookie> opt = Arrays.stream(httpServletRequest.getCookies()).filter(c->c.getName().equalsIgnoreCase("authToken")).findFirst();
            if (opt.isPresent()) {
                opt.get().setMaxAge(0);
                response.addCookie(opt.get());
            }
        }
        Cookie cookie = new Cookie("authToken",JSONObject.parseObject(responseEntity.getBody()).toString("access_token"));
        cookie.setDomain(httpServletRequest.getServerName());
        cookie.setPath("/");
        cookie.setMax(5*60);
        response.addCookie(cookie);
        return "redirect:/blogs/list";
    }
}
