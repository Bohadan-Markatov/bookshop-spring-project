package mate.academy.bookshop.specification.book;

import lombok.RequiredArgsConstructor;
import mate.academy.bookshop.dto.BookSearchParameters;
import mate.academy.bookshop.model.Book;
import mate.academy.bookshop.specification.book.impl.AuthorSpecification;
import mate.academy.bookshop.specification.book.impl.PriceSpecification;
import mate.academy.bookshop.specification.book.impl.TitleSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilderImpl implements BookSpecificationBuilder {
    private final BookSpecificationManager bookSpecificationManager;

    @Override
    public Specification<Book> build(BookSearchParameters bookSearchParameters) {
        Specification<Book> specification = Specification.where(null);
        if (bookSearchParameters.authors() != null && bookSearchParameters.authors().length > 0) {
            specification = specification.and(bookSpecificationManager
                    .getProvider(AuthorSpecification.COLUMN_NAME)
                    .getSpecification(bookSearchParameters));
        }
        if (bookSearchParameters.title() != null) {
            specification = specification.and(bookSpecificationManager
                    .getProvider(TitleSpecification.COLUMN_NAME)
                    .getSpecification(bookSearchParameters));
        }
        if (bookSearchParameters.priceTo() != null || bookSearchParameters.priceFrom() != null) {
            specification = specification.and(bookSpecificationManager
                    .getProvider(PriceSpecification.COLUMN_NAME)
                    .getSpecification(bookSearchParameters));
        }
        return specification;
    }
}
