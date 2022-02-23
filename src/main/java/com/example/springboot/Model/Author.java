package com.example.springboot.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.HashSet;
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

    @JsonIgnoreProperties("authors")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "books_authors",
            joinColumns = @JoinColumn(name = "a_id"),
            inverseJoinColumns = @JoinColumn(name = "b_id")
    )
    @Access(AccessType.PROPERTY)
    protected Set<Book> books = new HashSet<>();
}