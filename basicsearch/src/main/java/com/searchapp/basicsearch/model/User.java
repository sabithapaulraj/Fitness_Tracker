package com.searchapp.basicsearch.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Data // Lombok will auto-generate getters, setters, etc.
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String designation;
    private double salary;
    private String dateOfJoining;
    private String address;
    private String gender;
    private int age;
    private String maritalStatus;

    @ElementCollection
    private List<String> interests;
}
