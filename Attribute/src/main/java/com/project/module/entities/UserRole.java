package com.project.module.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "roles")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    private String name;
}
