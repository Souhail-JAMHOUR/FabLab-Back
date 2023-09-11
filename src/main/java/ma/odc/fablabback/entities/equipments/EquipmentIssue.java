package ma.odc.fablabback.entities.equipments;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.odc.fablabback.enums.EquipmentIssueState;
import ma.odc.fablabback.exceptions.UnauthorizedEquipmentIssueActionException;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentIssue {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String issueId;

  @ManyToOne
  @JoinColumn(name = "EQUIPMENT_RESERVATION")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private EquipmentReservation equipmentReservation;

  @Enumerated(value = EnumType.STRING)
  private EquipmentIssueState equipmentIssueState;

  private String description;
  private LocalDate reportedAt;

  public void approveIssue() throws UnauthorizedEquipmentIssueActionException {
    equipmentIssueState.setApproved(this);
  }

  public void rejectIssue() throws UnauthorizedEquipmentIssueActionException {
    equipmentIssueState.setRejected(this);
  }

  public void resolveIssue() throws UnauthorizedEquipmentIssueActionException {
    equipmentIssueState.setResolved(this);
  }
}
