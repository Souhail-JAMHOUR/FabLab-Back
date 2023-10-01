package ma.odc.fablabback.controllers;

import java.util.List;
import lombok.AllArgsConstructor;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentReservationDTO;
import ma.odc.fablabback.exceptions.EquipmentNotFoundException;
import ma.odc.fablabback.requests.EquipmentAvailabilityRequest;
import ma.odc.fablabback.services.impl.EquipmentReservationService;
import ma.odc.fablabback.services.impl.EquipmentService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/ereservations")
@AllArgsConstructor
@CrossOrigin("*")
public class EquipmentReservationController {
  private EquipmentReservationService equipmentReservationService;
  private EquipmentService equipmentService;

  @GetMapping("/all")
  public ResponseEntity<Page<EquipmentReservationDTO>> getAllEquipmentReservation(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "5") int size) {
    Page<EquipmentReservationDTO> allEquipmentReservations =
        equipmentReservationService.getAllEquipmentReservations(page, size);
    return ResponseEntity.ok(allEquipmentReservations);
  }

  @PostMapping("/of")
  public ResponseEntity<List<EquipmentReservationDTO>> getReservationByEquipment(
      //      @RequestParam(name = "page", defaultValue = "0") int page,
      //      @RequestParam(name = "size", defaultValue = "5") int size,
      @RequestBody EquipmentAvailabilityRequest request) throws EquipmentNotFoundException {
    equipmentService.getEquipment(request.getEquipmentId());
    List<EquipmentReservationDTO> equipmentReservationByEquipment =
        equipmentReservationService.getEquipmentReservationByEquipmentAndDatesAndState(request);
    return ResponseEntity.ok(equipmentReservationByEquipment);
  }

  @GetMapping("/{id}")
  public ResponseEntity<EquipmentReservationDTO> getEquipmentReservation(@PathVariable String id) {
    EquipmentReservationDTO equipmentReservation =
        equipmentReservationService.getEquipmentReservation(id);

    return ResponseEntity.ok(equipmentReservation);
  }
}
