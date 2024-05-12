package mate.academy.bookshop.service;

import java.util.List;
import mate.academy.bookshop.dto.book.BookDto;
import mate.academy.bookshop.dto.book.BookRequestDto;
import mate.academy.bookshop.dto.book.BookSearchParameters;
import org.springframework.data.domain.Pageable;

public interface BookService {

    BookDto save(BookRequestDto createBookRequestDto);

    BookDto update(Long id, BookRequestDto updateBookRequestDto);

    BookDto findById(Long id);

    void deleteById(Long id);

    List<BookDto> findAll(Pageable pageable);

    List<BookDto> search(Pageable pageable, BookSearchParameters bookSearchParameters);

    boolean existsByIsbn(String isbn);

}
