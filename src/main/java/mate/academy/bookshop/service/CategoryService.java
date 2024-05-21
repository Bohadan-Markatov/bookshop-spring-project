package mate.academy.bookshop.service;

import java.util.List;
import java.util.Set;
import mate.academy.bookshop.dto.category.CategoryDto;
import mate.academy.bookshop.dto.category.CategoryRequestDto;
import mate.academy.bookshop.model.Category;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto save(CategoryRequestDto categoryDto);

    CategoryDto update(Long id, CategoryRequestDto categoryDto);

    void deleteById(Long id);

    void validateCategory(Long id);

    public Set<Category> getCategories(Set<Long> categoriesIds);
}
