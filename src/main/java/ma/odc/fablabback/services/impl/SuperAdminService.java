package ma.odc.fablabback.services.impl;

import lombok.AllArgsConstructor;
import ma.odc.fablabback.dto.usersdto.AdminDTO;
import ma.odc.fablabback.entities.Users.Admin;
import ma.odc.fablabback.enums.Role;
import ma.odc.fablabback.exceptions.AppUsersNotFoundException;
import ma.odc.fablabback.mappers.UsersMapperImpl;
import ma.odc.fablabback.repositories.Users.AdminRepository;
import ma.odc.fablabback.requests.AdminRegisterRequest;
import ma.odc.fablabback.services.ISuperAdminService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SuperAdminService implements ISuperAdminService {
  private AdminRepository adminRepository;
  private UsersMapperImpl usersMapper;
  private PasswordEncoder passwordEncoder;

  public AdminDTO addNewAdmin(AdminRegisterRequest request) {
    Admin newAdmin =
        Admin.builder()
            .role(Role.ADMIN)
            .name(request.getName())
            .sex(request.getSex())
            .cin(request.getCin())
            .appUsersname(request.getUsername())
            .email(request.getEmail())
            .birthDate(request.getBirthDate())
            .password(passwordEncoder.encode(request.getPassword()))
            .poste(request.getPoste())
            .build();
    adminRepository.save(newAdmin);
    return usersMapper.adminToDTO(newAdmin);
  }

  @Override
  public void deleteAdmin(Long id) throws AppUsersNotFoundException {
    Admin admin =
        adminRepository
            .findById(id)
            .orElseThrow(() -> new AppUsersNotFoundException("No user found"));
    adminRepository.delete(admin);
  }
}
