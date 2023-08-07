package ma.odc.fablabback.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public abstract class User {
    @Id
    private long id;
    private String name;
    private String email;
    private String password;
}
