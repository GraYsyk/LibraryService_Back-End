package net.grasenko.com.Config.Controllers;

import jakarta.validation.Valid;

import net.grasenko.com.Config.Services.BookService;
import net.grasenko.com.Config.Services.PeopleService;
import net.grasenko.com.Config.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    private final BookService bookService;

    @Autowired
    public PeopleController(PeopleService peopleService, BookService bookService) {
        this.peopleService = peopleService;
        this.bookService = bookService;
    }


    //Get Mapping /////////////////////////////////////////////////////

    //Default Person list getter
    @GetMapping()
    public String index(@RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "10") int size,
                        @RequestParam(name = "sort", defaultValue = "false") boolean sort,
                        @RequestParam(name = "direction", defaultValue = "ASC") String direction,
                        @RequestParam(name = "search", required = false) String search,
                        Model model) throws Exception {
        Sort sortOrder = sort ? Sort.by(Sort.Direction.fromString(direction), "age") : Sort.unsorted();

        Pageable pageable = PageRequest.of(page, size, sortOrder);

        Page<Person> personPage;
        if (search != null && !search.isEmpty()) {
            personPage = peopleService.findByFirstName(search, pageable);
        } else {
            personPage = peopleService.findAll(pageable);
        }

        model.addAttribute("people", personPage);
        return "people/index";
    }

    //Person viewer/getter
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) throws SQLException {
        model.addAttribute("person", peopleService.findById(id));
        model.addAttribute("books", bookService.findByPerson_id(id));
        return "people/show";
    }

    //New person creator
    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());
        return "people/new";
    }

    //Person editor
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) throws SQLException {
        model.addAttribute("person", peopleService.findById(id));
        return "people/edit";
    }

    //Patch Mapping//////////////////////////////////////////////////////////

    //Patch mapping to free book
    @PatchMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") int id, @RequestParam("bookid") int bookId) {
        bookService.returnBook(id, bookId);

        return "redirect:/people/" + id;
    }

    //Update Mapping
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) throws SQLException {

        if (bindingResult.hasErrors()) {
            return "people/edit";
        }

        peopleService.update(id,person);
        return "redirect:/people";
    }

    //Post Mapping //////////////////////////////////////////////////////////////

    //Adding new Person
    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "people/new";
        }

        peopleService.save(person);
        return "redirect:/people";
    }

    //Delete Mapping ///////////////////////////////////////////////////////////////

    //Delete person by ID
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) throws SQLException {
        peopleService.delete(id);
        return "redirect:/people";
    }
}
