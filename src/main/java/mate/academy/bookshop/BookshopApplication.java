package mate.academy.bookshop;

import java.math.BigDecimal;
import mate.academy.bookshop.model.Book;
import mate.academy.bookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookshopApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(BookshopApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book book = new Book();
                book.setAuthor("Bob");
                book.setTitle("Clean code");
                book.setIsbn("1234");
                book.setPrice(BigDecimal.valueOf(100L));

                bookService.save(book);

                System.out.println(bookService.findAll());
            }
        };
    }
}
