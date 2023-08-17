package ma.odc.fablabback.security.models;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.odc.fablabback.enums.Sex;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
  private String username;
  private String name;
  private String email;
  private String password;
  private String confirmedPassword;
  private String cin;
  private LocalDate birthDate;
  private Sex sex;
}
