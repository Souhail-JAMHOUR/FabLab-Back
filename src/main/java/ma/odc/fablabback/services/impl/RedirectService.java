package ma.odc.fablabback.services.impl;

import lombok.RequiredArgsConstructor;
import ma.odc.fablabback.entities.Users.Admin;
import ma.odc.fablabback.entities.Users.AppUser;
import ma.odc.fablabback.repositories.Users.AdminRepository;
import ma.odc.fablabback.repositories.Users.AppUsersRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedirectService {
  private final AdminRepository adminRepository;
  private final AppUsersRepository usersRepository;

  public boolean isAdmin(String username) {
    AppUser user = usersRepository.findByAppUsersname(username).orElse(null);
    if (user != null) {
      Admin admin = adminRepository.findById(user.getAppUsersId()).orElse(null);
      if (admin != null) {
        return true;
      }
    }
    return false;
  }
}
