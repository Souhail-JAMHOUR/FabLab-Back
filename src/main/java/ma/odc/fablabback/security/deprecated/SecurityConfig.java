// package ma.odc.fablabback.security;
//
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Lazy;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.ProviderManager;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
//
//// @Configuration
//// @EnableWebSecurity(debug = true)
//// @EnableMethodSecurity
// public class SecurityConfig {
//
//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http.csrf(csrf -> csrf.disable());
//    http.oauth2ResourceServer(oa-> oa.jwt(Customizer.withDefaults()));
////    http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//    http.authorizeHttpRequests(
//        (authorize) ->
//            authorize
////                .requestMatchers(antMatcher("/v1/auth/**"))
////                .permitAll()
//                .anyRequest()
//                .permitAll());
//
//    return http.build();
//  }
//
////  @Bean
//  public PasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }
//
////  @Bean
//  public AuthenticationManager authenticationManager(@Lazy UserDetailsService userDetailsService)
// {
//    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//    authProvider.setPasswordEncoder(passwordEncoder());
//    authProvider.setUserDetailsService(userDetailsService);
//    return new ProviderManager(authProvider);
//  }
// }
