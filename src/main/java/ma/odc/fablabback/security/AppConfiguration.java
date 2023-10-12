package ma.odc.fablabback.security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity()
@EnableMethodSecurity(prePostEnabled = false)
public class AppConfiguration {
  @Value("${spring.application.security.jwt.secret-key}")
  private String SECRET_KEY;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable());
    http.oauth2ResourceServer(oa -> oa.jwt(Customizer.withDefaults()));
    http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.authorizeHttpRequests(
        (authorize) ->
            authorize
                .requestMatchers("/swagger-ui/**", "/v1/auth/**", "/v3/api-docs/**")
                .permitAll()
                .anyRequest()
                .permitAll());
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(@Lazy UserDetailsService userDetailsService) {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setPasswordEncoder(passwordEncoder());
    authProvider.setUserDetailsService(userDetailsService);
    return new ProviderManager(authProvider);
  }

  @Bean
  JwtEncoder jwtEncoder() {
    return new NimbusJwtEncoder(new ImmutableSecret<>(SECRET_KEY.getBytes()));
  }

  @Bean
  JwtDecoder jwtDecoder() {
    SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "HS256");
    return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS256).build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.addAllowedOrigin("*");
    corsConfiguration.addAllowedMethod("*");
    corsConfiguration.addAllowedHeader("*");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }
}
