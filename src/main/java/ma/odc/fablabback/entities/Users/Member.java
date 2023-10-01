package ma.odc.fablabback.entities.Users;

import jakarta.persistence.*;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.odc.fablabback.entities.Docs.Project;
import ma.odc.fablabback.entities.equipments.Reservation;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@SuperBuilder
@Table(name = "MEMBERS")
public class Member extends AppUser {

  private String status;

  private String etablissment;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
  private List<Reservation> reservations;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
  private Set<Project> projects;
}
