package mate.academy.bookshop.specification.book.impl;

import java.util.Arrays;
import mate.academy.bookshop.dto.BookSearchParameters;
import mate.academy.bookshop.model.Book;
import mate.academy.bookshop.specification.book.BookSpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecification implements BookSpecificationProvider {
    public static final String COLUMN_NAME = "author";

    @Override
    public Specification<Book> getSpecification(BookSearchParameters bookSearchParameters) {
        return (root, query, criteriaBuilder) -> root.get(COLUMN_NAME)
                .in(Arrays.stream(bookSearchParameters.authors()).toArray());
    }

    @Override
    public String getColumnName() {
        return COLUMN_NAME;
    }
}
