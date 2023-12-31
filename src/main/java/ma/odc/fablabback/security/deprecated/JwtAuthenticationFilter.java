//package ma.odc.fablabback.security;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.web.filter.OncePerRequestFilter;
//
////@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//  private JwtService jwtService;
//  private UserDetailServiceImpl userDetailService;
//
//  public JwtAuthenticationFilter(JwtService jwtService, UserDetailServiceImpl userDetailService) {
//    this.jwtService = jwtService;
//    this.userDetailService = userDetailService;
//  }
//
//  @Override
//  protected void doFilterInternal(
//      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//      throws ServletException, IOException {
//    final String authHeader = request.getHeader("Authorization");
//    final String token;
//    final String username;
//    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//      filterChain.doFilter(request, response);
//      return;
//    }
//    token = authHeader.substring(7);
//    username = jwtService.extractUsername(token);                                 //! extract the username
//    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//      UserDetails userDetails = userDetailService.loadUserByUsername(username);
//      if (jwtService.isTokenValid(token, userDetails)) {
//
//        UsernamePasswordAuthenticationToken authenticationToken =
//            new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword());
//
//        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//      }
//      filterChain.doFilter(request, response);
//    }
//  }
//}
