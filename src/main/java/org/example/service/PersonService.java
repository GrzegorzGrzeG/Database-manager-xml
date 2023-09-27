package org.example.service;

import org.example.entity.Person;
import org.example.entity.Type;
import org.example.repository.PersonRepository;

import java.util.List;
import java.util.logging.Logger;

public class PersonService {
    private static final Logger logger = Logger.getLogger(String.valueOf(PersonService.class));
    private final PersonRepository personRepository = new PersonRepository();

    public void create(String firstName, String lastName, String mobile, String email, String pesel, String type) {
        Person person = new Person()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setMobile(mobile)
                .setEmail(email)
                .setPesel(pesel);
        personRepository.create(Type.valueOf(type), person);
    }


    public void find(String type, String firstName, String lastName, String mobile) {
        personRepository.find(Type.valueOf(type), firstName, lastName, mobile);
    }

    public void modify(String t, String id, String firstName, String lastName, String mobile, String email, String pesel) {
        Person person = personRepository.findById(Type.valueOf(t), id);
        person
                .setFirstName(firstName)
                .setLastName(lastName)
                .setMobile(mobile)
                .setEmail(email)
                .setPesel(pesel);

        personRepository.modify(Type.valueOf(t), person);

    }

    public void remove(String type, String id) {
        boolean removed = personRepository.remove(Type.valueOf(type), id);
        String msg = removed ? "person " + id + " removed" : "person with " + id + " does not exist";
        logger.info(msg);
    }

    public void findAll(String type) {
        List<Person> people = personRepository.findAll(Type.valueOf(type));

        for (Person p : people) {
            System.out.println(p);
        }
    }
}
