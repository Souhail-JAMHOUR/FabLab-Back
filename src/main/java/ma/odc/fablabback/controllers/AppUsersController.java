package ma.odc.fablabback.controllers;

import java.util.List;
import lombok.AllArgsConstructor;
import ma.odc.fablabback.entities.Users.Admin;
import ma.odc.fablabback.entities.Users.AppUser;
import ma.odc.fablabback.entities.Users.Member;
import ma.odc.fablabback.repositories.Users.AdminRepository;
import ma.odc.fablabback.repositories.Users.AppUsersRepository;
import ma.odc.fablabback.repositories.Users.MemberRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class AppUsersController {

  private MemberRepository memberRepository;

  private AppUsersRepository AppUsersRepository;

  private AdminRepository adminRepository;

  @GetMapping("/users")
  public List<AppUser> display() {
    return AppUsersRepository.findAll();
  }

  @GetMapping("/members")
  public List<Member> getMembers() {
    return memberRepository.findAll();
  }

  @GetMapping("/admins")
  public List<Admin> getAdmins() {
    return adminRepository.findAll();
  }
}
