package ma.odc.fablabback.services;

import ma.odc.fablabback.entities.Users.Admin;
import ma.odc.fablabback.entities.Users.AppUser;
import ma.odc.fablabback.repositories.Users.AdminRepository;
import ma.odc.fablabback.repositories.Users.AppUsersRepository;
import ma.odc.fablabback.security.models.AuthenticationRequest;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements IAdminService {
  private AppUsersRepository appUsersRepository;
  private AdminRepository adminRepository;

  @Override
  public boolean checkAdmin(AuthenticationRequest authenticationRequest) {
    AppUser user = appUsersRepository.findByAppUsersname(authenticationRequest.getUsername()).orElse(null);
    Admin admin = adminRepository.findById(user.getAppUsersId()).orElse(null);
    if(admin != null){
      return true;
    }
    return false;
  }
}
