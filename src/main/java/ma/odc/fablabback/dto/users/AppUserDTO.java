package ma.odc.fablabback.dto.users;

import java.time.LocalDate;
import lombok.Data;

@Data
public class AppUserDTO {
  private long AppUsersId;
  private String name;
  private String email;
  private String cin;
  private LocalDate birthDate;
  private String sex;
  private String AppUsersname;
}
