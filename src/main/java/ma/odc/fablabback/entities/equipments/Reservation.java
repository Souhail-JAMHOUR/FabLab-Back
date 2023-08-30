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
  
}
