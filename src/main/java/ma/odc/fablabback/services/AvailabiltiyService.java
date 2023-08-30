package ma.odc.fablabback.services;

import lombok.AllArgsConstructor;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentDTO;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AvailabiltiyService {
    private EquipmentService equipmentService;

    public boolean isEquipmentAvailable(EquipmentDTO equipmentDTO){
        // * check the available quantity with the requested quantity

        // * 
        return false;
    }
}
