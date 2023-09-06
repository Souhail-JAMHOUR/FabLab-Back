package ma.odc.fablabback.services;

import ma.odc.fablabback.dto.equipmentsdto.CategoryDTO;
import ma.odc.fablabback.exceptions.CatergoryNotFoundException;

public interface ICategoryService {
  CategoryDTO getCategoryByName(String categoryId)throws CatergoryNotFoundException;
}
