package mate.academy.bookshop.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import mate.academy.bookshop.service.BookService;
import mate.academy.bookshop.util.IsbnFormatter;

@RequiredArgsConstructor
public class IsbnValidator implements ConstraintValidator<Isbn, String> {
    private static final String ISBN_VALIDATION_PATTERN
            = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}"
            + "$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)"
            + "(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$";
    private final BookService bookService;
    private String uniqueMessage;

    @Override
    public void initialize(Isbn constraintAnnotation) {
        this.uniqueMessage = constraintAnnotation.uniqueMessage();
    }

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext constraintValidatorContext) {
        if (isbn == null || !Pattern.compile(ISBN_VALIDATION_PATTERN).matcher(isbn).matches()) {
            return false;
        }
        if (bookService.existByIsbn(IsbnFormatter.format(isbn))) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(uniqueMessage)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
