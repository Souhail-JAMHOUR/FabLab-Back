package ma.odc.fablabback.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
  @Transactional(
      rollbackFor = {
        EquipmentNotFoundException.class,
        UnsatisfiedRequirementException.class,
        AppUsersNotFoundException.class
      })
  public ReservationDTO addNewReservation(ReservationRequest reservationRequest)
      throws EquipmentNotFoundException,
          UnsatisfiedRequirementException,
          AppUsersNotFoundException {
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
  public List<ReservationDTO> getMemberReservations(MemberDTO memberDTO) {
    List<ReservationDTO> collected =
        reservationRepository.findAllByMember(usersMapper.dtoToMembre(memberDTO)).stream()
            .map(equipmentMapper::reservationToDTO)
            .collect(Collectors.toList());
    return collected;
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
  public ReservationDTO rejectReservation(String id)
      throws ReservationNotFoundException,
          UnAuthorizedReservationAction,
          AppUsersNotFoundException {
    Reservation reservation =
        reservationRepository
            .findById(id)
            .orElseThrow(() -> new ReservationNotFoundException("No reservation found"));

    reservation.rejectReservation();
    Reservation savedReservation = setReservationAdmin(reservation);
    return equipmentMapper.reservationToDTO(savedReservation);
  }

  @Override
  public ReservationDTO rejectReservation(ReservationDTO reservationDTO)
      throws ReservationNotFoundException,
          UnAuthorizedReservationAction,
          AppUsersNotFoundException {
    Reservation reservation = getReservation(reservationDTO.getReservationId());
    ReservationDTO startedReservation = rejectReservation(reservation.getReservationId());
    return startedReservation;
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
  public ReservationDTO cancelReservation(String id)
      throws ReservationNotFoundException,
          UnAuthorizedReservationAction,
          AppUsersNotFoundException {
    Reservation reservation =
        reservationRepository
            .findById(id)
            .orElseThrow(() -> new ReservationNotFoundException("No reservation found"));

    reservation.cancelReservation();
    Reservation savedReservation = setReservationAdmin(reservation);
    return equipmentMapper.reservationToDTO(savedReservation);
  }

  @Override
  public ReservationDTO cancelReservation(ReservationDTO reservationDTO)
      throws ReservationNotFoundException,
          UnAuthorizedReservationAction,
          AppUsersNotFoundException {
    Reservation reservation = getReservation(reservationDTO.getReservationId());
    ReservationDTO startedReservation = cancelReservation(reservation.getReservationId());
    return startedReservation;
  }

  @Override
  public ReservationDTO endReservation(ReservationDTO reservationDTO)
      throws ReservationNotFoundException,
          UnAuthorizedReservationAction,
          AppUsersNotFoundException {
    Reservation reservation = getReservation(reservationDTO.getReservationId());
    ReservationDTO startedReservation = endReservation(reservation.getReservationId());
    return startedReservation;
  }

  @Override
  public ReservationDTO endReservation(String id)
      throws ReservationNotFoundException,
          UnAuthorizedReservationAction,
          AppUsersNotFoundException {
    Reservation reservation =
        reservationRepository
            .findById(id)
            .orElseThrow(() -> new ReservationNotFoundException("No reservation found"));

    reservation.endReservation();
    Reservation savedReservation = setReservationAdmin(reservation);
    return equipmentMapper.reservationToDTO(savedReservation);
  }

  @Override
  public ReservationDTO startReservation(String id)
      throws ReservationNotFoundException,
          AppUsersNotFoundException,
          UnAuthorizedReservationAction {
    Reservation reservation =
        reservationRepository
            .findById(id)
            .orElseThrow(() -> new ReservationNotFoundException("No reservation found"));

    reservation.startReservation();
    Reservation savedReservation = setReservationAdmin(reservation);
    return equipmentMapper.reservationToDTO(savedReservation);
  }

  @Override
  public ReservationDTO startReservation(ReservationDTO reservationDTO)
      throws ReservationNotFoundException,
          UnAuthorizedReservationAction,
          AppUsersNotFoundException {
    Reservation reservation = getReservation(reservationDTO.getReservationId());
    ReservationDTO startedReservation = startReservation(reservation.getReservationId());
    return startedReservation;
  }
}
