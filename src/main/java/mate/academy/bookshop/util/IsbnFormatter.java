package mate.academy.bookshop.util;

public class IsbnFormatter {
    private static final int STANDARD_BOOK_NUMBER_LENGTH = 9;
    private static final int CHECK_NUMBER_LENGTH = 1;

    public static String format(String isbn) {
        char[] c = isbn.toCharArray();

        StringBuilder builder = new StringBuilder();
        for (int i = c.length - 1 - CHECK_NUMBER_LENGTH; i >= 0; i--) {
            if (Character.isDigit(c[i])) {
                builder.append(c[i]);
                if (builder.length() == STANDARD_BOOK_NUMBER_LENGTH) {
                    break;
                }
            }
        }
        return builder.reverse().toString();
    }
}
