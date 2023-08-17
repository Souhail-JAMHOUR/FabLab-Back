package ma.odc.fablabback.entities.Users;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.odc.fablabback.entities.Docs.Project;
import ma.odc.fablabback.entities.equipments.Reservation;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Member extends AppUser {

  private String status;

  private String etablissment;

  @OneToMany(mappedBy = "member")
  private List<Reservation> reservations;

  @OneToMany(mappedBy = "member")
  private Set<Project> projects;
}
