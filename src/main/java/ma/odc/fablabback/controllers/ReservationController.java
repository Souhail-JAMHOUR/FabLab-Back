package ma.odc.fablabback.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import ma.odc.fablabback.dto.equipmentsdto.ReservationDTO;
import ma.odc.fablabback.exceptions.*;
import ma.odc.fablabback.requests.ReservationRequest;
import ma.odc.fablabback.services.impl.ReservationService;
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
      ReservationDTO reservation = reservationService.getReservationDto(id);
      return ResponseEntity.ok(reservation);
    } catch (ReservationNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @PostMapping("/approve/")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<ReservationDTO> approveReservation(
      @RequestBody ReservationDTO reservationDto) throws UnAuthorizedReservationAction, AppUsersNotFoundException, ReservationNotFoundException, UnsatisfiedRequirementException, EquipmentNotFoundException {
    ReservationDTO reservationDTO = reservationService.approveReservation(reservationDto);
    return ResponseEntity.ok(reservationDTO);
  }

  @PostMapping("/create")
  @PreAuthorize("hasAuthority('SCOPE_MEMBER')")
  public ResponseEntity<ReservationDTO> createReservation(
      @RequestBody ReservationRequest reservationRequest)
      throws UnsatisfiedRequirementException, EquipmentNotFoundException {
    ReservationDTO reservationDTO = reservationService.addNewReservation(reservationRequest);
    return ResponseEntity.ok(reservationDTO);
  }

  @PostMapping("/approve/{id}")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<ReservationDTO> approveReservation(@PathVariable String id) throws UnAuthorizedReservationAction, AppUsersNotFoundException, ReservationNotFoundException, UnsatisfiedRequirementException, EquipmentNotFoundException {
    ReservationDTO reservationDTO = reservationService.approveReservation(id);
    return ResponseEntity.ok(reservationDTO);
  }

  @PostMapping("/cancel/{id}")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable String id) {

    ReservationDTO reservationDTO = reservationService.cancelReservation(id);
    return ResponseEntity.ok(reservationDTO);
  }

  @PostMapping("/cancel/")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<ReservationDTO> cancelReservation(
      @RequestBody ReservationDTO reservationDTO) {

    reservationService.cancelReservation(reservationDTO);
    return ResponseEntity.ok(reservationDTO);
  }

  @PostMapping("/start/{id}")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<ReservationDTO> startReservation(@PathVariable String id) throws UnAuthorizedReservationAction, AppUsersNotFoundException, ReservationNotFoundException, UnsatisfiedRequirementException, EquipmentNotFoundException {
      ReservationDTO reservationDTO = reservationService.startReservation(id);
      return ResponseEntity.ok(reservationDTO);

  }
}
