package mate.academy.bookshop.mapper;

import java.util.HashSet;
import java.util.Set;
import mate.academy.bookshop.config.MapperConfig;
import mate.academy.bookshop.dto.book.BookDto;
import mate.academy.bookshop.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.bookshop.dto.book.BookRequestDto;
import mate.academy.bookshop.model.Book;
import mate.academy.bookshop.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    @Mapping(target = "isbn", expression = "java(mate.academy.bookshop.util.IsbnFormatter"
            + ".format(bookDto.getIsbn()))")
    Book toModel(BookRequestDto bookDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        Set<Long> categoryIds = new HashSet<>();
        book.getCategories().stream().map(Category::getId).forEach(categoryIds::add);
        bookDto.setCategoryIds(categoryIds);
    }
}
