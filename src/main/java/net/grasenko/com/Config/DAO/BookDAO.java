package net.grasenko.com.Config.DAO;

import net.grasenko.com.Config.models.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class BookDAO {

    private final SessionFactory sessionFactory;

    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    //Read Methods
    ///////////////////////////////////////////////////////////////

    @Transactional(readOnly = true)
    public List<Book> show() {
        Session session = sessionFactory.getCurrentSession();
        List<Book> books = session.createQuery("from Book").getResultList();
        return books;
    }

    @Transactional(readOnly = true)
    public Book getBookById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        return book;
    }

    //CUD Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    @Transactional()
    public void updateBook(int id, Book UpdateBook) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setAuthor(UpdateBook.getAuthor());
        book.setTitle(UpdateBook.getTitle());
        book.setYear(UpdateBook.getYear());
        session.update(book);
    }

    @Transactional()
    public void newBook(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Transactional()
    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        session.delete(book);
    }

    //Side Methods
    ///////////////////////////////////////////////////////////////////

    @Transactional()
    public void updateOwner(int id, int PersonId) {
        Session session = sessionFactory.getCurrentSession();

        String hql = "UPDATE Book SET person.id = :personId WHERE id = :id";

        session.createQuery(hql)
                .setParameter("personId", PersonId)
                .setParameter("id", id)
                .executeUpdate();

    }

    @Transactional
    public void returnBook(int personId, int bookId) {
        Session session = sessionFactory.getCurrentSession();

        String hql = "UPDATE Book SET person = null WHERE id = :bookId AND person.id = :personId";

        session.createQuery(hql)
                .setParameter("bookId", bookId)
                .setParameter("personId", personId)
                .executeUpdate();
    }

}
