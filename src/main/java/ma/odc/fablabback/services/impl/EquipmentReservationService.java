package ma.odc.fablabback.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentDTO;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentReservationDTO;
import ma.odc.fablabback.entities.equipments.Equipment;
import ma.odc.fablabback.entities.equipments.EquipmentReservation;
import ma.odc.fablabback.entities.equipments.Reservation;
import ma.odc.fablabback.enums.EReservationState;
import ma.odc.fablabback.exceptions.EquipmentNotFoundException;
import ma.odc.fablabback.exceptions.UnsatisfiedRequirementException;
import ma.odc.fablabback.mappers.EquipmentMapper;
import ma.odc.fablabback.repositories.equipments.EquipmentReservationRepository;
import ma.odc.fablabback.requests.EquipmentAvailabilityRequest;
import ma.odc.fablabback.requests.EquipmentReservationRequest;
import ma.odc.fablabback.services.IEquipmentReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class EquipmentReservationService implements IEquipmentReservationService {
  private EquipmentReservationRepository equipmentReservationRepository;
  private EquipmentMapper equipmentMapper;
  private EquipmentService equipmentService;

  @Override
  public Page<EquipmentReservationDTO> getAllEquipmentReservations(int page, int size) {
    Page<EquipmentReservation> all =
        equipmentReservationRepository.findAll(PageRequest.of(page, size));
    List<EquipmentReservationDTO> collect =
        all.getContent().stream()
            .map(
                equipmentReservation ->
                    equipmentMapper.equipmentReservationToDTO(equipmentReservation))
            .collect(Collectors.toList());
    return new PageImpl<>(collect, PageRequest.of(page, size), all.getTotalElements());
  }

  @Override
  public List<EquipmentReservationDTO> getEquipmentReservationByEquipment(
      EquipmentDTO equipmentDTO) {
    Equipment equipment = equipmentMapper.dtoToEquipment(equipmentDTO);
    List<EquipmentReservation> equipmentReservationByEquipment =
        equipmentReservationRepository.findEquipmentReservationByEquipment(equipment);
    List<EquipmentReservationDTO> equipmentReservationDTOS = new ArrayList<>();
    for (EquipmentReservation e : equipmentReservationByEquipment) {
      equipmentReservationDTOS.add(equipmentMapper.equipmentReservationToDTO(e));
    }
    return equipmentReservationDTOS;
  }

  @Override
  public List<EquipmentReservationDTO> getEquipmentReservationByEquipmentAndDatesAndState(
      EquipmentAvailabilityRequest request) throws EquipmentNotFoundException {
    EquipmentDTO equipmentDTO = equipmentService.getEquipment(request.getEquipmentId());
    Equipment equipment = equipmentMapper.dtoToEquipment(equipmentDTO);
    List<EquipmentReservationDTO> collected =
        equipmentReservationRepository
            .findAllByEquipmentAndReservation_ReservationStateInAndReservation_StartDateBetweenOrEquipmentAndReservation_ReservationStateInAndReservation_EndDateBetween(
                equipment,
                List.of(EReservationState.IN_PROGRESS, EReservationState.CONFIRMED),
                request.getStartDate(),
                request.getEndDate(),
                equipment,
                List.of(EReservationState.IN_PROGRESS, EReservationState.CONFIRMED),
                request.getStartDate(),
                request.getEndDate())
            .stream()
            .map(equipmentMapper::equipmentReservationToDTO)
            .collect(Collectors.toList());
    return collected;
  }

  @Override
  public List<EquipmentReservationDTO> getEquipmentReservationByEquipmentAndDates(
      EquipmentAvailabilityRequest request) throws EquipmentNotFoundException {

    EquipmentDTO equipmentDTO = equipmentService.getEquipment(request.getEquipmentId());
    Equipment equipment = equipmentMapper.dtoToEquipment(equipmentDTO);

    // ! without state
    List<EquipmentReservation> equipmentReservationByEquipmentAndDates =
        equipmentReservationRepository
            .findAllByEquipmentAndReservation_EndDateBetweenOrEquipmentAndReservation_EndDateBetween(
                equipment,
                request.getStartDate(),
                request.getEndDate(),
                equipment,
                request.getStartDate(),
                request.getEndDate());

    // ! WITH STATE

    List<EquipmentReservationDTO> equipmentReservationDTOS = new ArrayList<>();
    for (EquipmentReservation e : equipmentReservationByEquipmentAndDates) {
      equipmentReservationDTOS.add(equipmentMapper.equipmentReservationToDTO(e));
    }
    return equipmentReservationDTOS;
  }

  @Override
  public EquipmentReservationDTO createEquipmentReservation(
      EquipmentReservationRequest equipmentReservationRequest, Reservation reservation)
      throws EquipmentNotFoundException, UnsatisfiedRequirementException {

    EquipmentDTO equipmentDto =
        equipmentService.getEquipment(equipmentReservationRequest.getEquipmentId());
    Equipment equipment = equipmentMapper.dtoToEquipment(equipmentDto);
    EquipmentReservation equipmentReservation =
        EquipmentReservation.builder()
            .equipment(equipment)
            .requestedQuantity(equipmentReservationRequest.getRequestedQuantity())
            .reservation(reservation)
            .build();

    // ! check if the requirements are satisfied

    boolean checkEquipmentAvailabiltiy =
        equipmentService.checkEquipmentAvailabiltiy(equipmentReservation, reservation);
    if (checkEquipmentAvailabiltiy) {
      EquipmentReservation saved = equipmentReservationRepository.save(equipmentReservation);
      return equipmentMapper.equipmentReservationToDTO(saved);
    } else {
      throw new UnsatisfiedRequirementException("equipment cannot be added");
    }
  }

  @Override
  public EquipmentReservationDTO saveEquipmentReservation(
      EquipmentReservationDTO equipmentReservationDTO) {
    EquipmentReservation saved =
        equipmentReservationRepository.save(
            equipmentMapper.dtoToEquipmentReservation(equipmentReservationDTO));
    return equipmentMapper.equipmentReservationToDTO(saved);
  }

  @Override
  public List<EquipmentReservationDTO> getEquipmentAvailabilityForAdmin(
      EquipmentAvailabilityRequest request) throws EquipmentNotFoundException {
    EquipmentDTO equipment = equipmentService.getEquipment(request.getEquipmentId());
    List<EquipmentReservationDTO> collected =
        equipmentReservationRepository
            .findAllByEquipmentAndReservation_ReservationStateInAndReservation_StartDateLessThanEqualAndReservation_EndDateGreaterThanEqual(
                equipmentMapper.dtoToEquipment(equipment),
                List.of(EReservationState.IN_PROGRESS, EReservationState.CONFIRMED),
                request.getStartDate(),
                request.getEndDate().minusDays(1))
            .stream()
            .map(equipmentMapper::equipmentReservationToDTO)
            .collect(Collectors.toList());
    return collected;
  }

  public EquipmentReservationDTO getEquipmentReservation(String equipmentReservationId) {
    EquipmentReservation equipmentReservation =
        equipmentReservationRepository.findById(equipmentReservationId).orElse(null);
    return equipmentMapper.equipmentReservationToDTO(equipmentReservation);
  }
}
