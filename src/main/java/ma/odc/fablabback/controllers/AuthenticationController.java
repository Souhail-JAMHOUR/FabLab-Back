package ma.odc.fablabback.controllers;

import lombok.RequiredArgsConstructor;
import ma.odc.fablabback.Exceptions.AppUserExistsException;
import ma.odc.fablabback.security.models.AuthenticationResponse;
import ma.odc.fablabback.security.models.RegisterRequest;
import ma.odc.fablabback.services.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthenticationController {
  private final UserServiceImpl userService;

  @GetMapping("/hello")
  public String hello() {
    return "HELLO SOUHAIL";
  }

  @PostMapping("/signup")
  public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request)
      throws AppUserExistsException {
    return ResponseEntity.ok(userService.addNewUser(request));
  }


  
}
