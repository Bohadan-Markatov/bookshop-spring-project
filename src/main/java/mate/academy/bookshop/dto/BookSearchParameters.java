package mate.academy.bookshop.dto;

import java.math.BigDecimal;

public record BookSearchParameters(String[] authors, String[] titles,
                                   BigDecimal priceFrom, BigDecimal priceTo) {
}
