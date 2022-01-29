package com.example.springboothibernate.Model;

import lombok.Data;

import javax.persistence.*;

@Data
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
}
