package net.grasenko.com.Config.Controllers;

import jakarta.validation.Valid;
import net.grasenko.com.Config.Services.BookService;
import net.grasenko.com.Config.Services.PeopleService;
import net.grasenko.com.Config.models.Book;
import net.grasenko.com.Config.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private final BookService bookService;

    private final PeopleService peopleService;

    @Autowired
    public BooksController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    ////////////////////////////////////////////////////////////////////////////////

    //Get mapper for default Book list
    @GetMapping()
    public String book(@RequestParam(name = "page", defaultValue = "0") int page,
                       @RequestParam(name = "size", defaultValue = "10") int size,
                       @RequestParam(name = "sort", defaultValue = "false") boolean sort,
                       @RequestParam(name = "direction", defaultValue = "ASC") String direction,
                       @RequestParam(name = "search", required = false) String search,
                       Model model) {
        Sort sortOrder = sort ? Sort.by(Sort.Direction.fromString(direction), "year") : Sort.unsorted();

        Pageable pageable = PageRequest.of(page, size, sortOrder);

        Page<Book> booksPage;
        if (search != null && !search.isEmpty()) {
            booksPage = bookService.findByTitle(search, pageable);
        } else {
            booksPage = bookService.findAll(pageable);
        }

        // Добавляем результат в модель
        model.addAttribute("books", booksPage);
        return "book/books";
    }

    //Geting specific book by id
    @GetMapping("/{id}")
    public String book(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        Book book = bookService.findById(id);
        int overdue = bookService.overdueCheck(book);
        model.addAttribute("overdue", overdue); // Добавляем значение просрочки в модель

        model.addAttribute("book", book);
        model.addAttribute("people", peopleService.findAll());
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
        model.addAttribute("book", bookService.findById(id));
        return "book/edit";
    }

    ////////////////////////////////////////////////////////////////////////////////

    //Update book mapper
    @PatchMapping("/{id}/edit")
    public String editBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "book/edit";
        }
        bookService.update(id, book);
        return "redirect:/book/" + id;
    }

    //Update book owner mapper
    @PatchMapping("/{id}")
    public String updateBookOwner(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        bookService.updateOwner(id, person.getId());
        return "redirect:/book/" + id;
    }

    //////////////////////////////////////////////////////////////////////////////////

    //Adding new Book
    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "book/new";
        }

        bookService.save(book);

        return "redirect:/book";
    }

    ////////////////////////////////////////////////////////////////////////////////////

    //Deleting book from DB
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) throws SQLException {
        bookService.delete(id);
        return "redirect:/book";
    }
}
