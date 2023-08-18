package ma.odc.fablabback.security;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  private UserDetailServiceImpl userDetailServiceImpl;

  public SecurityConfig(UserDetailServiceImpl userDetailServiceImpl) {
    this.userDetailServiceImpl = userDetailServiceImpl;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
        (authorize) ->
            authorize
                .requestMatchers(antMatcher("/v1/auth/signup"))
                .permitAll()
                .dispatcherTypeMatchers(FORWARD, ERROR)
                .permitAll()
                .anyRequest()
                .authenticated());
    http.csrf(csrf -> csrf.disable());

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
