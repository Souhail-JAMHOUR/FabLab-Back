package ma.odc.fablabback.mappers;

import ma.odc.fablabback.dto.usersdto.AdminDTO;
import ma.odc.fablabback.dto.usersdto.AppUserDTO;
import ma.odc.fablabback.dto.usersdto.MemberDTO;
import ma.odc.fablabback.entities.Users.Admin;
import ma.odc.fablabback.entities.Users.AppUser;
import ma.odc.fablabback.entities.Users.Member;

public interface UserMapper {

    AppUserDTO appUserToDTO(AppUser appUser);
    AppUser dtoToAppUser(AppUserDTO appUserDTO);
    AdminDTO adminToDTO (Admin admin);
    Admin dtoToAdmin(AdminDTO adminDTO);
    MemberDTO membreToDTO(Member member);
    Member dtoToMembre(MemberDTO memberDTO);
    
}