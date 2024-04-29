package mate.academy.bookshop.service;

import java.util.List;
import mate.academy.bookshop.dto.BookDto;
import mate.academy.bookshop.dto.CreateBookRequestDto;
import mate.academy.bookshop.dto.UpdateBookRequestDto;

public interface BookService {

    BookDto save(CreateBookRequestDto createBookRequestDto);

    BookDto update(Long id, UpdateBookRequestDto updateBookRequestDto);

    BookDto findById(Long id);

    void deleteById(Long id);

    List<BookDto> findAll();
}
