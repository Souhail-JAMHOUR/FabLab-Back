package ma.odc.fablabback.services;

import lombok.AllArgsConstructor;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentDTO;
import ma.odc.fablabback.entities.equipments.Equipment;
import ma.odc.fablabback.entities.equipments.EquipmentReservation;
import ma.odc.fablabback.exceptions.EquipmentNotFoundException;
import ma.odc.fablabback.mappers.EquipmentMapper;
import ma.odc.fablabback.repositories.equipments.EquipmentRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EquipmentService implements IEquipmentService {
  private EquipmentMapper equipmentMapper;
  private EquipmentRepository equipmentRepository;
  @Override
  public boolean checkEquipmentAvailabiltiy(EquipmentReservation equipmentReservation) throws EquipmentNotFoundException {
    Equipment equipment = equipmentRepository.findById(equipmentReservation.getEquipment().getId()).orElseThrow(() -> new EquipmentNotFoundException("No Equipment found"));
    int globalQuantity = equipment.getQuantity();
    int requestedQuantity = equipmentReservation.getRequestedQuantity();
    return globalQuantity>requestedQuantity;
  }

  @Override
  public EquipmentDTO addNewEquipment(EquipmentDTO newEquipment) {
    Equipment equipment = equipmentMapper.dtoToEquipment(newEquipment);
    Equipment saved = equipmentRepository.save(equipment);
    return equipmentMapper.equipmentToDTO(saved);
  }

  @Override
  public EquipmentDTO getEquipment(Long id) throws EquipmentNotFoundException {
    Equipment equipment = equipmentRepository.findById(id).orElseThrow(()->new EquipmentNotFoundException("No Equipment found"));
    return equipmentMapper.equipmentToDTO(equipment);
  }

  @Override
  public void deleteEquipment(Long id) throws EquipmentNotFoundException {
    Equipment equipment = equipmentRepository.findById(id).orElseThrow(()->new EquipmentNotFoundException("No Equipment found"));
    equipmentRepository.delete(equipment);

  }

  @Override
  public EquipmentDTO updateEquipment(EquipmentDTO equipmentDTO) {
    Equipment equipment = equipmentMapper.dtoToEquipment(equipmentDTO);
    Equipment saved = equipmentRepository.save(equipment);
    return equipmentMapper.equipmentToDTO(saved);
  }
}
