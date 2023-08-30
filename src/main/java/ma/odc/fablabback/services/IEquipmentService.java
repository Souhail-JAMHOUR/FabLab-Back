package ma.odc.fablabback.services;

import ma.odc.fablabback.dto.equipmentsdto.EquipmentDTO;
import ma.odc.fablabback.entities.equipments.Equipment;
import ma.odc.fablabback.exceptions.EquipmentNotFoundException;

public interface IEquipmentService {
  boolean checkEquipmentAvailabiltiy(Equipment equipment, int requestedQuantity);

  EquipmentDTO addNewEquipment(EquipmentDTO newEquipment);

  EquipmentDTO getEquipment(Long id) throws EquipmentNotFoundException;

  void deleteEquipment(Long id) throws EquipmentNotFoundException;

  EquipmentDTO updateEquipment(EquipmentDTO equipmentDTO);

  
}
