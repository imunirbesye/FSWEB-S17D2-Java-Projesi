package com.workintech.java.spring.FSWEBS17D2JavaProjesi.model;

public class SeniorDeveloper extends Developer{
    public SeniorDeveloper(Integer id, String name, double salary) {
        super(id, name, salary, Experience.SENIOR);
    }
}
