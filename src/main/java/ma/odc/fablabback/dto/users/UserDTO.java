package ma.odc.fablabback.dto.users;
import java.time.LocalDate;
import lombok.Data;

@Data
public class UserDTO {
    private long userId;
    private String name;
    private String email;
    private String cin;
    private LocalDate birthDate;
    private String sex;
    private String username;

}
