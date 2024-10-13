package net.grasenko.com.Config.Services;

import net.grasenko.com.Config.Repositories.BookRepository;
import net.grasenko.com.Config.Repositories.PeopleRepository;
import net.grasenko.com.Config.models.Book;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }

    public Page<Book> findByTitle(String title, Pageable pageable) {
        return bookRepository.findByTitleContaining(title, pageable);
    }

    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Book findById(int id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
    }

    @Transactional
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Book Book = findById(id);
        int personId = Book.getPerson().getId();
        updatedBook.setId(id);
        updatedBook.setPerson(Book.getPerson());
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    //////////////////////////////////////////////////////////////
    //Special Requests Part
    //////////////////////////////////////////////////////////////

    public List<Book> findByPerson_id(int person_id) {
        return bookRepository.findByPerson_id(person_id);
    }

    @Transactional
    public void returnBook(int book_id,int person_id) {
        bookRepository.deleteBookById(book_id, person_id);
    }

    @Transactional
    public void updateOwner(int id, int person_id) {
        Book book = bookRepository.findById(id).get();
        book.setTaken_at(new Date());
        bookRepository.updateOwner(book.getId(), person_id, book.getTaken_at());
    }

    public int overdueCheck(Book book) {
        if (book.getTaken_at() != null) {
            long currentTime = new Date().getTime();
            long takenAtTime = book.getTaken_at().getTime();
            long diffInMillies = Math.abs(currentTime - takenAtTime);
            long diffInDays = diffInMillies / (24 * 60 * 60 * 1000);
            if (diffInDays > 10) {
                return (int) (diffInDays - 10);
            }
        }
        return 0;
    }

}
