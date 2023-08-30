package ma.odc.fablabback.services;

import java.util.List;
import ma.odc.fablabback.dto.usersdto.AppUserDTO;
import ma.odc.fablabback.entities.Users.AppUser;
import ma.odc.fablabback.exceptions.AppUserExistsException;
import ma.odc.fablabback.requests.AuthenticationRequest;
import ma.odc.fablabback.requests.AuthenticationResponse;
import ma.odc.fablabback.requests.UserRegisterRequest;

public interface UserService {
  AppUserDTO addNewMembre(UserRegisterRequest request) throws AppUserExistsException;

  AppUser loadUserbyUsername(String username);

  AuthenticationResponse authenticate(AuthenticationRequest request);

  List<AppUserDTO> getAllUsers();
}
