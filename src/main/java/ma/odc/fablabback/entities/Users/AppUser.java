package ma.odc.fablabback.entities.Users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.odc.fablabback.enums.ESex;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "APP_USERS")
@Inheritance(strategy = InheritanceType.JOINED)
public class AppUser {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long AppUsersId;

  private String appUsersname;
  private String name;
  private String email;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;
  private String cin;
  @JsonFormat(pattern="yyyy-MM-dd")
  private LocalDate birthDate;

  @Enumerated(EnumType.STRING)
  private ESex ESex;
}
