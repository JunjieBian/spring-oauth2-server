package com.adley.oauth.client.service;

@Slf4j
public class AuthService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
      try {
          Map<String,SpsUserDetails> spsUserDetailsHashMap = new HashMap();
          JSONOblect.parseArray(String.join("",Files.readAllLines(ResourceUtils.getFile("classpath:auth.json").toPath())),SpsUserDetails.class).forEach(s->{
                  spsUserDetailsHashMap.put(s.getUsername(),s);
          });
          if (null == spsUserDetailsHashMap.get(userName)) {
              return new SpsUserDetails();
          } else {
              return spsUserDetailsHashMap.get(userName);
          }
      } catch (IOException e) {
          log.error("ERROR",e);
          return new SpsUserDetails();
      }
    }
}
