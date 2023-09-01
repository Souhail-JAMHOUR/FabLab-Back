package ma.odc.fablabback.services.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import ma.odc.fablabback.dto.usersdto.AppUserDTO;
import ma.odc.fablabback.entities.Users.AppUser;
import ma.odc.fablabback.entities.Users.Member;
import ma.odc.fablabback.enums.Role;
import ma.odc.fablabback.exceptions.AppUserExistsException;
import ma.odc.fablabback.mappers.UserMapper;
import ma.odc.fablabback.mappers.UsersMapperImpl;
import ma.odc.fablabback.repositories.Users.AppUsersRepository;
import ma.odc.fablabback.repositories.Users.MemberRepository;
import ma.odc.fablabback.requests.AuthenticationRequest;
import ma.odc.fablabback.requests.AuthenticationResponse;
import ma.odc.fablabback.requests.UserRegisterRequest;
import ma.odc.fablabback.services.UserService;
import org.springframework.beans.factory.annotation.Value;
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
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserMapper userMapper;
  private final AppUsersRepository appUsersRepository;
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtEncoder jwtEncoder;
  private final AuthenticationManager authenticationManager;
  private final UsersMapperImpl usersMapper;

  @Value("${spring.application.security.jwt.expiration}")
  private long expiration;

  @Override
  public AppUserDTO addNewMembre(UserRegisterRequest request) throws AppUserExistsException {
    AppUser appUser = appUsersRepository.findByAppUsersname(request.getUsername()).orElse(null);
    if (appUser != null) {
      throw new AppUserExistsException("User does not exists");
    }
    if (!request.getPassword().equals(request.getConfirmedPassword())) {
      throw new RuntimeException("Passwords does not match");
    }

    Member newMembre =
        Member.builder()
            .birthDate(request.getBirthDate())
            .name(request.getName())
            .cin(request.getCin())
            .sex(request.getSex())
            .role(Role.MEMBER)
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .appUsersname(request.getUsername())
            .status(request.getStatus())
            .etablissment(request.getEtablissment())
            .build();
    Member savedUser = memberRepository.save(newMembre);
    return userMapper.appUserToDTO(savedUser);
  }

  @Override
  public AppUser loadUserbyUsername(String username) {
    return appUsersRepository.findByAppUsersname(username).orElse(null);
  }

  @Override
  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

    //    ! System.out.println("****************AUTH**************************");

    String scopes =
        authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));

    Instant instant = Instant.now();

    JwtClaimsSet jwtClaimsSet =
        JwtClaimsSet.builder()
            .issuer("FabLab")
            .issuedAt(instant)
            .expiresAt(instant.plus(expiration, ChronoUnit.MINUTES))
            .subject(request.getUsername())
            .claim("scope", scopes)
            .build();
    JwtEncoderParameters jwtEncoderParameters =
        JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).type("JWT").build(), jwtClaimsSet);
    String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
    return AuthenticationResponse.builder().accessToken(jwt).build();
  }

  @Override
  public List<AppUserDTO> getAllUsers() {
    List<AppUser> all = appUsersRepository.findAll();
    List<AppUserDTO> dtos = new ArrayList<>();

    for (AppUser user : all) {
      AppUserDTO appUserDTO = usersMapper.appUserToDTO(user);
      dtos.add(appUserDTO);
    }
    return dtos;
  }
}
