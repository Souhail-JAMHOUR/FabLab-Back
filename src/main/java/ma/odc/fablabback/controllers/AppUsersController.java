package ma.odc.fablabback.controllers;

import java.util.List;
import lombok.AllArgsConstructor;
import ma.odc.fablabback.dto.usersdto.AdminDTO;
import ma.odc.fablabback.dto.usersdto.AppUserDTO;
import ma.odc.fablabback.dto.usersdto.MemberDTO;
import ma.odc.fablabback.exceptions.AppUsersNotFoundException;
import ma.odc.fablabback.requests.AdminRegisterRequest;
import ma.odc.fablabback.services.impl.AdminServiceImpl;
import ma.odc.fablabback.services.impl.MemberService;
import ma.odc.fablabback.services.impl.SuperAdminService;
import ma.odc.fablabback.services.impl.UserServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/users")
@AllArgsConstructor
@CrossOrigin("*")
public class AppUsersController {
  private SuperAdminService superAdminService;
  private MemberService memberService;
  private AdminServiceImpl adminService;
  private UserServiceImpl userService;

  @GetMapping("/all")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public Page<AppUserDTO> getAllUsers(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "2") int size) {
    return userService.getAllUsers(page, size);
  }

  @GetMapping("/user/search")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN')")
  public Page<AppUserDTO> searchUser(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "5") int size,
      @RequestParam(name = "keyword", defaultValue = "") String keyword) {

    return userService.searchUser("%" + keyword + "%", page, size);
  }

  @GetMapping("/members/search")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN')")
  public Page<MemberDTO> searchMember(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "5") int size,
      @RequestParam(name = "keyword", defaultValue = "") String keyword) {
    return memberService.searchMember("%" + keyword + "%", page, size);
  }

  @GetMapping("/admins/search")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN')")
  public Page<AdminDTO> searchAdmin(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "5") int size,
      @RequestParam(name = "keyword", defaultValue = "") String keyword) {
    return adminService.searchAdmin("%" + keyword + "%", page, size);
  }

  @GetMapping("/members")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public Page<MemberDTO> getMembers(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "5") int size) {
    return memberService.getAllMembers(page, size);
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
  public void deleteAdmin(@PathVariable long id) throws AppUsersNotFoundException {
    superAdminService.deleteAdmin(id);
  }
}
