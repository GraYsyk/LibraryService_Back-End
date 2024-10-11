package net.grasenko.com.Config.DAO;

import net.grasenko.com.Config.models.Book;
import net.grasenko.com.Config.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private final SessionFactory sessionFactory;
    private final ResourceUrlProvider mvcResourceUrlProvider;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory, ResourceUrlProvider mvcResourceUrlProvider) {
        this.sessionFactory = sessionFactory;
        this.mvcResourceUrlProvider = mvcResourceUrlProvider;
    }

    //Read Methods
    /////////////////////////////////////////////////////////////////////////////

    @Transactional(readOnly = true)
    public List<Person> show() {
        Session session = sessionFactory.getCurrentSession();

        List<Person> people = session.createQuery("from Person", Person.class).getResultList();

        return people;
    }

    @Transactional(readOnly = true)
    public Person getPerson(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        return person;
    }

    //CUD Methods
    /////////////////////////////////////////////////////////////////////////////

    @Transactional()
    public void savePerson(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(person);
    }

    @Transactional()
    public void update(int id, Person updatePerson) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        person.setName(updatePerson.getName());
        person.setAge(updatePerson.getAge());
        session.update(person);
    }

    @Transactional()
    public void deletePerson(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        session.delete(person);
    }

    //Side Methods
    /////////////////////////////////////////////////////////////////////////////

    @Transactional(readOnly = true)
    public List<Book> getPersonBooks(int id) {
        Session session = sessionFactory.getCurrentSession();
        List<Book> personBooks = session.createQuery("from Book where person.id = :id", Book.class).setParameter("id", id).getResultList();
        return personBooks;
    }
}
