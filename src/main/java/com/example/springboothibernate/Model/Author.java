package com.example.springboothibernate.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name ="authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    @Column(name = "a_id")
    protected int id;
    @Column(name = "a_name")
    @Access(AccessType.PROPERTY)
    protected String name;
    @ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER)
    @Access(AccessType.PROPERTY)
    protected List<Book> books = new ArrayList<>();
}