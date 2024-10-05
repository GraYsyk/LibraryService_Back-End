package net.grasenko.com.Config.Util;

import net.grasenko.com.Config.models.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookPMapper implements RowMapper<Book> {

    // BookMapper for another individual task

    @Override
    public Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setYear(resultSet.getInt("year"));
        book.setPersonname(resultSet.getString("person_id")); // Устанавливаем имя владельца
        return book;
    }
}
