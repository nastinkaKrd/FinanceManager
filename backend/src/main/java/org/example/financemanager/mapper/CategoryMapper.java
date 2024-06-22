package org.example.financemanager.mapper;

import org.example.financemanager.domain.Category;
import org.example.financemanager.dto.CategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends BaseMapper<Category, CategoryDto> {
}
