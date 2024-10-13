package net.grasenko.com.Config.Repositories;

import net.grasenko.com.Config.models.Book;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Page<Book> findByTitleContaining(String title, Pageable pageable);

    Page<Book> findAll(Pageable pageable);

    List<Book> findByPerson_id(int person_id);

    @Modifying
    @Query("UPDATE Book b SET b.person.id = :personId, b.taken_at = :taken_at WHERE b.id = :id")
    void updateOwner(@Param("id") int id, @Param("personId") int personId, @Param("taken_at") Date takenAt);

    @Modifying
    @Query("UPDATE Book SET person = null, taken_at = null WHERE id = :bookId AND person.id = :personId")
    void deleteBookById(@Param("bookId") int bookId, @Param("personId") int personId);
}
