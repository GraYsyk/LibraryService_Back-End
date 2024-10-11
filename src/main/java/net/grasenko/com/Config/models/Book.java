package net.grasenko.com.Config.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;// book ID

    @NotEmpty(message = "Book Title should not be empty")
    @Column(name = "title")
    private String title;    // book name

    @NotEmpty(message = "If book does not have an author, please write 'Unknown Author' ")
    @Column(name = "author")
    private String author;      // Autor - can be zero if Unknown

    @Min(value = 1800, message = "We dont have books old like that..")
    @Column(name = "year")
    private int year;           // Year

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person; // Owner Id (Can be null)

    /////////////////////////////////////////////////////////////////////////////

    public Book() {
    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Book(Person person, int year, String author, String title) {
        this.person = person;
        this.year = year;
        this.author = author;
        this.title = title;
    }

    //////////////////////////////////////////////////////////////////////////////

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getPerson_id() {
        return person;
    }

    public void setPerson_id(Person person) {
        this.person = person;
    }
}
