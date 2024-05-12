package mate.academy.bookshop.specification.book.impl;

import mate.academy.bookshop.dto.book.BookSearchParameters;
import mate.academy.bookshop.model.Book;
import mate.academy.bookshop.specification.book.BookSpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecification implements BookSpecificationProvider {
    public static final String COLUMN_NAME = "title";

    @Override
    public Specification<Book> getSpecification(BookSearchParameters bookSearchParameters) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get(COLUMN_NAME),
                "%" + bookSearchParameters.title() + "%");
    }

    @Override
    public String getColumnName() {
        return COLUMN_NAME;
    }
}
