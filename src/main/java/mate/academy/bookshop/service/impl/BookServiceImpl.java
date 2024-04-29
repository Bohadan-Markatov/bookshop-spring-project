package mate.academy.bookshop.service.impl;

import java.util.List;
import lombok.AllArgsConstructor;
import mate.academy.bookshop.dto.BookDto;
import mate.academy.bookshop.dto.CreateBookRequestDto;
import mate.academy.bookshop.dto.UpdateBookRequestDto;
import mate.academy.bookshop.exception.EntityNotFoundException;
import mate.academy.bookshop.mapper.BookMapper;
import mate.academy.bookshop.model.Book;
import mate.academy.bookshop.repository.BookRepository;
import mate.academy.bookshop.service.BookService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto createBookRequestDto) {
        Book book = bookMapper.toModel(createBookRequestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDto update(Long id, UpdateBookRequestDto updateBookRequestDto) {
        Book createdBook = bookRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Can't find entity by id: " + id));
        if (updateBookRequestDto.getTitle() != null) {
            createdBook.setTitle(updateBookRequestDto.getTitle());
        }
        if (updateBookRequestDto.getAuthor() != null) {
            createdBook.setAuthor(updateBookRequestDto.getAuthor());
        }
        if (updateBookRequestDto.getIsbn() != null) {
            createdBook.setIsbn(updateBookRequestDto.getIsbn());
        }
        if (updateBookRequestDto.getPrice() != null) {
            createdBook.setPrice(updateBookRequestDto.getPrice());
        }
        if (updateBookRequestDto.getDescription() != null) {
            createdBook.setDescription(updateBookRequestDto.getDescription());
        }
        if (updateBookRequestDto.getCoverImage() != null) {
            createdBook.setCoverImage(updateBookRequestDto.getCoverImage());
        }
        return bookMapper.toDto(bookRepository.save(createdBook));
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
}
