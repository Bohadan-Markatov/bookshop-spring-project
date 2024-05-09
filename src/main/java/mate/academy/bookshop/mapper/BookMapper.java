package mate.academy.bookshop.mapper;

import mate.academy.bookshop.config.MapperConfig;
import mate.academy.bookshop.dto.book.BookDto;
import mate.academy.bookshop.dto.book.BookRequestDto;
import mate.academy.bookshop.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    @Mapping(target = "isbn", expression = "java(mate.academy.bookshop.util.IsbnFormatter"
            + ".format(bookDto.getIsbn()))")
    Book toModel(BookRequestDto bookDto);
}
