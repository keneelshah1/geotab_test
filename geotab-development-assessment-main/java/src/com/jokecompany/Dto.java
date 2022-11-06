package com.jokecompany;

public class Dto {
    private String name , surname;

    public Dto(String name) {
    	String[] splitted = name.split("\\s+"); // splitting the first and last name
    	this.name = splitted[0];
        this.surname = splitted[1];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { //not used
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) { //not used
        this.surname = surname;
    }
}
