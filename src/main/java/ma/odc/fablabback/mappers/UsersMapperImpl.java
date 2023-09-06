package ma.odc.fablabback.mappers;

import ma.odc.fablabback.dto.usersdto.AdminDTO;
import ma.odc.fablabback.dto.usersdto.AppUserDTO;
import ma.odc.fablabback.dto.usersdto.MemberDTO;
import ma.odc.fablabback.entities.Users.Admin;
import ma.odc.fablabback.entities.Users.AppUser;
import ma.odc.fablabback.entities.Users.Member;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UsersMapperImpl implements UserMapper {
  @Override
  public AppUserDTO appUserToDTO(AppUser appUser) {
    AppUserDTO newUserDto = new AppUserDTO();
    BeanUtils.copyProperties(appUser, newUserDto);
    return newUserDto;
  }

  @Override
  public AppUser dtoToAppUser(AppUserDTO appUserDTO) {
    AppUser newUser = new AppUser();
    BeanUtils.copyProperties(appUserDTO, newUser);
    return newUser;
  }

  @Override
  public AdminDTO adminToDTO(Admin admin) {
    AdminDTO adminDTO = new AdminDTO();
    BeanUtils.copyProperties(admin, adminDTO);
    return adminDTO;
  }

  @Override
  public Admin dtoToAdmin(AdminDTO adminDTO) {
    Admin admin = new Admin();
    BeanUtils.copyProperties(adminDTO, admin);
    return admin;
  }

  @Override
  public MemberDTO membreToDTO(Member member) {
    MemberDTO memberDTO = new MemberDTO();
    BeanUtils.copyProperties(member, memberDTO);
    return memberDTO;
  }

  @Override
  public Member dtoToMembre(MemberDTO memberDTO) {
    Member member = new Member();
    BeanUtils.copyProperties(memberDTO, member);
    return member;
  }
}
