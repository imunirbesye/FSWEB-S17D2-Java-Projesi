package com.workintech.java.spring.FSWEBS17D2JavaProjesi.validation;

import com.workintech.java.spring.FSWEBS17D2JavaProjesi.model.Developer;

import java.util.Map;

public class DeveloperValidation {
    public static boolean isDeveloperExist(Map<Integer, Developer> developers, int id) {
        return developers.containsKey(id);
    }
}
