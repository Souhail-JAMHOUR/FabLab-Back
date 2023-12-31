package ma.odc.fablabback.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import ma.odc.fablabback.dto.usersdto.AdminDTO;
import ma.odc.fablabback.entities.Users.Admin;
import ma.odc.fablabback.entities.Users.AppUser;
import ma.odc.fablabback.exceptions.AppUsersNotFoundException;
import ma.odc.fablabback.mappers.UsersMapperImpl;
import ma.odc.fablabback.repositories.Users.AdminRepository;
import ma.odc.fablabback.repositories.Users.AppUsersRepository;
import ma.odc.fablabback.requests.AuthenticationRequest;
import ma.odc.fablabback.services.IAdminService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements IAdminService {
  private AppUsersRepository appUsersRepository;
  private UsersMapperImpl usersMapper;
  private AdminRepository adminRepository;

  @Override
  public boolean checkAdmin(AuthenticationRequest authenticationRequest) {
    AppUser user =
        appUsersRepository.findByAppUsersname(authenticationRequest.getUsername()).orElse(null);
    Admin admin = adminRepository.findById(user.getAppUsersId()).orElse(null);
    if (admin != null) {
      return true;
    }
    return false;
  }

  @Override
  public List<AdminDTO> getAllAdmins() {
    List<Admin> all = adminRepository.findAll();
    List<AdminDTO> adminDTOS = new ArrayList<>();
    for (Admin a : all) {
      adminDTOS.add(usersMapper.adminToDTO(a));
    }
    return adminDTOS;
  }

  public AdminDTO getAdminByName(String username) throws AppUsersNotFoundException {
    Admin admin =
        adminRepository
            .findByAppUsersname(username)
            .orElseThrow(() -> new AppUsersNotFoundException("No Admin with this username"));
    AdminDTO adminDTO = usersMapper.adminToDTO(admin);
    return adminDTO;
  }

  public List<AdminDTO> searchAdmin(String keyword) {
    List<AdminDTO> collected =
        adminRepository.searchAdmins(keyword).stream()
            .map(usersMapper::adminToDTO)
            .collect(Collectors.toList());
    return collected;
  }
}
