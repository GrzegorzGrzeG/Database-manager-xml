package org.example.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@Data
@XmlRootElement(name = "Person")
@XmlAccessorType(XmlAccessType.FIELD)
@Accessors(chain = true)
public class Person {
    private String personId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String pesel;

}
