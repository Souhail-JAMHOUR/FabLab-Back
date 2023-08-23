package ma.odc.fablabback.controllers;

import lombok.RequiredArgsConstructor;
import ma.odc.fablabback.services.RedirectService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("redirect")
@RequiredArgsConstructor
public class RedirectController {
  private final JwtDecoder jwtDecoder;
  private final RedirectService redirectService;

  @GetMapping("")
  public ResponseEntity<String> redirectRequest(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
      String token = jwt.substring(7);
      String subject = jwtDecoder.decode(token).getSubject();
      boolean isAdmin = redirectService.isAdmin(subject);
      if (isAdmin){
          return ResponseEntity.ok("redirect:/users/all");
      }
      return ResponseEntity.ok("redirect:/");
  }
}
