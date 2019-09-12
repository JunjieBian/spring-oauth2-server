package con.adley.oauth.client.web;

@Slf4j
@Controller
@RequestMapping("/blogs")
public class BlogsController {
    @Autowired
    private Environment env;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(method = RequestMethod.GET, value="/list")
    public ModelAndView blogList(@CookieValue(name="authToken") String authToken, ModelAndView modelAndView, HttpServletResponse response) {
        try {
            assert StringUtils.hasText(authToken);
            response.addCookie(new Cookie("authToken",authToken));
            modelAndView.setViewName("blogs/blogs");
            modelAndView.addObject("blogList", JSONObject.parseArray(getResource("/blogs/blog/list",authToken),JSONObject.class));
        } catch(Exception e) {
            log.error("请求异常",e);
            modelAndView.setViewName("/api_error");
        }
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value="/content", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String readBlogs(String blogId, @CookieValue(name="authToken") String authToken) {
        try {
            return getResource("/blogs/blog/"+blogId,authToken);
        } catch(Exception e) {
            log.error("请求异常",e);
            return "";
        }
    }

    private String getResource(String url, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        header.add("Authorization", "Bearer "+authToken);
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(null,headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(env.getProperty("auth.resource.server.url")+url, request, String.class);
        return responseEntity.getBody();
    }
}
