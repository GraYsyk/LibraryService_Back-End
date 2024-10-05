package net.grasenko.com.Config.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class Book {

    private int id;// book ID

    @NotEmpty(message = "Book Title should not be empty")
    private String title;    // book name

    @NotEmpty(message = "If book does not have an author, please write 'Unknown Author' ")
    private String author;      // Autor - can be zero if Unknown

    @Min(value = 1800, message = "We dont have books old like that..")
    private int year;           // Year

    private Integer person_id; // Owner Id (Can be null)

    private String personname;   // Owner Id (Can be null)

    /////////////////////////////////////////////////////////////////////////////

    public Book() {
    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Book(int person_id, int year, String author, String title) {
        this.person_id = person_id;
        this.year = year;
        this.author = author;
        this.title = title;
    }

    public Book(String title, String author, int year, String personname) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.personname = personname;
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

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getPersonname() {
        return personname;
    }

    public void setPersonname(String person) {
        this.personname = person;
    }
}
