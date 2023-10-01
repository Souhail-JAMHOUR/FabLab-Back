package ma.odc.fablabback.requests;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class UserRegisterRequest {
  private String username;
  private String name;
  private String email;
  private String password;
  private String confirmedPassword;
//  private String cin;
//
//  @JsonFormat(pattern = "yyyy-MM-dd")
//  private LocalDate birthDate;
//
//  @JsonEnumDefaultValue private Sex sex;
//  private String status;
//
//  private String etablissment;
}
