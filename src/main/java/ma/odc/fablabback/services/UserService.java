package ma.odc.fablabback.services;

import ma.odc.fablabback.Exceptions.AppUserExistsException;
import ma.odc.fablabback.entities.Users.AppUser;
import ma.odc.fablabback.security.models.AuthenticationResponse;
import ma.odc.fablabback.security.models.RegisterRequest;

public interface UserService {
  AuthenticationResponse addNewUser(RegisterRequest request) throws AppUserExistsException;

  AppUser loadUserbyUsername(String username);

  AuthenticationResponse authenticte(RegisterRequest request);
}
