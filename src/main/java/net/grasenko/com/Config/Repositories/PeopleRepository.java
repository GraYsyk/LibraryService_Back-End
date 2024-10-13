package net.grasenko.com.Config.Repositories;

import net.grasenko.com.Config.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Page<Person> findAll(Pageable pageable);

    Page<Person> findByNameContaining(String firstName, Pageable pageable);

}
