package org.example.service;

import org.example.entity.Person;
import org.example.entity.Type;
import org.example.repository.PersonRepository;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

public class PersonService {
    private final PersonRepository personRepository = new PersonRepository();

    public PersonService() throws JAXBException {
    }


    public void create(String firstName, String lastName, String mobile, String email, String pesel, String type) {
        Person person = new Person()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setMobile(mobile)
                .setEmail(email)
                .setPesel(pesel);
        try {
            personRepository.create(Type.valueOf(type), person);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }


    public void find(String type, String firstName, String lastName, String mobile) {
        try {
            personRepository.find(Type.valueOf(type), firstName, lastName, mobile);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public void modify(String t, String id, String firstName, String lastName, String mobile, String email, String pesel) {
        Person person = null;
        try {
            person = personRepository.findById(Type.valueOf(t), id);
            person
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setMobile(mobile)
                    .setEmail(email)
                    .setPesel(pesel);

            personRepository.modify(Type.valueOf(t), person);

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(String type, String id) {
        boolean removed = false;
        try {
            removed = personRepository.remove(Type.valueOf(type), id);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        String msg = removed ? id + " person removed" : "person with " + id + " does not exist";
        System.out.println(msg);
    }

    public void findAll(String type) {
        List<Person> people = new ArrayList<>();
        try {
            people = personRepository.findAll(Type.valueOf(type));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        for (Person p : people) {
            System.out.println(p);
        }
    }
}
