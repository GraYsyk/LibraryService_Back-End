package net.grasenko.com.Config.DAO;

import net.grasenko.com.Config.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    private final ResourceUrlProvider mvcResourceUrlProvider;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate, ResourceUrlProvider mvcResourceUrlProvider) {
        this.jdbcTemplate = jdbcTemplate;
        this.mvcResourceUrlProvider = mvcResourceUrlProvider;
    }

    /////////////////////////////////////////////////////////////////////////////

    public List<Person> show() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getPerson(int id) {
        return jdbcTemplate.query
                ("SELECT * FROM Person WHERE id = ?", new Object[] { id }, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    /////////////////////////////////////////////////////////////////////////////

    public void savePerson(Person person) {
        jdbcTemplate.update("INSERT INTO Person (name, age) VALUES (?, ?)",
                person.getName(), person.getAge());
    }

    public void update(int id, Person updatePerson) {
        jdbcTemplate.update("UPDATE Person SET name=?, age=? WHERE id=?",
                updatePerson.getName(), updatePerson.getAge(), id);
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    /////////////////////////////////////////////////////////////////////////////
}
