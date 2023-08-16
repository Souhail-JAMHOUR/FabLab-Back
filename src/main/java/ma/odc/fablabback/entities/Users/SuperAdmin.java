package ma.odc.fablabback.entities.Users;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@Data @NoArgsConstructor @AllArgsConstructor
public class SuperAdmin extends Admin {

    private String superAdminSpecific;

}
