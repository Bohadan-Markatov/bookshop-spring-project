package mate.academy.bookshop.service;

import java.util.List;
import mate.academy.bookshop.dto.BookDto;
import mate.academy.bookshop.dto.BookRequestDto;
import mate.academy.bookshop.dto.BookSearchParameters;

public interface BookService {

    BookDto save(BookRequestDto createBookRequestDto);

    BookDto update(Long id, BookRequestDto updateBookRequestDto);

    BookDto findById(Long id);

    void deleteById(Long id);

    List<BookDto> findAll();

    List<BookDto> search(BookSearchParameters bookSearchParameters);
}
