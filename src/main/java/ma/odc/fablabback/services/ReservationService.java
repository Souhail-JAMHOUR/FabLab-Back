package ma.odc.fablabback.services;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentReservationDTO;
import ma.odc.fablabback.dto.equipmentsdto.ReservationDTO;
import ma.odc.fablabback.dto.usersdto.AdminDTO;
import ma.odc.fablabback.dto.usersdto.MemberDTO;
import ma.odc.fablabback.entities.Users.Admin;
import ma.odc.fablabback.entities.Users.Member;
import ma.odc.fablabback.entities.equipments.EquipmentReservation;
import ma.odc.fablabback.entities.equipments.Reservation;
import ma.odc.fablabback.exceptions.AppUsersNotFoundException;
import ma.odc.fablabback.exceptions.EquipmentNotFoundException;
import ma.odc.fablabback.exceptions.ReservationNotFoundException;
import ma.odc.fablabback.exceptions.UnsatisfiedRequirementException;
import ma.odc.fablabback.mappers.EquipmentMapper;
import ma.odc.fablabback.mappers.UsersMapperImpl;
import ma.odc.fablabback.repositories.equipments.ReservationRepository;
import ma.odc.fablabback.requests.EquipmentReservationRequest;
import ma.odc.fablabback.requests.ReservationRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class ReservationService implements IReservationService {
  private EquipmentService equipmentService;
  private EquipmentReservationService equipmentReservationService;
  private AdminServiceImpl adminService;
  private MemberService memberService;
  private UsersMapperImpl usersMapper;
  private EquipmentMapper equipmentMapper;
  private ReservationRepository reservationRepository;

  @Override
  public ReservationDTO addNewReservation(ReservationRequest reservationRequest) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    MemberDTO memberByUsername = memberService.getMemberByUsername(username);
    Member member = usersMapper.dtoToMembre(memberByUsername);

    // ! save the reservation with no equipment reservation then add the equipment reservations

    Reservation reservation =
        Reservation.builder()
            .startDate(reservationRequest.getStartDate())
            .endDate(reservationRequest.getEndDate())
            .member(member)
            .build();

    Reservation saved1 = reservationRepository.save(reservation);

    List<EquipmentReservation> equipmentReservationList = new ArrayList<>();

    for (EquipmentReservationRequest e : reservationRequest.getEquipmentReservationRequests()) {
      EquipmentReservationDTO equipmentReservationDto =
          equipmentReservationService.createEquipmentReservation(e, saved1);
      EquipmentReservation equipmentReservation =
          equipmentMapper.dtoToEquipmentReservation(equipmentReservationDto);
      equipmentReservationList.add(equipmentReservation);
    }
    reservation.setEquipmentReservationList(equipmentReservationList);

    return equipmentMapper.reservationToDTO(reservationRepository.save(reservation));
  }

  @Override
  public ReservationDTO getReservation(String id) throws ReservationNotFoundException {
    Reservation reservation =
        reservationRepository
            .findById(id)
            .orElseThrow(() -> new ReservationNotFoundException("No reservation found"));
    return equipmentMapper.reservationToDTO(reservation);
  }

  @Override
  public ReservationDTO approveReservation(String id)
      throws ReservationNotFoundException,
          AppUsersNotFoundException,
          EquipmentNotFoundException,
          UnsatisfiedRequirementException {
    Reservation reservation =
        reservationRepository
            .findById(id)
            .orElseThrow(() -> new ReservationNotFoundException("No reservation found"));

    ReservationDTO reservationDTO = equipmentMapper.reservationToDTO(reservation);
    
    if (verifyReservationEquipment(reservationDTO)) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String adminUsername = authentication.getName();
      AdminDTO adminByName = adminService.getAdminByName(adminUsername);
      Admin admin = usersMapper.dtoToAdmin(adminByName);
      reservation.setAdmin(admin);
      Reservation saved = reservationRepository.save(reservation);
      return equipmentMapper.reservationToDTO(saved);
    } else {
      throw new UnsatisfiedRequirementException("Some equipments are not available");
    }
    // manage the state

  }

  @Override
  public ReservationDTO approveReservation(ReservationDTO reservationDTO)
      throws ReservationNotFoundException,
          AppUsersNotFoundException,
          EquipmentNotFoundException,
          UnsatisfiedRequirementException {
    Reservation reservation = equipmentMapper.dtoToReservation(reservationDTO);

    if (verifyReservationEquipment(reservationDTO)) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String adminUsername = authentication.getName();
      AdminDTO adminByName = adminService.getAdminByName(adminUsername);
      Admin admin = usersMapper.dtoToAdmin(adminByName);
      reservation.setAdmin(admin);
      Reservation saved = reservationRepository.save(reservation);
      return equipmentMapper.reservationToDTO(saved);
    } else {
      throw new UnsatisfiedRequirementException("Some equipments are not available");
    }
  }

  @Override
  public ReservationDTO rejectReservation(String id) throws ReservationNotFoundException {
    Reservation reservation =
        reservationRepository
            .findById(id)
            .orElseThrow(() -> new ReservationNotFoundException("No reservation found"));
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

  @Override
  public boolean verifyReservationEquipment(ReservationDTO reservationDTO)
      throws EquipmentNotFoundException {
    Reservation reservation = equipmentMapper.dtoToReservation(reservationDTO);
    List<EquipmentReservation> equipmentReservationList = reservation.getEquipmentReservationList();
    boolean allAvailable = true;

    for (int i = 0; i > equipmentReservationList.size(); ++i) {
      if (!(equipmentService.checkEquipmentAvailabiltiy(equipmentReservationList.get(i)))) {
        allAvailable = false;
      }
    }
    return allAvailable;
  }

  @Override
  public ReservationDTO cancelReservation(String id) {
    return null;
  }

  @Override
  public ReservationDTO cancelReservation(ReservationDTO reservationDTO) {
    return null;
  }
}
