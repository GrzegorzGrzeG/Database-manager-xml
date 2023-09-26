package org.example;

import org.example.entity.Person;
import org.example.service.PersonService;

import javax.xml.bind.JAXBException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PersonService personService = null;
        try {
            personService = new PersonService();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        Person person = null;


        boolean exit = false;
        while (!exit) {
            System.out.println("1. Create Person");
            System.out.println("2. Find person by type, firstname, lastname, phone number");
            System.out.println("3. Update person");
            System.out.println("4. Delete");
            System.out.println("5. List of all persons");
            System.out.println("type x to exit");
            String input = scanner.nextLine();
            switch (input.toLowerCase()) {
                case "1":
                    System.out.print("first name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("last name: ");
                    String lastName = scanner.nextLine();
                    System.out.print("mobile: ");
                    String mobile = scanner.nextLine();
                    System.out.print("email: ");
                    String email = scanner.nextLine();
                    System.out.print("pesel: ");
                    String pesel = scanner.nextLine();
                    System.out.print("Internal or External: ");
                    String type = scanner.nextLine();
                    personService.create(firstName, lastName, mobile, email, pesel, type.toUpperCase());
                    break;
                case "2":
                    System.out.print("firstname: ");
                    firstName = scanner.nextLine();
                    System.out.print("lastname: ");
                    lastName = scanner.nextLine();
                    System.out.print("mobile: ");
                    mobile = scanner.nextLine();
                    System.out.print("Internal or External: ");
                    type = scanner.nextLine();
                    personService.find(type.toUpperCase(), firstName, lastName, mobile);
                    break;
                case "3":
                    System.out.print("Internal or External: ");
                    type = scanner.nextLine();
                    System.out.println("id: ");
                    String id = scanner.nextLine();
                    System.out.print("firstname: ");
                    firstName = scanner.nextLine();
                    System.out.print("lastname: ");
                    lastName = scanner.nextLine();
                    System.out.print("mobile: ");
                    mobile = scanner.nextLine();
                    System.out.print("email: ");
                    email = scanner.nextLine();
                    System.out.print("pesel: ");
                    pesel = scanner.nextLine();
                    personService.modify(type.toUpperCase(), id, firstName, lastName, mobile, email, pesel);
                    break;
                case "4":
                    System.out.print("Internal or External: ");
                    type = scanner.nextLine();
                    System.out.println("id: ");
                    id = scanner.nextLine();
                    personService.remove(type.toUpperCase(), id);
                    break;
                case "5":
                    System.out.print("Internal or External: ");
                    type = scanner.nextLine();
                    personService.findAll(type.toUpperCase());
                    break;
                case "x":
                    System.out.println("EXIT");
                    exit = true;
                    break;
                default:
                    System.out.println("error");
                    break;

            }
        }


    }
}
