package ma.odc.fablabback.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentIssueDTO;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentReservationDTO;
import ma.odc.fablabback.dto.equipmentsdto.ReservationDTO;
import ma.odc.fablabback.entities.equipments.EquipmentIssue;
import ma.odc.fablabback.entities.equipments.Reservation;
import ma.odc.fablabback.enums.EquipmentIssueState;
import ma.odc.fablabback.exceptions.*;
import ma.odc.fablabback.mappers.EquipmentMapper;
import ma.odc.fablabback.repositories.equipments.EquipmentIssueRepository;
import ma.odc.fablabback.requests.EquipmentIssueRequest;
import ma.odc.fablabback.services.IEquipmentIssueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipmentIssueService implements IEquipmentIssueService {
  private final EquipmentIssueRepository equipmentIssueRepository;
  private final EquipmentMapper equipmentMapper;
  private final ReservationService reservationService;
  private final EquipmentService equipmentService;
  private final EquipmentReservationService equipmentReservationService;

  @Override
  public EquipmentIssueDTO createIssue(EquipmentIssueRequest request) {
    EquipmentReservationDTO equipmentReservationDTO =
        equipmentReservationService.getEquipmentReservation(request.getEquipmentReservationId());
    EquipmentIssue issue =
        EquipmentIssue.builder()
            .equipmentIssueState(EquipmentIssueState.ONHOLD)
            .description(request.getDescription())
            .equipmentReservation(
                equipmentMapper.dtoToEquipmentReservation(equipmentReservationDTO))
            .reportedAt(LocalDate.now())
            .build();
    EquipmentIssue saved = equipmentIssueRepository.save(issue);
    return equipmentMapper.equipmentIssueToDTO(saved);
  }

  @Override
  public EquipmentIssueDTO approveIssue(EquipmentIssueDTO issueDTO)
      throws EquipmentIssueNotFoundException,
          UnauthorizedEquipmentIssueActionException,
          EquipmentNotFoundException {
    EquipmentIssue equipmentIssue = getEquipmentIssue(issueDTO.getIssueId());
    equipmentIssue.approveIssue();
    EquipmentIssue approvedIssue = equipmentIssueRepository.save(equipmentIssue);
    equipmentService.decreaseQuantityByOne(equipmentIssue.getEquipmentReservation().getEquipment());
    return equipmentMapper.equipmentIssueToDTO(approvedIssue);
  }

  public EquipmentIssue getEquipmentIssue(String id) throws EquipmentIssueNotFoundException {
    EquipmentIssue equipmentIssue =
        equipmentIssueRepository
            .findById(id)
            .orElseThrow(() -> new EquipmentIssueNotFoundException("No Equipment Issue Found"));
    return equipmentIssue;
  }

  @Override
  public EquipmentIssueDTO approveIssue(String id)
      throws EquipmentIssueNotFoundException,
          UnauthorizedEquipmentIssueActionException,
          EquipmentNotFoundException {
    EquipmentIssue equipmentIssue = getEquipmentIssue(id);
    EquipmentIssueDTO approvedIssue =
        approveIssue(equipmentMapper.equipmentIssueToDTO(equipmentIssue));
    return approvedIssue;
  }

  @Override
  public EquipmentIssueDTO rejectIssue(EquipmentIssueDTO issueDTO)
      throws EquipmentIssueNotFoundException, UnauthorizedEquipmentIssueActionException {
    EquipmentIssue equipmentIssue = getEquipmentIssue(issueDTO.getIssueId());
    equipmentIssue.rejectIssue();
    EquipmentIssue rejectedIssue = equipmentIssueRepository.save(equipmentIssue);
    return equipmentMapper.equipmentIssueToDTO(rejectedIssue);
  }

  @Override
  public EquipmentIssueDTO rejectIssue(String id)
      throws EquipmentIssueNotFoundException, UnauthorizedEquipmentIssueActionException {
    EquipmentIssue issue = getEquipmentIssue(id);
    EquipmentIssueDTO rejectIssue = rejectIssue(equipmentMapper.equipmentIssueToDTO(issue));
    return rejectIssue;
  }

  @Override
  public EquipmentIssueDTO resolveIssue(EquipmentIssueDTO issueDTO)
      throws UnauthorizedEquipmentIssueActionException,
          EquipmentIssueNotFoundException,
          EquipmentNotFoundException {
    EquipmentIssue equipmentIssue = getEquipmentIssue(issueDTO.getIssueId());
    equipmentIssue.resolveIssue();
    EquipmentIssue rejectedIssue = equipmentIssueRepository.save(equipmentIssue);
    equipmentService.increaseQuantityByOne(equipmentIssue.getEquipmentReservation().getEquipment());
    return equipmentMapper.equipmentIssueToDTO(rejectedIssue);
  }

  @Override
  public EquipmentIssueDTO resolveIssue(String id)
      throws EquipmentIssueNotFoundException,
          UnauthorizedEquipmentIssueActionException,
          EquipmentNotFoundException {
    EquipmentIssue issue = getEquipmentIssue(id);
    EquipmentIssueDTO resolvedIssue = resolveIssue(equipmentMapper.equipmentIssueToDTO(issue));
    return resolvedIssue;
  }

  @Override
  public List<EquipmentIssueDTO> getAllEquipmentIssues() {
    List<EquipmentIssueDTO> equipmentIssueDTOS =
        equipmentIssueRepository.findAll().stream()
            .map(issue -> equipmentMapper.equipmentIssueToDTO(issue))
            .collect(Collectors.toList());
    return equipmentIssueDTOS;
  }

  @Override
  public List<EquipmentIssueDTO> getReservationIssues(ReservationDTO reservationDTO)
      throws ReservationNotFoundException {
    Reservation reservation = reservationService.getReservation(reservationDTO.getReservationId());
    List<EquipmentIssueDTO> equipmentIssueDTOS =
        equipmentIssueRepository.findByEquipmentReservation_Reservation(reservation).stream()
            .map(equipmentIssue -> equipmentMapper.equipmentIssueToDTO(equipmentIssue))
            .collect(Collectors.toList());
    return equipmentIssueDTOS;
  }
}
