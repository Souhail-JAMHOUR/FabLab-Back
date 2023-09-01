package ma.odc.fablabback.controllers;

import java.util.List;
import lombok.AllArgsConstructor;
import ma.odc.fablabback.dto.usersdto.AdminDTO;
import ma.odc.fablabback.dto.usersdto.AppUserDTO;
import ma.odc.fablabback.dto.usersdto.MemberDTO;
import ma.odc.fablabback.exceptions.AppUsersNotFoundException;
import ma.odc.fablabback.mappers.UsersMapperImpl;
import ma.odc.fablabback.repositories.Users.AppUsersRepository;
import ma.odc.fablabback.requests.AdminRegisterRequest;
import ma.odc.fablabback.services.impl.AdminServiceImpl;
import ma.odc.fablabback.services.impl.MemberService;
import ma.odc.fablabback.services.impl.SuperAdminService;
import ma.odc.fablabback.services.impl.UserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/users")
@AllArgsConstructor
public class AppUsersController {
  private SuperAdminService superAdminService;
  private MemberService memberService;
  private AdminServiceImpl adminService;
  private UserServiceImpl userService;


  private UsersMapperImpl usersMapper;

  private AppUsersRepository AppUsersRepository;

  @GetMapping("/all")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public List<AppUserDTO> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/members")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public List<MemberDTO> getMembers() {
    return memberService.getAllMembers();
  }

  @GetMapping("/admins")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public List<AdminDTO> getAdmins() {
    return adminService.getAllAdmins();
  }


  @PostMapping("/addAdmin")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN')")
  public AdminDTO addAdmin(@RequestBody AdminRegisterRequest adminRegisterRequest) {
    return superAdminService.addNewAdmin(adminRegisterRequest);
  }

  @DeleteMapping("admin/{id}")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN')")
  public void deleteAdmin(@PathVariable long id){
    try {
      superAdminService.deleteAdmin(id);
    } catch (AppUsersNotFoundException e) {
      throw new RuntimeException(e);
    }
  }


}
