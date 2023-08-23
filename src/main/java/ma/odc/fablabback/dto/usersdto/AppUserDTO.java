package ma.odc.fablabback.dto.usersdto;

import java.time.LocalDate;
import lombok.Data;
import ma.odc.fablabback.enums.Role;
import ma.odc.fablabback.enums.Sex;

@Data
public class AppUserDTO {
  private long AppUsersId;
  private String name;
  private String email;
  private String cin;
  private LocalDate birthDate;
  private Sex sex;
  private String AppUsersname;
  private Role role;
}
