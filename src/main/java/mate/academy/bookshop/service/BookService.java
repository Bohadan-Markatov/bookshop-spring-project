package mate.academy.bookshop.service;

import java.util.List;
import mate.academy.bookshop.dto.BookDto;
import mate.academy.bookshop.dto.BookRequestDto;
import mate.academy.bookshop.dto.BookSearchParameters;
import org.springframework.data.domain.Pageable;

public interface BookService {

    BookDto save(BookRequestDto createBookRequestDto);

    BookDto update(Long id, BookRequestDto updateBookRequestDto);

    BookDto findById(Long id);

    void deleteById(Long id);

    List<BookDto> findAll(Pageable pageable);

    List<BookDto> search(Pageable pageable, BookSearchParameters bookSearchParameters);

    boolean existByIsbn(String isbn);

}
