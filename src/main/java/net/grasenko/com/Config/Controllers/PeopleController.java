package net.grasenko.com.Config.Controllers;

import jakarta.validation.Valid;
import net.grasenko.com.Config.DAO.BookDAO;
import net.grasenko.com.Config.DAO.PersonDAO;
import net.grasenko.com.Config.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final BookDAO bookDAO;
    private PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
    }

    //Get Mapping /////////////////////////////////////////////////////

    //Default Person list getter
    @GetMapping()
    public String index(Model model) throws Exception {
        model.addAttribute("people", personDAO.show());
        return "people/index";
    }

    //Person viewer/getter
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) throws SQLException {
        model.addAttribute("person", personDAO.getPerson(id));
        model.addAttribute("books", personDAO.getPersonBooks(id));
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
        model.addAttribute("person", personDAO.getPerson(id));
        return "people/edit";
    }

    //Patch Mapping//////////////////////////////////////////////////////////

    //Patch mapping to free book
    @PatchMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") int id, @RequestParam("bookid") int bookId) {
        bookDAO.returnBook(id, bookId);

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

        personDAO.update(id,person);
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

        personDAO.savePerson(person);
        return "redirect:/people";
    }

    //Delete Mapping ///////////////////////////////////////////////////////////////

    //Delete person by ID
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) throws SQLException {
        personDAO.deletePerson(id);
        return "redirect:/people";
    }
}
