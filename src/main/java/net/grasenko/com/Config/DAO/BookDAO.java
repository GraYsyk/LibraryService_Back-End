package net.grasenko.com.Config.DAO;

import net.grasenko.com.Config.Util.BookMapper;
import net.grasenko.com.Config.Util.BookPMapper;
import net.grasenko.com.Config.models.Book;
import net.grasenko.com.Config.models.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    ///////////////////////////////////////////////////////////////

    public List<Book> show() {
        return jdbcTemplate.query("SELECT * FROM book", new BookPMapper());
    }

    public Book getBookById(int id) {
        return jdbcTemplate.queryForObject("SELECT b.*, p.name AS personName FROM Book b LEFT JOIN Person p ON b.person_id = p.id WHERE b.id = ?",
                new Object[]{id}, new BookMapper());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    public void updateOwner(int id, Person person) {
        jdbcTemplate.update("UPDATE book SET person_id = ? WHERE id = ?", person.getId(), id);
    }

    public void updateBook(int id, Book book) {
        jdbcTemplate.update("UPDATE book SET title = ?, author = ?, year = ? WHERE id = ?", book.getTitle(), book.getAuthor(), book.getYear(), id);
    }

    public void newBook(Book book) {
        jdbcTemplate.update("INSERT INTO book (title, author, year) VALUES (?, ?, ?)", book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

    ///////////////////////////////////////////////////////////////////

    public List<Book> getPersonBooks(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id = ?", new Object[]{id}, new BookPMapper());
    }

    public void returnBook(int personId, int bookId) {
        jdbcTemplate.update("UPDATE book SET person_id = ? WHERE id = ?", null, bookId);
    }
}
