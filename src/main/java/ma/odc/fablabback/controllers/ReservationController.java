package ma.odc.fablabback.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import ma.odc.fablabback.dto.equipmentsdto.ReservationDTO;
import ma.odc.fablabback.dto.usersdto.MemberDTO;
import ma.odc.fablabback.exceptions.*;
import ma.odc.fablabback.requests.ReservationRequest;
import ma.odc.fablabback.services.impl.MemberService;
import ma.odc.fablabback.services.impl.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/reservation/")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReservationController {
  private final ReservationService reservationService;
  private final MemberService memberService;

  @GetMapping("/all")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<List<ReservationDTO>> getAllReservation() {
    List<ReservationDTO> allReservations = reservationService.getAllReservations();
    return ResponseEntity.ok(allReservations);
  }

  @GetMapping("/myreservation")
  @PreAuthorize("hasAuthority('SCOPE_MEMBER')")
  public ResponseEntity<List<ReservationDTO>> getMyReservation() throws AppUsersNotFoundException {
    MemberDTO connectedMember = memberService.getConnectedMember();
    List<ReservationDTO> allReservations =
        reservationService.getMemberReservations(connectedMember);
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

  @PutMapping("/create")
  @PreAuthorize("hasAuthority('SCOPE_MEMBER')")
  public ResponseEntity<ReservationDTO> createReservation(
      @RequestBody ReservationRequest reservationRequest)
      throws UnsatisfiedRequirementException,
          EquipmentNotFoundException,
          AppUsersNotFoundException {
    ReservationDTO reservationDTO = reservationService.addNewReservation(reservationRequest);
    return ResponseEntity.ok(reservationDTO);
  }

  @PutMapping("/approve/{id}")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<ReservationDTO> approveReservation(@PathVariable String id)
      throws UnAuthorizedReservationAction,
          AppUsersNotFoundException,
          ReservationNotFoundException,
          UnsatisfiedRequirementException,
          EquipmentNotFoundException {
    ReservationDTO reservationDTO = reservationService.approveReservation(id);
    return ResponseEntity.ok(reservationDTO);
  }

  @PutMapping("/cancel/{id}")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable String id)
      throws UnAuthorizedReservationAction,
          AppUsersNotFoundException,
          ReservationNotFoundException {

    ReservationDTO reservationDTO = reservationService.cancelReservation(id);
    return ResponseEntity.ok(reservationDTO);
  }

  @PutMapping("/start/{id}")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<ReservationDTO> startReservation(@PathVariable String id)
      throws UnAuthorizedReservationAction,
          AppUsersNotFoundException,
          ReservationNotFoundException {
    ReservationDTO reservationDTO = reservationService.startReservation(id);
    return ResponseEntity.ok(reservationDTO);
  }

  @PutMapping("/end/{id}")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<ReservationDTO> endReservation(@PathVariable String id)
      throws UnAuthorizedReservationAction,
          AppUsersNotFoundException,
          ReservationNotFoundException {
    ReservationDTO reservationDTO = reservationService.endReservation(id);
    return ResponseEntity.ok(reservationDTO);
  }

  @PutMapping("/reject/{id}")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<ReservationDTO> rejectReservation(@PathVariable String id)
      throws UnAuthorizedReservationAction,
          AppUsersNotFoundException,
          ReservationNotFoundException {
    ReservationDTO reservationDTO = reservationService.rejectReservation(id);
    return ResponseEntity.ok(reservationDTO);
  }
}
