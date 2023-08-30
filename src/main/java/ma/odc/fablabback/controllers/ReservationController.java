package ma.odc.fablabback.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import ma.odc.fablabback.dto.equipmentsdto.ReservationDTO;
import ma.odc.fablabback.exceptions.AppUsersNotFoundException;
import ma.odc.fablabback.exceptions.ReservationNotFoundException;
import ma.odc.fablabback.services.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/reservation/")
@RequiredArgsConstructor
public class ReservationController {
  private final ReservationService reservationService;

  @GetMapping("/all")
  public ResponseEntity<List<ReservationDTO>> getAllReservation() {
    List<ReservationDTO> allReservations = reservationService.getAllReservations();
    return ResponseEntity.ok(allReservations);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ReservationDTO> getReservation(@PathVariable String id) {
    try {
      ReservationDTO reservation = reservationService.getReservation(id);
      return ResponseEntity.ok(reservation);
    } catch (ReservationNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @PostMapping("/approve/{id}")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<String> approveReservation(@PathVariable String id) {
    try {
      reservationService.approveReservation(id);
      return ResponseEntity.ok("OK");
    } catch (ReservationNotFoundException e) {
      throw new RuntimeException(e);
    } catch (AppUsersNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
