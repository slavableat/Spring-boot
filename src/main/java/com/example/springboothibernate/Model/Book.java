package com.example.springboothibernate.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="b_id")
    @Access(AccessType.PROPERTY)
    protected Long id;

    @Column(name="b_name")
    @Access(AccessType.PROPERTY)
    protected String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="g_id")
    @Access(AccessType.PROPERTY)
    protected Genre genre;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "books_authors",
            //foreign key for EmployeeEntity in employee_car table
            joinColumns = @JoinColumn(name = "b_id"),
            //foreign key for other side - EmployeeEntity in employee_car table
            inverseJoinColumns = @JoinColumn(name = "a_id")
    )
    @Access(AccessType.PROPERTY)
    protected Set<Author> authors = new HashSet<>();
}
