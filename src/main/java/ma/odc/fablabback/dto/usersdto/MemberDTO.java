package ma.odc.fablabback.dto.usersdto;

import lombok.Data;

@Data
public class MemberDTO extends AppUserDTO{

  private String status;

  private String etablissment;
}
