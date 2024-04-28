package mate.academy.bookshop.specification.book;

import mate.academy.bookshop.dto.BookSearchParameters;
import mate.academy.bookshop.model.Book;
import org.springframework.data.jpa.domain.Specification;

public interface BookSpecificationProvider {

    Specification<Book> getSpecification(BookSearchParameters bookSearchParameters);

    String getColumnName();
}
