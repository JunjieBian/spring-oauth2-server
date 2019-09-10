package com.adley.oauth.client.config;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigureerAdapter {
    @Bean
    public UserDetailsService userDetailsService() {return new AuthService();}

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {return new BCryptPassowordEncoder();}

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequrests()
                .requestMatchers(PathRequest.toStaticResources().adCommonLocations()).permitAll()
                .antMatchers("/h2-console/**","/index","/login","/actuator/**","/static/**","/","/granttoken").permitAll()
                .antMatchers("/user/**","/weibo/**").hasRole("USER")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").and().headers().frameOptions().sameOrigin();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
}
