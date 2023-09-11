package ma.odc.fablabback.enums;

import ma.odc.fablabback.entities.equipments.EquipmentIssue;
import ma.odc.fablabback.exceptions.UnauthorizedEquipmentIssueActionException;

public enum EquipmentIssueState {
  ONHOLD,
  REJECTED,
  APPROVED,
  RESOLVED;

  public void setApproved(EquipmentIssue issue) throws UnauthorizedEquipmentIssueActionException {
    switch (this) {
      case ONHOLD -> issue.setEquipmentIssueState(APPROVED);
      case REJECTED -> throw new UnauthorizedEquipmentIssueActionException(
          "Cannot confirm a rejected Issue");
      case APPROVED -> throw new UnauthorizedEquipmentIssueActionException(
          "Issue already approved");
      case RESOLVED -> throw new UnauthorizedEquipmentIssueActionException(
          "Issue has been already resolved");
      default -> throw new UnauthorizedEquipmentIssueActionException("Action cannot be performed");
    }
  }

  public void setRejected(EquipmentIssue issue) throws UnauthorizedEquipmentIssueActionException {
    switch (this) {
      case ONHOLD -> issue.setEquipmentIssueState(REJECTED);
      case REJECTED -> throw new UnauthorizedEquipmentIssueActionException(
          "Issue already rejected");
      case APPROVED -> throw new UnauthorizedEquipmentIssueActionException(
          "cannot reject an approved issue try to resolve it instead");
      case RESOLVED -> throw new UnauthorizedEquipmentIssueActionException(
          "Issue has been already resolved");
      default -> throw new UnauthorizedEquipmentIssueActionException("Action cannot be performed");
    }
  }

  public void setResolved(EquipmentIssue issue) throws UnauthorizedEquipmentIssueActionException {
    switch (this) {
      case ONHOLD -> throw new UnauthorizedEquipmentIssueActionException(
          "Issue need to be approved first");
      case REJECTED -> throw new UnauthorizedEquipmentIssueActionException(
          "Cannot resolved a rejected Issue");
      case APPROVED -> issue.setEquipmentIssueState(RESOLVED);
      case RESOLVED -> throw new UnauthorizedEquipmentIssueActionException(
          "Issue has been already resolved");
      default -> throw new UnauthorizedEquipmentIssueActionException("Action cannot be performed");
    }
  }
}
