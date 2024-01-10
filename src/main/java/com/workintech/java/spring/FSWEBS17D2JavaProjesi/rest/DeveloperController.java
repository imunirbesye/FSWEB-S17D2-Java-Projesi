package com.workintech.java.spring.FSWEBS17D2JavaProjesi.rest;

import com.workintech.java.spring.FSWEBS17D2JavaProjesi.dto.DeveloperResponse;
import com.workintech.java.spring.FSWEBS17D2JavaProjesi.model.*;
import com.workintech.java.spring.FSWEBS17D2JavaProjesi.tax.Taxable;
import com.workintech.java.spring.FSWEBS17D2JavaProjesi.validation.DeveloperValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/developers")
public class DeveloperController {

    private Map<Integer, Developer> developers;

    private final Taxable taxable;

    @Autowired
    public DeveloperController(Taxable taxable) {
        this.taxable = taxable;
    }

    @PostConstruct
    public void init(){
        this.developers = new HashMap<>();
    }

    @GetMapping
    public List<Developer> getAll(){
        return this.developers.values().stream().toList();
    }

    @GetMapping("/{id}")
    public DeveloperResponse get(@PathVariable Integer id){
        if(!DeveloperValidation.isDeveloperExist(developers, id)) {
            return new DeveloperResponse(null,id+" değeri ile herhangi bir developer kaydı bulunamadı", HttpStatus.NOT_FOUND.value());
        }
        return new DeveloperResponse(this.developers.get(id),"Kayıt bulundu ve başarılı", HttpStatus.OK.value());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Developer save(@RequestBody Developer developer){
        Developer newDeveloper = getDeveloper(developer);
        if (newDeveloper == null) return null;

        this.developers.put(newDeveloper.getId(), newDeveloper);
        return newDeveloper;
    }

    @PutMapping("/{id}")
    public DeveloperResponse update(@PathVariable Integer id, @RequestBody Developer developer) {
        if(!DeveloperValidation.isDeveloperExist(this.developers, id)) {
            return new DeveloperResponse(developer, id + " gönderilen id ile ilgili bir kayıt bulunamadı!", HttpStatus.NOT_FOUND.value());
        }

        developer.setId(id);

        Developer newDeveloper = getDeveloper(developer);
        if (newDeveloper == null) return null;

        this.developers.put(newDeveloper.getId(), newDeveloper);
        return new DeveloperResponse(newDeveloper, "güncelleme işlemi başrılı!", HttpStatus.OK.value());
    }

    private Developer getDeveloper(Developer developer) {
        Developer newDeveloper = null;
        if(developer.getExperience().equals(Experience.JUNIOR)){
            newDeveloper = new JuniorDeveloper(developer.getId(), developer.getName(), developer.getSalary() - developer.getSalary() * taxable.getSimpleTaxRate());
        } else if(developer.getExperience().equals(Experience.MID)) {
            newDeveloper = new MidDeveloper(developer.getId(), developer.getName(), developer.getSalary() - developer.getSalary() * taxable.getMiddleTaxRate());
        } else if(developer.getExperience().equals(Experience.SENIOR)) {
            newDeveloper = new SeniorDeveloper(developer.getId(), developer.getName(), developer.getSalary() - developer.getSalary() * taxable.getUpperTaxRate());
        } else {
            return null;
        }
        return newDeveloper;
    }

    @DeleteMapping("/{id}")
    public DeveloperResponse delete(@PathVariable Integer id){
        DeveloperResponse developerResponse = get(id);
        developers.remove(id);
        return new DeveloperResponse(developerResponse.getDeveloper(), "silme işlemi başarılı", HttpStatus.OK.value());
    }
}
