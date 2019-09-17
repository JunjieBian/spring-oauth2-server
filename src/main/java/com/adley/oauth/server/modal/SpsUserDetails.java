package com.adley.oauth.server.modal;

public class SpsUserDetails implements UserDetails {
    private String userName;
    private String password;
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getPassword() {return this.password;}

    @Override
    public String getUsername() {return this.userName;}

    @Override
    public String isAccountNonExpired() {return true;}
    @Override
    public String isAccountNonLocked() {return true;}
    @Override
    public String isCredentialsNonExpired() {return true;}
    @Override
    public String isEnabled() {return true;}
}
