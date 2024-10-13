package net.grasenko.com.Config.Services;

import net.grasenko.com.Config.Repositories.PeopleRepository;
import net.grasenko.com.Config.models.Person;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {this.peopleRepository = peopleRepository;}

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Page findAll(Pageable pageable){
        return peopleRepository.findAll(pageable);
    }

    public Person findById(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    public Page<Person> findByFirstName(String firstName, Pageable pageable) {
        return peopleRepository.findByNameContaining(firstName, pageable);
    }

    @Transactional
    public Person save(Person person) {
        return peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }
}
