package ma.odc.fablabback.entities.Users;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "SUPER_ADMINS")
public class SuperAdmin extends Admin {

    private String superAdminSpecific;

}
