package net.grasenko.com.Config.Controllers;

import jakarta.validation.Valid;
import net.grasenko.com.Config.DAO.BookDAO;
import net.grasenko.com.Config.DAO.PersonDAO;
import net.grasenko.com.Config.models.Book;
import net.grasenko.com.Config.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

/**
 * Autor - GraYs9650
 */


@Controller
@Component
@RequestMapping("/book")
public class BooksController {

    private final BookDAO bookDAO;

    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    ////////////////////////////////////////////////////////////////////////////////

    //Get mapper for default Book list
    @GetMapping()
    public String book(Model model) {
        model.addAttribute("book", bookDAO.show());
        return "book/books";
    }

    //Geting specific book by id
    @GetMapping("/{id}")
    public String book(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
     model.addAttribute("book", bookDAO.getBookById(id));
     model.addAttribute("people", personDAO.show());
     return "book/show";
    }

    //New book creator mapping
    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "book/new";
    }

    //Book editor mapping
    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.getBookById(id));
        return "book/edit";
    }

    ////////////////////////////////////////////////////////////////////////////////

    //Update book mapper
    @PatchMapping("/{id}/edit")
    public String editBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "book/edit";
        }
        bookDAO.updateBook(id, book);
        return "redirect:/book/" + id;
    }

    //Update book owner mapper
    @PatchMapping("/{id}")
    public String updateBookOwner(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        bookDAO.updateOwner(id, person);
        return "redirect:/book/" + id;
    }

    //////////////////////////////////////////////////////////////////////////////////

    //Adding new Book
    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "book/new";
        }

        bookDAO.newBook(book);

        return "redirect:/book";
    }

    ////////////////////////////////////////////////////////////////////////////////////

    //Deleting book from DB
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) throws SQLException {
        bookDAO.delete(id);
        return "redirect:/book";
    }
}
