package ma.odc.fablabback.services;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentDTO;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentReservationDTO;
import ma.odc.fablabback.entities.equipments.Equipment;
import ma.odc.fablabback.entities.equipments.EquipmentReservation;
import ma.odc.fablabback.exceptions.EquipmentNotFoundException;
import ma.odc.fablabback.mappers.EquipmentMapper;
import ma.odc.fablabback.repositories.equipments.EquipmentReservationRepository;
import ma.odc.fablabback.requests.EquipmentAvailabilityRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EquipmentReservationService implements IEquipmentReservationService {
  private EquipmentReservationRepository equipmentReservationRepository;
  private EquipmentMapper equipmentMapper;
  private EquipmentService equipmentService;

  @Override
  public List<EquipmentReservationDTO> getAllEquipmentReservations() {
    List<EquipmentReservation> all = equipmentReservationRepository.findAll();
    List<EquipmentReservationDTO> equipmentReservationDTOS = new ArrayList<>();
    for (EquipmentReservation e : all) {
      equipmentReservationDTOS.add(equipmentMapper.equipmentReservationToDTO(e));
    }
    return equipmentReservationDTOS;
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
  public List<EquipmentReservationDTO> getEquipmentReservationByEquipmentAndDates(
      EquipmentAvailabilityRequest request) throws EquipmentNotFoundException {

    EquipmentDTO equipmentDTO = equipmentService.getEquipment(request.getEquipmenId());
    Equipment equipment = equipmentMapper.dtoToEquipment(equipmentDTO);
    List<EquipmentReservation> equipmentReservationByEquipmentAndDates =
        equipmentReservationRepository
            .findAllByEquipmentAndReservation_EndDateBetweenOrEquipmentAndReservation_EndDateBetween(
                equipment,
                request.getStartDate(),
                request.getEndDate(),
                equipment,
                request.getStartDate(),
                request.getEndDate());
    List<EquipmentReservationDTO> equipmentReservationDTOS = new ArrayList<>();
    for (EquipmentReservation e : equipmentReservationByEquipmentAndDates) {
      equipmentReservationDTOS.add(equipmentMapper.equipmentReservationToDTO(e));
    }
    return equipmentReservationDTOS;
  }
}
