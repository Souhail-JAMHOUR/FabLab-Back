package ma.odc.fablabback.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentDTO;
import ma.odc.fablabback.exceptions.CatergoryNotFoundException;
import ma.odc.fablabback.exceptions.EquipmentNotFoundException;
import ma.odc.fablabback.requests.EquipmentAvailabilityResponse;
import ma.odc.fablabback.requests.NewEquipmentRequest;
import ma.odc.fablabback.services.impl.EquipmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/equipment")
public class EquipmentController {
  private final EquipmentService equipmentService;

  @GetMapping("/all")
  public ResponseEntity<List<EquipmentDTO>> getAllEquipments() {
    List<EquipmentDTO> allEquipments = equipmentService.getAllEquipments();
    return ResponseEntity.ok(allEquipments);
  }

  @GetMapping("/check/{id}")
  public ResponseEntity<EquipmentAvailabilityResponse> checkEquipment(@PathVariable long id)
      throws EquipmentNotFoundException {
    EquipmentAvailabilityResponse response =
        equipmentService.checkEquipmentAvailabilityForAdmin(id);
    return ResponseEntity.ok(response);
  }
  @PostMapping("/new")
  public ResponseEntity<EquipmentDTO>addNewEquipment(@RequestBody NewEquipmentRequest request) throws CatergoryNotFoundException {
    EquipmentDTO equipmentDTO = equipmentService.addNewEquipment(request);
    return ResponseEntity.ok(equipmentDTO);
  }
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String>deleteEquipment(@PathVariable Long id) throws EquipmentNotFoundException {
    equipmentService.deleteEquipment(id);
    return ResponseEntity.ok("Deleted");
  }

  @GetMapping("/search")
  public List<EquipmentDTO> searchEquipments(@RequestParam(name = "keyword",defaultValue = "") String keyword){
    return equipmentService.searchEquipment("%"+keyword+"%");
  }

}
