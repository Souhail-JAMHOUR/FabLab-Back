package ma.odc.fablabback.entities.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ma.odc.fablabback.entities.Docs.Project;
import ma.odc.fablabback.entities.equipments.Reservation;

@Entity
@Data
@NoArgsConstructor
@ToString
@SuperBuilder
@Table(name = "ADMINS")
public class Admin extends AppUser {

  private String poste;

  @OneToMany(cascade = CascadeType.ALL,mappedBy = "admin")
  @JsonIgnore
  private List<Reservation> approvedReservations;

  @OneToMany(cascade = CascadeType.ALL,mappedBy = "admin")
  @JsonIgnore
  private List<Project> approvedProjects;
}
