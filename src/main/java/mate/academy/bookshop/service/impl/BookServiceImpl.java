package mate.academy.bookshop.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.bookshop.dto.book.BookDto;
import mate.academy.bookshop.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.bookshop.dto.book.BookRequestDto;
import mate.academy.bookshop.dto.book.BookSearchParameters;
import mate.academy.bookshop.exception.EntityNotFoundException;
import mate.academy.bookshop.exception.NotUniqueValueException;
import mate.academy.bookshop.mapper.BookMapper;
import mate.academy.bookshop.model.Book;
import mate.academy.bookshop.repository.BookRepository;
import mate.academy.bookshop.service.BookService;
import mate.academy.bookshop.service.CategoryService;
import mate.academy.bookshop.specification.book.BookSpecificationBuilder;
import mate.academy.bookshop.util.IsbnFormatter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;
    private final CategoryService categoryService;

    @Override
    public BookDto save(BookRequestDto createBookRequestDto) {
        if (existsByIsbn(IsbnFormatter.format(createBookRequestDto.getIsbn()))) {
            throw new NotUniqueValueException("ISBN must be unique");
        }
        Book book = bookMapper.toModel(createBookRequestDto);
        if (doesCategoryExist(createBookRequestDto)) {
            book.setCategories(categoryService
                    .getCategories(createBookRequestDto.getCategoriesId()));
        }
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDto update(Long id, BookRequestDto updateBookRequestDto) {
        BookDto existingBook = findById(id);
        String oldIsbn = existingBook.getIsbn();
        String newIsbn = IsbnFormatter.format(updateBookRequestDto.getIsbn());
        if (existsByIsbn(newIsbn) && !oldIsbn.equals(newIsbn)) {
            throw new NotUniqueValueException("ISBN must be unique");
        }
        Book updatedBook = bookMapper.toModel(updateBookRequestDto);
        if (doesCategoryExist(updateBookRequestDto)) {
            updatedBook.setCategories(categoryService
                    .getCategories(updateBookRequestDto.getCategoriesId()));
        }
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
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDto> search(Pageable pageable, BookSearchParameters bookSearchParameters) {
        Specification<Book> bookSpecification
                = bookSpecificationBuilder.build(bookSearchParameters);
        return bookRepository.findAll(bookSpecification).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findAllByCategoryIds(Pageable pageable, Long id) {
        categoryService.validateCategory(id);
        return bookRepository.findAllByCategoryId(pageable, id).stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }

    private boolean doesCategoryExist(BookRequestDto dto) {
        return dto.getCategoriesId() != null && !dto.getCategoriesId().isEmpty();
    }
}
