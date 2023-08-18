package ma.odc.fablabback.services;

import ma.odc.fablabback.repositories.Users.AppUsersRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
  private PasswordEncoder
      passwordEncoder; // ! not to inject this as a dependency causing cycle  todo find a way by
                       // lazy inject
  private AppUsersRepository appUsersRepository;
  public UserServiceImpl(
      AppUsersRepository appUsersRepository,
      @Lazy PasswordEncoder passwordEncoder) {
    this.appUsersRepository = appUsersRepository;
    this.passwordEncoder = passwordEncoder;
  }

//  @Override
//  public AuthenticationResponse addNewUser(RegisterRequest request) throws AppUserExistsException {
//    AppUser appUser = appUsersRepository.findByAppUsersname(request.getUsername()).orElse(null);
//    if (appUser != null) {
//      throw new AppUserExistsException();
//    }
//    if (!request.getPassword().equals(request.getConfirmedPassword())) {
//      throw new RuntimeException("Passwords does not match");
//    }
//    appUser =
//        AppUser.builder()
//            .appUsersname(request.getUsername())
//            .cin("F642077")
//            .name(request.getName())
//            .email(request.getEmail())
//            .password(passwordEncoder.encode(request.getPassword()))
//            .birthDate(request.getBirthDate())
//            .sex(request.getSex())
//            .build();
//    UserDetails userDetails =
//        User.withUsername(request.getUsername())
//            .password(passwordEncoder.encode(request.getPassword()))
//            .build();
//    appUsersRepository.save(appUser);
//    return AuthenticationResponse.builder().accessToken(jwtToken).build();
//  }
//
//  @Override
//  public AppUser loadUserbyUsername(String username) {
//    return appUsersRepository.findByAppUsersname(username).get();
//  }
}
