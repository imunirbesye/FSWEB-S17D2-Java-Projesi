package com.workintech.java.spring.FSWEBS17D2JavaProjesi.model;

public class MidDeveloper extends Developer{
    public MidDeveloper(Integer id, String name, double salary) {
        super(id, name, salary, Experience.MID);
    }
}
