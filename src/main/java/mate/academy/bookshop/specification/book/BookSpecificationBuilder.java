package mate.academy.bookshop.specification.book;

import mate.academy.bookshop.dto.book.BookSearchParameters;
import mate.academy.bookshop.model.Book;
import org.springframework.data.jpa.domain.Specification;

public interface BookSpecificationBuilder {
    Specification<Book> build(BookSearchParameters bookSearchParameters);
}
