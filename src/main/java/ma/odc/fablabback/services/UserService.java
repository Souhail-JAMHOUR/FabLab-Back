package ma.odc.fablabback.services;

import java.util.List;
import ma.odc.fablabback.dto.usersdto.AppUserDTO;
import ma.odc.fablabback.entities.Users.AppUser;
import ma.odc.fablabback.exceptions.AppUserExistsException;
import ma.odc.fablabback.requests.AuthenticationRequest;
import ma.odc.fablabback.requests.AuthenticationResponse;
import ma.odc.fablabback.requests.UserRegisterRequest;
import org.springframework.data.domain.Page;

public interface UserService {
  AppUserDTO addNewMembre(UserRegisterRequest request) throws AppUserExistsException;

  AppUser loadUserbyUsername(String username);

  AuthenticationResponse authenticate(AuthenticationRequest request);

  Page<AppUserDTO> getAllUsers(int page, int size);

  //  List<AppUserDTO> searchUser(String keyword);

  Page<AppUserDTO> searchUser(String keyword, int page, int size);
}
