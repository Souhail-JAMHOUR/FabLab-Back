package ma.odc.fablabback.entities.Users;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ma.odc.fablabback.entities.Docs.Project;
import ma.odc.fablabback.entities.equipments.Reservation;
import java.util.List;

@Entity
@Data
@NoArgsConstructor @ToString @SuperBuilder
public class Admin extends User {

    private String poste;

    @OneToMany(mappedBy = "admin")
    private List<Reservation> approvedReservations;



    @OneToMany(mappedBy = "Approver")
    private List<Project> approvedProjects;
}
