package ma.odc.fablabback.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import ma.odc.fablabback.Exceptions.AppUserExistsException;
import ma.odc.fablabback.dto.usersdto.AppUserDTO;
import ma.odc.fablabback.entities.Users.AppUser;
import ma.odc.fablabback.entities.Users.Member;
import ma.odc.fablabback.enums.Role;
import ma.odc.fablabback.mappers.UserMapper;
import ma.odc.fablabback.repositories.Users.AppUsersRepository;
import ma.odc.fablabback.repositories.Users.MemberRepository;
import ma.odc.fablabback.security.models.AuthenticationRequest;
import ma.odc.fablabback.security.models.AuthenticationResponse;
import ma.odc.fablabback.security.models.RegisterRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {
  private UserMapper userMapper;
  private AppUsersRepository appUsersRepository;
  private MemberRepository memberRepository;
  private PasswordEncoder passwordEncoder;
  private JwtEncoder jwtEncoder;
  private AuthenticationManager authenticationManager;

  @Override
  public AppUserDTO addNewMembre(RegisterRequest request) throws AppUserExistsException {
    AppUser appUser = appUsersRepository.findByAppUsersname(request.getUsername()).orElse(null);
    if (appUser != null) {
      throw new AppUserExistsException();
    }
    if (!request.getPassword().equals(request.getConfirmedPassword())) {
      throw new RuntimeException("Passwords does not match");
    }

    Member newUser =
        Member.builder()
            .birthDate(request.getBirthDate())
            .name(request.getName())
            .cin(request.getCin())
            .sex(request.getSex())
            .role(Role.MEMBER)
            .email(request.getPassword())
            .password(passwordEncoder.encode(request.getPassword()))
            .appUsersname(request.getUsername())
            .build();
    Member savedUser = memberRepository.save(newUser);
    return userMapper.appUserToDTO(savedUser);
  }

  @Override
  public AppUser loadUserbyUsername(String username) {
    return appUsersRepository.findByAppUsersname(username).orElse(null);
  }

  @Override
  public AuthenticationResponse authenticte(AuthenticationRequest request) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

    System.out.println("****************AUTH**************************");
    String scopes =
        authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));

    Instant instant = Instant.now();
    JwtClaimsSet jwtClaimsSet =
        JwtClaimsSet.builder()
            .issuedAt(instant)
            .expiresAt(instant.plus(10, ChronoUnit.MINUTES))
            .subject(request.getUsername())
            .claim("scope", scopes)
            .build();
    JwtEncoderParameters jwtEncoderParameters =
        JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), jwtClaimsSet);
    String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
    return AuthenticationResponse.builder().accessToken(jwt).build();
  }
}
