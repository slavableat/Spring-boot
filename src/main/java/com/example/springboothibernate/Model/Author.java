package com.example.springboothibernate.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name ="authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    @Column(name = "a_id")
    protected Long id;

    @Column(name = "a_name")
    @Access(AccessType.PROPERTY)
    protected String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "books_authors",
            //foreign key for EmployeeEntity in employee_car table
            joinColumns = @JoinColumn(name = "a_id"),
            //foreign key for other side - EmployeeEntity in employee_car table
            inverseJoinColumns = @JoinColumn(name = "b_id")
    )
    @Access(AccessType.PROPERTY)
    protected Set<Book> books = new HashSet<>();
}