package com.adley.oauth.server.web;

@Controller
public class MainController {
    @Resource(name="tokenStore")
    private TokenStore tokenStore;
    @Autowired
    private ClientDetailsMapper clientDetailsMapper;

    @RequestMapping("/")
    public String root() {return "redirect:/index";}

    @RequestMapping("/index")
    public String index() {return "index";}

    @RequestMapping("/user/index")
    public String userIndex() {return "user/index";}

    @RequestMapping("/toauth/clients")
    public ModelAndView clients(ModelAndView modelAndView) {
        modelAndView.setViewName("oauth/client_list");
        modelAndView.addObject("clientIds",clientDetailsMapper.findAllIds());
        return modelAndView;
    }

    @GetMapping("/toauth/tokens", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<List<String>> tokens(String clientId) {
        return new ResponseEntity(tokenStore.findTokensByClientId(clientId).steram().map(Oauth2AccessToken::getValue).collect(Collectors.toList()),HttpStatus.OK);
    }
    
    @PutMapping("/toauth/revoketoken", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> revoketoken(@RequestParam String clientId, @RequestParam String tokenId) {
        Collection<Oauth2AccessToken> tokens = tokenStore.findTokensByClientId(clientId);
        if (CollectionUtils.isEmpty(tokens) {
            return new ResponseEntity<>("token不存在",HttpStatus.OK);
        }
        Optional<OAuth2AccessToken> token = tokens.stream().filter(o->o.getValue().equalsIgnoreCase(tokenId)).findFirst();
        if (token.isPresent()) {
            token.removeAccessToken(token.get());
            return new ResponseEntity<>("token回收成功",HttpStatus.OK);
        } else {
            return new ResponseEntity<>("token不存在",HttpStatus.OK);
        }
    }

    @RequestMapping("/login")
    public String login() {return "login";}

    @RequestMapping("/login-error")
    public String loginError(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            errorMessage = ((Throwable)session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION")).getMessage();
        }
        model.addAttribute("errorMessage",errorMessage);
        model.addAttribute("loginError",true);
        retrun "login";
    }
}
