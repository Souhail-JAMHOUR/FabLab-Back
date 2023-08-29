package ma.odc.fablabback.services;

import lombok.AllArgsConstructor;
import ma.odc.fablabback.Exceptions.EquipmentNotFoundException;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentDTO;
import ma.odc.fablabback.entities.equipments.Equipment;
import ma.odc.fablabback.mappers.EquipmentMapperImpl;
import ma.odc.fablabback.repositories.equipments.EquipmentRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IEquipmentServiceImpl implements IEquipmentService {
  private  EquipmentMapperImpl equipmentMapper;
  private EquipmentRepository equipmentRepository;
  @Override
  public boolean checkEquipmentAvailabiltiy(Equipment equipment, int requestedQuantity) {
    int globalQuantity = equipmentRepository.findAll().size();
    return requestedQuantity > globalQuantity;
  }

  @Override
  public EquipmentDTO addNewEquipment(EquipmentDTO newEquipment) {
    Equipment equipment = equipmentMapper.dtoToEquipment(newEquipment);
    Equipment saved = equipmentRepository.save(equipment);
    return equipmentMapper.equipmentToDTO(saved);
  }

  @Override
  public EquipmentDTO getEquipment(Long id) throws EquipmentNotFoundException {
    Equipment equipment = equipmentRepository.findById(id).orElseThrow(EquipmentNotFoundException::new);
    return equipmentMapper.equipmentToDTO(equipment);
  }

  @Override
  public void deleteEquipment(Long id) throws EquipmentNotFoundException {
    Equipment equipment = equipmentRepository.findById(id).orElseThrow(EquipmentNotFoundException::new);
    equipmentRepository.delete(equipment);

  }

  @Override
  public EquipmentDTO updateEquipment(EquipmentDTO equipmentDTO) {
    Equipment equipment = equipmentMapper.dtoToEquipment(equipmentDTO);
    Equipment saved = equipmentRepository.save(equipment);
    return equipmentMapper.equipmentToDTO(saved);
  }
}
