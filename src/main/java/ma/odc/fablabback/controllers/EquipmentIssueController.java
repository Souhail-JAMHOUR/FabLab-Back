package ma.odc.fablabback.controllers;

import lombok.RequiredArgsConstructor;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentIssueDTO;
import ma.odc.fablabback.exceptions.EquipmentIssueNotFoundException;
import ma.odc.fablabback.exceptions.EquipmentNotFoundException;
import ma.odc.fablabback.exceptions.UnauthorizedEquipmentIssueActionException;
import ma.odc.fablabback.requests.EquipmentIssueRequest;
import ma.odc.fablabback.services.impl.EquipmentIssueService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/failure")
@CrossOrigin("*")
public class EquipmentIssueController {
  private final EquipmentIssueService equipmentIssueService;

  @GetMapping("/all")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<Page<EquipmentIssueDTO>> getAllIssues(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "") int size) {
    Page<EquipmentIssueDTO> allEquipmentIssues = equipmentIssueService.getAllEquipmentIssues(page,size);
    return ResponseEntity.ok(allEquipmentIssues);
  }

  @PostMapping("/signal")
  @PreAuthorize("hasAuthority('SCOPE_MEMBER')")
  public ResponseEntity<EquipmentIssueDTO> signalFailure(@RequestBody EquipmentIssueRequest request) {
    EquipmentIssueDTO issue = equipmentIssueService.createIssue(request);
    return ResponseEntity.ok(issue);
  }

  @PutMapping("/approve/{id}")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<EquipmentIssueDTO> approveFailure(@PathVariable String id)
      throws UnauthorizedEquipmentIssueActionException,
          EquipmentIssueNotFoundException,
          EquipmentNotFoundException {
    EquipmentIssueDTO equipmentIssueDTO = equipmentIssueService.approveIssue(id);
    return ResponseEntity.ok(equipmentIssueDTO);
  }

  @PutMapping("/reject/{id}")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<EquipmentIssueDTO> rejectFailure(@PathVariable String id)
      throws UnauthorizedEquipmentIssueActionException, EquipmentIssueNotFoundException {
    EquipmentIssueDTO equipmentIssueDTO = equipmentIssueService.rejectIssue(id);
    return ResponseEntity.ok(equipmentIssueDTO);
  }

  @PutMapping("/resolve/{id}")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<EquipmentIssueDTO> resolveFailure(@PathVariable String id)
      throws UnauthorizedEquipmentIssueActionException,
          EquipmentIssueNotFoundException,
          EquipmentNotFoundException {
    EquipmentIssueDTO equipmentIssueDTO = equipmentIssueService.resolveIssue(id);
    return ResponseEntity.ok(equipmentIssueDTO);
  }
}
