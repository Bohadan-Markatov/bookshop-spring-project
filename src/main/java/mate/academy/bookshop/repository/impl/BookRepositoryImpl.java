package mate.academy.bookshop.repository.impl;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import mate.academy.bookshop.model.Book;
import mate.academy.bookshop.repository.BookRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final SessionFactory sessionFactory;

    @Override
    public Book save(Book book) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
            return book;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can not save the book to DB" + book, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Book> bookQuery = session.createQuery("SELECT book FROM Book book "
                    + "WHERE book.id = :id", Book.class);
            bookQuery.setParameter("id", id);
            return bookQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new RuntimeException("Can not find book from DB by id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT book FROM Book book", Book.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can not get all books from DB", e);
        }
    }
}
