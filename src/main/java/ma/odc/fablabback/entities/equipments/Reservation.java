package ma.odc.fablabback.entities.equipments;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.odc.fablabback.entities.Users.Admin;
import ma.odc.fablabback.entities.Users.Member;
import ma.odc.fablabback.enums.EReservationState;
import ma.odc.fablabback.exceptions.UnAuthorizedReservationAction;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String reservationId;

  @Temporal(TemporalType.DATE)
  private LocalDate startDate;

  @Temporal(TemporalType.DATE)
  private LocalDate endDate;

  @ManyToOne private Member member;
  @ManyToOne private Admin admin;

  @OneToMany(mappedBy = "reservation")
  private List<EquipmentReservation> equipmentReservationList;
  

  @Enumerated(value = EnumType.STRING)
  private EReservationState reservationState = EReservationState.ONHOLD;

  public void confirmReservation() throws UnAuthorizedReservationAction {
    reservationState.setConfirmed(this);
  }

  public void cancelReservation() throws UnAuthorizedReservationAction {
    reservationState.setCanceled(this);
  }

  public void rejectReservation() throws UnAuthorizedReservationAction {
    reservationState.setRejected(this);
  }


  public void startReservation() throws UnAuthorizedReservationAction {
    reservationState.setInProgress(this);
  }

  public void endReservation() throws UnAuthorizedReservationAction {
    reservationState.setEnded(this);
  }
}
