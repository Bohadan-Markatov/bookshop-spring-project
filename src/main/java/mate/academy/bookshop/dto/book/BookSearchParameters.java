package mate.academy.bookshop.dto.book;

import java.math.BigDecimal;

public record BookSearchParameters(String[] authors, String title,
                                   BigDecimal priceFrom, BigDecimal priceTo) {
}
