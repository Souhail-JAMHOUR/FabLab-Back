package ma.odc.fablabback.controllers;

import java.util.List;
import lombok.AllArgsConstructor;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentReservationDTO;
import ma.odc.fablabback.exceptions.EquipmentNotFoundException;
import ma.odc.fablabback.requests.EquipmentAvailabilityRequest;
import ma.odc.fablabback.services.impl.EquipmentReservationService;
import ma.odc.fablabback.services.impl.EquipmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/ereservations")
@AllArgsConstructor
public class EquipmentReservationController {
  private EquipmentReservationService equipmentReservationService;
  private EquipmentService equipmentService;

  @GetMapping("/all")
  public ResponseEntity<List<EquipmentReservationDTO>> getAllEquipmentReservation() {
    List<EquipmentReservationDTO> allEquipmentReservations =
        equipmentReservationService.getAllEquipmentReservations();
    return ResponseEntity.ok(allEquipmentReservations);
  }

  @PostMapping("/of")
  public ResponseEntity<List<EquipmentReservationDTO>> getReservationByEquipment(
      @RequestBody EquipmentAvailabilityRequest request) throws EquipmentNotFoundException {
      equipmentService.getEquipment(request.getEquipmentId());
    List<EquipmentReservationDTO> equipmentReservationByEquipment =
        equipmentReservationService.getEquipmentReservationByEquipmentAndDatesAndState(request);
    return ResponseEntity.ok(equipmentReservationByEquipment);
  }
}
