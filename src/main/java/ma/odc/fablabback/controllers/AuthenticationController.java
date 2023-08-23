package ma.odc.fablabback.controllers;

import lombok.RequiredArgsConstructor;
import ma.odc.fablabback.Exceptions.AppUserExistsException;
import ma.odc.fablabback.dto.usersdto.AppUserDTO;
import ma.odc.fablabback.security.models.AuthenticationRequest;
import ma.odc.fablabback.security.models.AuthenticationResponse;
import ma.odc.fablabback.security.models.UserRegisterRequest;
import ma.odc.fablabback.services.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthenticationController {
  private final UserServiceImpl userServiceImpl;
//  private final AdminServiceImpl adminService;

  @PostMapping("/signup")
  public ResponseEntity<AppUserDTO> register(@RequestBody UserRegisterRequest request)
      throws AppUserExistsException {
    return ResponseEntity.ok(userServiceImpl.addNewMembre(request));
  }

  @PostMapping("/signin")
  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(userServiceImpl.authenticte(request));
  }


  
}
