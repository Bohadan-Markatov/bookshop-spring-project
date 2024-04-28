package mate.academy.bookshop.specification.book;

import lombok.RequiredArgsConstructor;
import mate.academy.bookshop.dto.BookSearchParameters;
import mate.academy.bookshop.model.Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilderImpl implements BookSpecificationBuilder {
    private static final String AUTHOR_COLUMN_NAME = "author";
    private static final String TITLE_COLUMN_NAME = "title";
    private static final String PRICE_COLUMN_NAME = "price";
    private final BookSpecificationManager bookSpecificationManager;

    @Override
    public Specification<Book> build(BookSearchParameters bookSearchParameters) {
        Specification<Book> specification = Specification.where(null);
        if (bookSearchParameters.authors() != null && bookSearchParameters.authors().length > 0) {
            specification = specification.and(bookSpecificationManager
                    .getProvider(AUTHOR_COLUMN_NAME)
                    .getSpecification(bookSearchParameters));
        }
        if (bookSearchParameters.titles() != null && bookSearchParameters.titles().length > 0) {
            specification = specification.and(bookSpecificationManager
                    .getProvider(TITLE_COLUMN_NAME)
                    .getSpecification(bookSearchParameters));
        }
        if (bookSearchParameters.priceTo() != null || bookSearchParameters.priceFrom() != null) {
            specification = specification.and(bookSpecificationManager
                    .getProvider(PRICE_COLUMN_NAME)
                    .getSpecification(bookSearchParameters));
        }
        return specification;
    }
}
