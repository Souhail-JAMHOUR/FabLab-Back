package ma.odc.fablabback.services;

import ma.odc.fablabback.Exceptions.AppUserExistsException;
import ma.odc.fablabback.dto.usersdto.AppUserDTO;
import ma.odc.fablabback.entities.Users.AppUser;
import ma.odc.fablabback.security.models.AuthenticationRequest;
import ma.odc.fablabback.security.models.AuthenticationResponse;
import ma.odc.fablabback.security.models.UserRegisterRequest;

public interface UserService {
  AppUserDTO addNewMembre(UserRegisterRequest request) throws AppUserExistsException;

  AppUser loadUserbyUsername(String username);

  AuthenticationResponse authenticte(AuthenticationRequest request);
}
