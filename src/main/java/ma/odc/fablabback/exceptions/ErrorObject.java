package ma.odc.fablabback.exceptions;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorObject {
  private Integer statusCode;
  private String message;
  private Date timestamp;
}
