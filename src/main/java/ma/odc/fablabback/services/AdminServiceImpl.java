package ma.odc.fablabback.services;

import lombok.AllArgsConstructor;
import ma.odc.fablabback.Exceptions.AppUsersNotFoundException;
import ma.odc.fablabback.dto.usersdto.AdminDTO;
import ma.odc.fablabback.entities.Users.Admin;
import ma.odc.fablabback.entities.Users.AppUser;
import ma.odc.fablabback.mappers.EquipmentMapperImpl;
import ma.odc.fablabback.mappers.UsersMapperImpl;
import ma.odc.fablabback.repositories.Users.AdminRepository;
import ma.odc.fablabback.repositories.Users.AppUsersRepository;
import ma.odc.fablabback.requests.AuthenticationRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements IAdminService {
  private AppUsersRepository appUsersRepository;
  private UsersMapperImpl usersMapper;
  private AdminRepository adminRepository;
  private EquipmentMapperImpl equipmentMapper;

  @Override
  public boolean checkAdmin(AuthenticationRequest authenticationRequest) {
    AppUser user = appUsersRepository.findByAppUsersname(authenticationRequest.getUsername()).orElse(null);
    Admin admin = adminRepository.findById(user.getAppUsersId()).orElse(null);
    if(admin != null){
      return true;
    }
    return false;
  }

//  @Override
//  public AdminDTO approveReservation(ReservationDTO reservationDTO) throws AppUsersNotFoundException {
//    String adminUsername = SecurityContextHolder.getContext().getAuthentication().getName();
//    Admin admin = adminRepository.findByAppUsersname(adminUsername).orElseThrow(AppUsersNotFoundException::new);
//    List<Reservation> approvedReservations;
//    try{
//      approvedReservations = admin.getApprovedReservations();
//      approvedReservations.add(equipmentMapper.dtoToReservation(reservationDTO));
//
//    }
//    catch(NullPointerException e){
//      approvedReservations = new ArrayList<>();
//      approvedReservations.add(equipmentMapper.dtoToReservation(reservationDTO));
//    }
//      Admin saved = adminRepository.save(admin);
//
//    return usersMapper.adminToDTO(saved);
//  }

  public AdminDTO getAdminByName(String username) throws AppUsersNotFoundException {
    Admin admin = adminRepository.findByAppUsersname(username).orElseThrow(AppUsersNotFoundException::new);
    AdminDTO adminDTO = usersMapper.adminToDTO(admin);
    return adminDTO;
  }
}
