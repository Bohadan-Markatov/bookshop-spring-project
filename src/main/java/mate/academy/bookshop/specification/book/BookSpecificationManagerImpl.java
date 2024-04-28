package mate.academy.bookshop.specification.book;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationManagerImpl implements BookSpecificationManager {
    private final List<BookSpecificationProvider> bookSpecificationProviders;

    @Override
    public BookSpecificationProvider getProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(spec -> spec.getColumnName().equals(key))
                .findFirst()
                .orElseThrow(()
                        -> new RuntimeException("Can't find specification for this key: " + key));
    }
}
