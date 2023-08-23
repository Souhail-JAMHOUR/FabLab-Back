package ma.odc.fablabback.controllers;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import ma.odc.fablabback.dto.usersdto.AdminDTO;
import ma.odc.fablabback.dto.usersdto.AppUserDTO;
import ma.odc.fablabback.entities.Users.Admin;
import ma.odc.fablabback.entities.Users.AppUser;
import ma.odc.fablabback.entities.Users.Member;
import ma.odc.fablabback.mappers.UsersMapperImpl;
import ma.odc.fablabback.repositories.Users.AdminRepository;
import ma.odc.fablabback.repositories.Users.AppUsersRepository;
import ma.odc.fablabback.repositories.Users.MemberRepository;
import ma.odc.fablabback.security.models.AdminRegisterRequest;
import ma.odc.fablabback.services.UserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class AppUsersController {

  private MemberRepository memberRepository;
  private UsersMapperImpl usersMapper;
  private UserServiceImpl userService;

  private AppUsersRepository AppUsersRepository;

  private AdminRepository adminRepository;

  @GetMapping("/all")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public List<AppUserDTO> display() {
    List<AppUser> all = AppUsersRepository.findAll();
    List<AppUserDTO> dtos = new ArrayList<>();

    for (AppUser user : all) {
      AppUserDTO appUserDTO = usersMapper.appUserToDTO(user);
      dtos.add(appUserDTO);
    }
    return dtos;
  }

  @GetMapping("/members")
  public List<Member> getMembers() {
    return memberRepository.findAll();
  }

  @GetMapping("/admins")
  public List<Admin> getAdmins() {
    return adminRepository.findAll();
  }


  @PostMapping("/addAdmin")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN')")
  public AdminDTO addAdmin(@RequestBody AdminRegisterRequest adminRegisterRequest) {
    return userService.addNewAdmin(adminRegisterRequest);
  }


}
