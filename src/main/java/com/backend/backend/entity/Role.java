package com.backend.backend.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Role_Table")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // e.g., ADMIN, MANAGER, SUPPORTER

//    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
//    private List<User> users;

    // Constructors
    public Role() {}
    public Role(String name) { this.name = name; }

    // Getters and Setters
    
    public Long getId() { return id; }   
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

//    public List<User> getUsers() { return users; }
//    public void setUsers(List<User> users) { this.users = users; }
}
