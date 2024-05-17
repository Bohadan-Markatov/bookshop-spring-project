package mate.academy.bookshop.repository;

import java.util.List;
import mate.academy.bookshop.model.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    boolean existsByIsbn(String isbn);

    @Query("SELECT b FROM Book b JOIN b.categories c "
            + "WHERE c.id = :categoryId AND b.isDeleted = false")
    List<Book> findAllByCategoryId(Pageable pageable, @Param("categoryId") Long categoryId);
}
