package ma.odc.fablabback.requests;

import lombok.Data;

@Data
public class NewEquipmentRequest {
  private String name;
  private int quantity;
  private String category;
  private String imageUrl;
}
