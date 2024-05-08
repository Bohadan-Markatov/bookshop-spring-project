package mate.academy.bookshop.util;

import java.util.regex.Pattern;

public class IsbnFormatter {
    private static final String ISBN10_VALIDATION_PATTERN =
            "^(?:ISBN(?:-10)?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$)"
                    + "[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$";
    private static final String ISBN13_VALIDATION_PATTERN =
            "^(?:ISBN(?:-13)?:? )?(?=[0-9]{13}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)"
                    + "97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]$";
    private static final int ISBN10_CODE_LENGTH = 10;
    private static final int ISBN13_CODE_LENGTH = 13;

    public static String format(String isbn) {
        int isbnCondeLength = getIsbnCodeLength(isbn);

        char[] c = isbn.toCharArray();

        StringBuilder builder = new StringBuilder();
        for (int i = c.length - 1; i >= 0; i--) {
            if (Character.isDigit(c[i])) {
                builder.append(c[i]);
                if (builder.length() == isbnCondeLength) {
                    break;
                }
            }
        }
        return builder.reverse().toString();
    }

    private static int getIsbnCodeLength(String isbn) {
        if (isbn != null) {
            if (Pattern.compile(ISBN10_VALIDATION_PATTERN)
                    .matcher(isbn).matches()) {
                return ISBN10_CODE_LENGTH;
            }
            if (Pattern.compile(ISBN13_VALIDATION_PATTERN)
                    .matcher(isbn).matches()) {
                return ISBN13_CODE_LENGTH;
            }
        }
        throw new IllegalArgumentException("Invalid ISBN format");
    }
}
