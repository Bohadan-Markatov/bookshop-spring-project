package mate.academy.bookshop.specification.book;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationManagerImpl implements BookSpecificationManager {
    private final List<BookSpecificationProvider> bookSpecificationProviders;
    private Map<String, BookSpecificationProvider> bookSpecificationProviderMap;

    @PostConstruct
    private void initProvidersMap() {
        bookSpecificationProviderMap = new HashMap<>();
        for (BookSpecificationProvider provider : bookSpecificationProviders) {
            bookSpecificationProviderMap.put(provider.getColumnName(), provider);
        }
    }

    @Override
    public BookSpecificationProvider getProvider(String key) {
        return Optional.ofNullable(bookSpecificationProviderMap.get(key)).orElseThrow(()
                -> new RuntimeException("Can't find specification for this key: " + key));
    }
}
