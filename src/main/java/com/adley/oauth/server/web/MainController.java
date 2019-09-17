package com.adley.oauth.server.web;

@Controller
public class MainController {
    @Resource(name="tokenStore")
    private TokenStore tokenStore;

    @RequestMapping("/")
    public String root() {return "redirect:/index";}

    @RequestMapping("/index")
    public String index() {return "index";}

    @RequestMapping("/user/index")
    public String userIndex() {return "user/index";}

    @RequestMapping("/toauth/clients")
    public ModelAndView clients(ModelAndView modelAndView) {return "oauth/authpwd";}

    @RequestMapping("/toauth/codeauth")
    public String codeToken() {return "oauth/codeauth";}

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
