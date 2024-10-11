package net.grasenko.com.Config.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Name should not be empty!")
    @Size(min = 2, max = 20)
    @Column(name = "name")
    private String name; //Person name

    @Max(value = 110, message = "Tf??")
    @Min(value = 1, message = "Minimal age is 1!")
    @Column(name = "age")
    private int age; //Person age (Min is 1)

    @OneToMany(mappedBy = "person")
    private List<Book> books;

    /////////////////////////////////////////////////////////////////////////////

    public Person() {
    }

    public Person(int id, int age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    /////////////////////////////////////////////////////////////////////////////

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {return books;}

    public void setBooks(List<Book> books) {this.books = books;}
}
