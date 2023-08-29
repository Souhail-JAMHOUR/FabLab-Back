package ma.odc.fablabback.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import ma.odc.fablabback.Exceptions.AppUsersNotFoundException;
import ma.odc.fablabback.Exceptions.ReservationNotFoundException;
import ma.odc.fablabback.dto.equipmentsdto.ReservationDTO;
import ma.odc.fablabback.services.IReservationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/reservation/")
@RequiredArgsConstructor
public class ReservationController {
  private final IReservationServiceImpl reservationService;

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

  @PatchMapping("/approve/{id}")
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
