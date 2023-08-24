package ma.odc.fablabback.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import ma.odc.fablabback.Exceptions.AppUserExistsException;
import ma.odc.fablabback.entities.Users.AppUser;
import ma.odc.fablabback.repositories.Users.AppUsersRepository;
import ma.odc.fablabback.security.JwtService;
import ma.odc.fablabback.security.models.AuthenticationResponse;
import ma.odc.fablabback.security.models.RegisterRequest;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
  private PasswordEncoder passwordEncoder; // ! LAZY INJECT
  private AppUsersRepository appUsersRepository;
  private JwtService jwtService;
  private JwtEncoder jwtEncoder;
  private AuthenticationManager authenticationManager;

  public UserServiceImpl(
          AppUsersRepository appUsersRepository,
          @Lazy PasswordEncoder passwordEncoder,
          JwtService jwtService,
          JwtEncoder jwtEncoder, AuthenticationManager authenticationManager) {
    this.appUsersRepository = appUsersRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.jwtEncoder = jwtEncoder;
    this.authenticationManager = authenticationManager;
  }

  @Override
  public AuthenticationResponse addNewUser(RegisterRequest request) throws AppUserExistsException {
    AppUser appUser = appUsersRepository.findByAppUsersname(request.getUsername()).orElse(null);
    if (appUser != null) {
      throw new AppUserExistsException();
    }
    if (!request.getPassword().equals(request.getConfirmedPassword())) {
      throw new RuntimeException("Passwords does not match");
    }
    appUser =
        AppUser.builder()
            .appUsersname(request.getUsername())
            .cin("F642077")
            .name(request.getName())
            .email(request.getEmail())
            .birthDate(request.getBirthDate())
            .password(passwordEncoder.encode(request.getPassword()))
            .ESex(request.getESex())
            .build();
    UserDetails userDetails =
        User.withUsername(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .build();
    appUsersRepository.save(appUser);
    String jwtToken = jwtService.generateToken(userDetails);
    return AuthenticationResponse.builder().accessToken(jwtToken).build();
  }

  @Override
  public AppUser loadUserbyUsername(String username) {
    return appUsersRepository.findByAppUsersname(username).orElse(null);
  }

  @Override
  public AuthenticationResponse authenticte(RegisterRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

    Instant instant = Instant.now();
    JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
            .issuedAt(instant)
            .expiresAt(instant.plus(10, ChronoUnit.MINUTES))
            .subject(request.getUsername())
            .build();
    JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
            JwsHeader.with(MacAlgorithm.HS256).build(),
            jwtClaimsSet
    );
    String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
    return AuthenticationResponse.builder().accessToken(jwt).build();
  }
}
