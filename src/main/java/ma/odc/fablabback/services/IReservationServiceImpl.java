package ma.odc.fablabback.services;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import ma.odc.fablabback.Exceptions.AppUsersNotFoundException;
import ma.odc.fablabback.Exceptions.ReservationNotFoundException;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentReservationDTO;
import ma.odc.fablabback.dto.equipmentsdto.ReservationDTO;
import ma.odc.fablabback.dto.usersdto.AdminDTO;
import ma.odc.fablabback.entities.Users.Admin;
import ma.odc.fablabback.entities.Users.Member;
import ma.odc.fablabback.entities.equipments.EquipmentReservation;
import ma.odc.fablabback.entities.equipments.Reservation;
import ma.odc.fablabback.mappers.EquipmentMapperImpl;
import ma.odc.fablabback.mappers.UsersMapperImpl;
import ma.odc.fablabback.repositories.equipments.ReservationRepository;
import ma.odc.fablabback.requests.ReservationRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class IReservationServiceImpl implements IReservationService {
  private EquipmentMapperImpl equipmentMapper;
  private UsersMapperImpl usersMapper;
  private ReservationRepository reservationRepository;
  private IEquipmentServiceImpl equipmentService;
  private AdminServiceImpl adminService;

  @Override
  public ReservationDTO addNewReservation(ReservationRequest reservationRequest) {
    Member member = usersMapper.dtoToMembre(reservationRequest.getMember());
    List<EquipmentReservation> equipmentReservationList = new ArrayList<>();
    for (EquipmentReservationDTO e : reservationRequest.getEquipmentReservationList()) {
      EquipmentReservation equipmentReservation = equipmentMapper.dtoToEquipmentReservation(e);
      equipmentReservationList.add(equipmentReservation);
    }
    Reservation reservation =
        Reservation.builder()
            .startDate(reservationRequest.getStartDate())
            .endDate(reservationRequest.getEndDate())
            .member(member)
            .equipmentReservationList(equipmentReservationList)
            .build();

    Reservation savedReservation = reservationRepository.save(reservation);
    return equipmentMapper.reservationToDTO(savedReservation);
  }

  @Override
  public ReservationDTO getReservation(String id) throws ReservationNotFoundException {
    Reservation reservation =
        reservationRepository.findById(id).orElseThrow(ReservationNotFoundException::new);
    return equipmentMapper.reservationToDTO(reservation);
  }

  @Override
  public ReservationDTO approveReservation(String id)
      throws ReservationNotFoundException, AppUsersNotFoundException {
    Reservation reservation =
        reservationRepository.findById(id).orElseThrow(ReservationNotFoundException::new);

    ReservationDTO reservationDTO = equipmentMapper.reservationToDTO(reservation);

    // check if the equipments still available
    List<EquipmentReservation> equipmentReservationList = reservation.getEquipmentReservationList();
    boolean allAvailable = true;

    for (int i = 0; i > equipmentReservationList.size(); ++i) {
      if (!(equipmentService.checkEquipmentAvailabiltiy(
          equipmentReservationList.get(i).getEquipment(),
          equipmentReservationList.get(i).getRequestedQuantity()))) {
        allAvailable = false;
      }
    }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String adminUsername = authentication.getName();
    AdminDTO adminByName = adminService.getAdminByName(adminUsername);
    Admin admin = usersMapper.dtoToAdmin(adminByName);
    reservation.setAdmin(admin);
    reservationRepository.save(reservation);

    // manage the state

    return null;
  }

  @Override
  public ReservationDTO rejectReservation(String id) throws ReservationNotFoundException {
    Reservation reservation =
        reservationRepository.findById(id).orElseThrow(ReservationNotFoundException::new);
    // manage the state to be rejected
    return null;
  }

  @Override
  public List<ReservationDTO> getAllReservations() {
    List<Reservation> allReservations = reservationRepository.findAll();
    List<ReservationDTO> reservationDTOS = new ArrayList<>();
    for (Reservation r : allReservations) {
      ReservationDTO reservationDTO = equipmentMapper.reservationToDTO(r);
      reservationDTOS.add(reservationDTO);
    }
    return reservationDTOS;
  }
}
