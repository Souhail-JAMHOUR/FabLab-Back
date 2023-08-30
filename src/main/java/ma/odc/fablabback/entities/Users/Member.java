package ma.odc.fablabback.entities.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.odc.fablabback.entities.Docs.Project;
import ma.odc.fablabback.entities.equipments.Reservation;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@SuperBuilder
@Table(name = "MEMBERS")
public class Member extends AppUser {

  @Column(nullable = false)
  private String status;

  @Column(nullable = false)
  private String etablissment;

  @OneToMany(mappedBy = "member")
  private List<Reservation> reservations;

  @OneToMany(mappedBy = "member")
  private Set<Project> projects;

  
}
