package ma.odc.fablabback.services.impl;

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
import ma.odc.fablabback.enums.EReservationState;
import ma.odc.fablabback.exceptions.*;
import ma.odc.fablabback.mappers.EquipmentMapper;
import ma.odc.fablabback.mappers.UsersMapperImpl;
import ma.odc.fablabback.repositories.equipments.ReservationRepository;
import ma.odc.fablabback.requests.EquipmentReservationRequest;
import ma.odc.fablabback.requests.ReservationRequest;
import ma.odc.fablabback.services.IReservationService;
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
  @Transactional(rollbackFor = {EquipmentNotFoundException.class, UnsatisfiedRequirementException.class})
  public ReservationDTO addNewReservation(ReservationRequest reservationRequest)
      throws EquipmentNotFoundException, UnsatisfiedRequirementException {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    MemberDTO memberByUsername = memberService.getMemberByUsername(username);
    Member member = usersMapper.dtoToMembre(memberByUsername);

    // ! save the reservation with no equipment reservation then add the equipment reservations

    Reservation reservation =
        Reservation.builder()
            .startDate(reservationRequest.getStartDate())
            .endDate(reservationRequest.getEndDate())
            .member(member)
            .reservationState(EReservationState.ONHOLD)
            .build();

    Reservation saved1 = reservationRepository.save(reservation);

    List<EquipmentReservation> equipmentReservationList = new ArrayList<>();

//    List<Equipment> unavailableEquipments = new ArrayList<>();

    for (EquipmentReservationRequest e : reservationRequest.getEquipmentReservationRequests()) {

      // ! this should not save to db
      EquipmentReservationDTO equipmentReservationDto =
          equipmentReservationService.createEquipmentReservation(e, saved1);

      EquipmentReservation equipmentReservation =
          equipmentMapper.dtoToEquipmentReservation(equipmentReservationDto);

      equipmentReservationList.add(equipmentReservation);

      // ! VERIFY IF CAN BE RESERVED
      //      boolean checkEquipmentAvailabiltiy =
      // equipmentService.checkEquipmentAvailabiltiy(equipmentReservation);
      //
      //      if (checkEquipmentAvailabiltiy){
      //
      //      }
      //      else {
      //        // ! Find A WAY TO NOTIFY USER THAT THIS EQUIPMENT IS NOT AVAILABLE
      //        throw new UnsatisfiedRequirementException("Quantity or equipment are unavailable");
      //      }
    }
    reservation.setEquipmentReservationList(equipmentReservationList);

    return equipmentMapper.reservationToDTO(reservationRepository.save(reservation));
  }

  @Override
  public ReservationDTO getReservationDto(String id) throws ReservationNotFoundException {
    Reservation reservation =
        reservationRepository
            .findById(id)
            .orElseThrow(() -> new ReservationNotFoundException("No reservation found"));
    return equipmentMapper.reservationToDTO(reservation);
  }

  @Override
  public Reservation getReservation(String id) throws ReservationNotFoundException {
    return reservationRepository
        .findById(id)
        .orElseThrow(() -> new ReservationNotFoundException("No reservation found"));
  }

  @Override
  public Reservation setReservationAdmin(Reservation reservation) throws AppUsersNotFoundException {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String adminUsername = authentication.getName();
    AdminDTO adminByName = adminService.getAdminByName(adminUsername);
    Admin admin = usersMapper.dtoToAdmin(adminByName);
    reservation.setAdmin(admin);
    return reservationRepository.save(reservation);
  }

  @Override
  public ReservationDTO approveReservation(ReservationDTO reservationDTO)
      throws AppUsersNotFoundException,
          EquipmentNotFoundException,
          UnsatisfiedRequirementException,
          UnAuthorizedReservationAction {
    Reservation reservation = equipmentMapper.dtoToReservation(reservationDTO);

    if (verifyReservationEquipment(reservationDTO)) {
      reservation.confirmReservation();
      Reservation savedReservation = setReservationAdmin(reservation);
      return equipmentMapper.reservationToDTO(savedReservation);
    } else {
      throw new UnsatisfiedRequirementException("Some equipments are not available");
    }
  }

  @Override
  public ReservationDTO approveReservation(String id)
      throws ReservationNotFoundException,
          AppUsersNotFoundException,
          EquipmentNotFoundException,
          UnsatisfiedRequirementException,
          UnAuthorizedReservationAction {
    Reservation reservation = getReservation(id);
    ReservationDTO reservationDTO = equipmentMapper.reservationToDTO(reservation);
    return approveReservation(reservationDTO);
  }

  @Override
  public ReservationDTO rejectReservation(String id) throws ReservationNotFoundException {
    Reservation reservation = getReservation(id);
    // manage the state to be rejected
    return null;
  }

  @Override
  public ReservationDTO rejectReservation(ReservationDTO reservationDTO)
      throws ReservationNotFoundException {
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

    for (int i = 0; i < equipmentReservationList.size(); i++) {
      if (!(equipmentService.checkEquipmentAvailabiltiy(
          equipmentReservationList.get(i), reservation))) {
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

  @Override
  public ReservationDTO endReservation(ReservationDTO reservationDTO) {
    return null;
  }

  @Override
  public ReservationDTO endReservation(String id) {
    return null;
  }

  @Override
  public ReservationDTO startReservation(String id)
      throws ReservationNotFoundException,
          EquipmentNotFoundException,
          UnsatisfiedRequirementException,
          AppUsersNotFoundException,
          UnAuthorizedReservationAction {
    Reservation reservation =
        reservationRepository
            .findById(id)
            .orElseThrow(() -> new ReservationNotFoundException("No reservation found"));

    ReservationDTO reservationDTO = equipmentMapper.reservationToDTO(reservation);

    if (verifyReservationEquipment(reservationDTO)) {
      reservation.startReservation();
      Reservation savedReservation = setReservationAdmin(reservation);
      return equipmentMapper.reservationToDTO(savedReservation);
    } else {
      throw new UnsatisfiedRequirementException("Some equipments are not available");
    }
  }

  @Override
  public ReservationDTO startReservation(ReservationDTO reservationDTO) {
    Reservation reservation = equipmentMapper.dtoToReservation(reservationDTO);

    return null;
  }
}
