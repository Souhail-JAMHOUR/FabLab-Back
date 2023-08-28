package ma.odc.fablabback.services;

import ma.odc.fablabback.dto.equipmentsdto.EquipmentDTO;

public interface IEquipmentService {
    boolean checkEquipmentAvailabiltiy(EquipmentDTO equipmentDTO);

    EquipmentDTO addNewEquipment(EquipmentDTO newEquipment);

    EquipmentDTO getEquipment(Long id);

    void deleteEquipment(Long id);

    EquipmentDTO updateEquipment(EquipmentDTO equipmentDTO);
    

}
