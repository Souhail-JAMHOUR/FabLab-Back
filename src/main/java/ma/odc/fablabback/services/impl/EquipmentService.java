package ma.odc.fablabback.services.impl;

import java.util.List;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentDTO;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentReservationDTO;
import ma.odc.fablabback.entities.equipments.Equipment;
import ma.odc.fablabback.entities.equipments.EquipmentReservation;
import ma.odc.fablabback.entities.equipments.Reservation;
import ma.odc.fablabback.exceptions.EquipmentNotFoundException;
import ma.odc.fablabback.mappers.EquipmentMapper;
import ma.odc.fablabback.repositories.equipments.EquipmentRepository;
import ma.odc.fablabback.requests.EquipmentAvailabilityRequest;
import ma.odc.fablabback.services.IEquipmentService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class EquipmentService implements IEquipmentService {
  private EquipmentMapper equipmentMapper;
  private EquipmentRepository equipmentRepository;
  private EquipmentReservationService equipmentReservationService;

  public EquipmentService(
      EquipmentMapper equipmentMapper,
      EquipmentRepository equipmentRepository,
      @Lazy EquipmentReservationService equipmentReservationService) {
    this.equipmentMapper = equipmentMapper;
    this.equipmentRepository = equipmentRepository;
    this.equipmentReservationService = equipmentReservationService;
  }

  @Override
  public boolean checkEquipmentAvailabiltiy(
      EquipmentReservation equipmentReservation, Reservation reservation)
      throws EquipmentNotFoundException {
    Equipment equipment =
        equipmentRepository
            .findById(equipmentReservation.getEquipment().getId())
            .orElseThrow(() -> new EquipmentNotFoundException("No Equipment found"));

    // ! Verify according to the global quantity first

    int globalQuantity = equipment.getQuantity();
    int requestedQuantity = equipmentReservation.getRequestedQuantity();

    int reservedQuantity = 0;

    // ! Verifiy according to the equipment reservations

    // ? need to inject an availability request and quest by date
    EquipmentAvailabilityRequest equipmentAvailabilityRequest =
        EquipmentAvailabilityRequest.builder()
            .equipmentId(equipment.getId())
            .startDate(reservation.getStartDate())
            .endDate(reservation.getEndDate())
            .build();

    //    List<EquipmentReservationDTO> equipmentReservationByEquipment =
    //        equipmentReservationService.getEquipmentReservationByEquipment(
    //            equipmentMapper.equipmentToDTO(equipment));

    List<EquipmentReservationDTO> equipmentReservationByEquipmentAndDates =
        equipmentReservationService.getEquipmentReservationByEquipmentAndDatesAndState(
            equipmentAvailabilityRequest);

    for (EquipmentReservationDTO e : equipmentReservationByEquipmentAndDates) {
      reservedQuantity += e.getRequestedQuantity();
    }

    return (globalQuantity >= requestedQuantity
        && requestedQuantity + reservedQuantity <= globalQuantity);
  }

  @Override
  public EquipmentDTO addNewEquipment(EquipmentDTO newEquipment) {
    Equipment equipment = equipmentMapper.dtoToEquipment(newEquipment);
    Equipment saved = equipmentRepository.save(equipment);
    return equipmentMapper.equipmentToDTO(saved);
  }

  @Override
  public EquipmentDTO getEquipment(Long id) throws EquipmentNotFoundException {
    Equipment equipment =
        equipmentRepository
            .findById(id)
            .orElseThrow(() -> new EquipmentNotFoundException("No Equipment found"));
    return equipmentMapper.equipmentToDTO(equipment);
  }

  @Override
  public void deleteEquipment(Long id) throws EquipmentNotFoundException {
    Equipment equipment =
        equipmentRepository
            .findById(id)
            .orElseThrow(() -> new EquipmentNotFoundException("No Equipment found"));
    equipmentRepository.delete(equipment);
  }

  @Override
  public EquipmentDTO updateEquipment(EquipmentDTO equipmentDTO) {
    Equipment equipment = equipmentMapper.dtoToEquipment(equipmentDTO);
    Equipment saved = equipmentRepository.save(equipment);
    return equipmentMapper.equipmentToDTO(saved);
  }
}
