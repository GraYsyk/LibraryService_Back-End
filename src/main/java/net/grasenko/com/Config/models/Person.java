package net.grasenko.com.Config.models;

import jakarta.validation.constraints.*;

public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty!")
    @Size(min = 2, max = 20)
    private String name; //Person name

    @Max(value = 110, message = "Tf??")
    @Min(value = 1, message = "Minimal age is 1!")
    private int age; //Person age (Min is 1)

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
}
