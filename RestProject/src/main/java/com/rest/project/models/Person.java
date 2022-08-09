package com.rest.project.models;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Person {
    private String name;
    private String books;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBooks() {
        return books;
    }

    public void setBooks(String books) {
        this.books = books;
    }
}
