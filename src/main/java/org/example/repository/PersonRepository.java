package org.example.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Person;
import org.example.entity.Type;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PersonRepository {
    private static Logger logger = Logger.getLogger(String.valueOf(PersonRepository.class));

    private JAXBContext ctx;

    public PersonRepository() {
        try {
            this.ctx = JAXBContext.newInstance(Person.class);
        } catch (JAXBException e) {
            logger.info("problem with repo");
        }
    }

    private List<Person> load(Type type) {
        Unmarshaller unmarshaller;
        List<Person> db = new ArrayList<>();
        File dir = new File("src/main/resources/" + type.toString());

        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".xml")) {
                    try {
                        unmarshaller = ctx.createUnmarshaller();
                        Person person = (Person) unmarshaller.unmarshal(file);
                        db.add(person);
                    } catch (JAXBException e) {
                        logger.info("problem with load");
                    }
                }
            }
        }
        return db;

    }

    private void save(List<Person> people, Type type) {
        Marshaller marshaller;
        File dir = new File("src/main/resources/" + type.toString());
        for (Person person : people) {
            try {
                marshaller = ctx.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                File file = new File(dir, person.getPersonId() + ".xml");
                marshaller.marshal(person, file);
            } catch (JAXBException e) {
                logger.info("problem with save");
            }
        }
    }

    public void create(Type type, Person person) {
        List<Person> people = load(type);
        int id = 0;
        if (!people.isEmpty()) {
            id = Integer.parseInt(people.get(people.size() - 1).getPersonId());
        }
        person.setPersonId(String.valueOf(++id));
        if (isPeselExist(type, person.getPesel())) {
            throw new IllegalArgumentException("Person with this PESEL number already exists");
        }
        people.add(person);
        save(people, type);
    }

    public Person find(Type type, String firstName, String lastName, String mobile) {
        List<Person> people = load(type);
        for (Person person : people) {
            if (person.getFirstName().equals(firstName) &&
                    person.getLastName().equals(lastName) &&
                    person.getMobile().equals(mobile)) {
                return person;
            }
        }
        throw new IllegalArgumentException("Person does not exist");
    }

    public Person findById(Type type, String id) {
        List<Person> people = load(type);
        for (Person person : people) {
            if (person.getPersonId().equals(id)) {
                return person;
            }
        }
        throw new IllegalArgumentException("Person does not exist");
    }

    public boolean isPeselExist(Type type, String pesel) {
        boolean result = false;
        List<Person> people = load(type);
        for (Person person : people) {
            if (person.getPesel().equals(pesel)) {
                result = true;
            }
        }
        return result;
    }

    public void modify(Type type, Person person) {
        List<Person> people = load(type);
        if (isPeselExist(type, person.getPesel())) {
            throw new IllegalArgumentException("Person with this PESEL number already exists");
        }
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).getPersonId().equals(person.getPersonId())) {
                people.set(i, person);
                save(people, type);
            }
        }
    }

    public boolean remove(Type type, String id) {
        File dir = new File("src/main/resources/" + type.toString());
        List<Person> people = load(type);
        for (Person person : people) {
            if (person.getPersonId().equals(id)) {
                File file = new File(dir, person.getPersonId() + ".xml");
                if (file.exists()) {
                    people.remove(person);
                    file.delete();
                    save(people, type);
                    return true;
                }
            }
        }

        return false;
    }

    public List<Person> findAll(Type type){
        return load(type);
    }
}
