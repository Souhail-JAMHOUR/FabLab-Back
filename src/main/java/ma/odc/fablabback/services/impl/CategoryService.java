package ma.odc.fablabback.services.impl;

import lombok.RequiredArgsConstructor;
import ma.odc.fablabback.dto.equipmentsdto.CategoryDTO;
import ma.odc.fablabback.entities.equipments.Category;
import ma.odc.fablabback.exceptions.CatergoryNotFoundException;
import ma.odc.fablabback.mappers.EquipmentMapper;
import ma.odc.fablabback.repositories.equipments.CategoryRepository;
import ma.odc.fablabback.services.ICategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
  private final CategoryRepository categoryRepository;
  private final EquipmentMapper equipmentMapper;

  @Override
  public CategoryDTO getCategoryByName(String categoryId) throws CatergoryNotFoundException {
    Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CatergoryNotFoundException("Category not found"));
    return equipmentMapper.categoryToDto(category);
  }
}
