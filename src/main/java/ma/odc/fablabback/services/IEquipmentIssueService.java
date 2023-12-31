package ma.odc.fablabback.services;

import java.util.List;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentIssueDTO;
import ma.odc.fablabback.dto.equipmentsdto.ReservationDTO;
import ma.odc.fablabback.exceptions.*;
import ma.odc.fablabback.requests.EquipmentIssueRequest;

public interface IEquipmentIssueService {
  EquipmentIssueDTO createIssue(EquipmentIssueRequest request)
      throws EquipmentReservationNotFoundException;

  EquipmentIssueDTO approveIssue(EquipmentIssueDTO issueDTO)
      throws EquipmentIssueNotFoundException,
          UnauthorizedEquipmentIssueActionException,
          EquipmentNotFoundException;

  EquipmentIssueDTO approveIssue(String id)
      throws EquipmentIssueNotFoundException,
          UnauthorizedEquipmentIssueActionException,
          EquipmentNotFoundException;

  EquipmentIssueDTO rejectIssue(EquipmentIssueDTO issueDTO)
      throws EquipmentIssueNotFoundException, UnauthorizedEquipmentIssueActionException;

  EquipmentIssueDTO rejectIssue(String id)
      throws EquipmentIssueNotFoundException, UnauthorizedEquipmentIssueActionException;

  EquipmentIssueDTO resolveIssue(EquipmentIssueDTO issueDTO)
      throws UnauthorizedEquipmentIssueActionException,
          EquipmentIssueNotFoundException,
          EquipmentNotFoundException;

  EquipmentIssueDTO resolveIssue(String id)
      throws EquipmentIssueNotFoundException,
          UnauthorizedEquipmentIssueActionException,
          EquipmentNotFoundException;

  List<EquipmentIssueDTO> getAllEquipmentIssues();

  List<EquipmentIssueDTO> getReservationIssues(ReservationDTO reservationDTO)
      throws ReservationNotFoundException;
}
