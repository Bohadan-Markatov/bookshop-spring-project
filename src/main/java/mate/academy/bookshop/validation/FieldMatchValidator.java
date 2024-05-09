package mate.academy.bookshop.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Arrays;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstField;
    private String secondField;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        firstField = constraintAnnotation.firstField();
        secondField = constraintAnnotation.secondField();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        Object firstFieldValue = getFieldValueByName(object, firstField);
        Object secondFieldValue = getFieldValueByName(object, secondField);

        return firstFieldValue != null && firstFieldValue.equals(secondFieldValue);
    }

    private Object getFieldValueByName(Object object, String fieldName) {
        Field[] fields = object.getClass().getDeclaredFields();
        Field field = Arrays.stream(fields)
                .filter(f -> f.getName().equals(fieldName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Field with this name not found"));
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Can't get field value");
        }
    }
}
