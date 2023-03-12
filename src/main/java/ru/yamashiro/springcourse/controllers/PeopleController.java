package ru.yamashiro.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yamashiro.springcourse.dao.PersonDAO;
import ru.yamashiro.springcourse.models.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        // Получим всех людей из DAO и передадим на отображение
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")  // в id можно поместить любое число и оно поместится в аргументы метода
    public String show(@PathVariable("id") int id, Model model) {
        // Получим человека по id
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person)  // тоже самое, что и ниже, но в данном случае создастся пустой объект, тк никаких параметров не будет получено и этот объект будет помещен в модель (что нам и и надо)
    {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") Person person)   //@ModelAttribute("person") создает объект, считывает данные из post запроса и помещает их в объект, потом кладет наш объект сразу в модель
    {
        personDAO.save(person);
        return "redirect:/people"; // переходим на страницу /people
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id)
    {
        model.addAttribute("person" , personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") Person person, @PathVariable("id")  int id)
    {
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id)
    {
        personDAO.delete(id);
        return "redirect:/people";
    }


}