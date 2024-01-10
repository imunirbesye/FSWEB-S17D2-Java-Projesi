package com.workintech.java.spring.FSWEBS17D2JavaProjesi.model;

public class JuniorDeveloper extends Developer{
    public JuniorDeveloper(Integer id, String name, double salary) {
        super(id, name, salary, Experience.JUNIOR);
    }
}
