package mate.academy.bookshop.service.impl;

import java.util.List;
import lombok.AllArgsConstructor;
import mate.academy.bookshop.dto.BookDto;
import mate.academy.bookshop.dto.BookRequestDto;
import mate.academy.bookshop.dto.BookSearchParameters;
import mate.academy.bookshop.exception.EntityNotFoundException;
import mate.academy.bookshop.exception.NotUniqueValueException;
import mate.academy.bookshop.mapper.BookMapper;
import mate.academy.bookshop.model.Book;
import mate.academy.bookshop.repository.BookRepository;
import mate.academy.bookshop.service.BookService;
import mate.academy.bookshop.specification.book.BookSpecificationBuilder;
import mate.academy.bookshop.util.IsbnFormatter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(BookRequestDto createBookRequestDto) {
        if (existByIsbn(IsbnFormatter.format(createBookRequestDto.getIsbn()))) {
            throw new NotUniqueValueException("ISBN must be unique");
        }
        Book book = bookMapper.toModel(createBookRequestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDto update(Long id, BookRequestDto updateBookRequestDto) {
        BookDto existingBook = findById(id);
        String oldIsbn = existingBook.getIsbn();
        String newIsbn = IsbnFormatter.format(updateBookRequestDto.getIsbn());
        if (existByIsbn(newIsbn) && !oldIsbn.equals(newIsbn)) {
            throw new NotUniqueValueException("ISBN must be unique");
        }
        Book updatedBook = bookMapper.toModel(updateBookRequestDto);
        updatedBook.setId(id);
        return bookMapper.toDto(bookRepository.save(updatedBook));
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Can't find entity by id: " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDto> search(BookSearchParameters bookSearchParameters) {
        Specification<Book> bookSpecification
                = bookSpecificationBuilder.build(bookSearchParameters);
        return bookRepository.findAll(bookSpecification).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public boolean existByIsbn(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }
}
