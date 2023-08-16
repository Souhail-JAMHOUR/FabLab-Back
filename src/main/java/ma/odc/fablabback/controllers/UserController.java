package ma.odc.fablabback.controllers;

import ma.odc.fablabback.entities.Users.Admin;
import ma.odc.fablabback.entities.Users.Member;
import ma.odc.fablabback.entities.Users.User;
import ma.odc.fablabback.repositories.Users.AdminRepository;
import ma.odc.fablabback.repositories.Users.MemberRepository;
import ma.odc.fablabback.repositories.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;
    @GetMapping("/users")
    public List<User> display(){
        return userRepository.findAll();
    }
    @GetMapping("/members")
    public List<Member> getMembers(){
        return memberRepository.findAll();
    }
    @GetMapping("/admins")
    public List<Admin> getAdmins(){
        return adminRepository.findAll();
    }
}
