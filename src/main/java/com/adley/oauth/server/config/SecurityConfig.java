package com.adley.oauth.server.config;

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
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequrests()
                .requestMatchers(PathRequest.toStaticResources().adCommonLocations()).permitAll()
                .antMatchers("/h2-console/**","/index","/login","/actuator/**","/static/**","/").permitAll()
                .antMatchers("/user/**").hasRole("USER","ADMIN")
                .antMatchers("/toauth/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").and().headers().frameOptions().sameOrigin().and().csrf().disabled();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
