package com.example.springboot.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "b_id")
    @Access(AccessType.PROPERTY)
    @NotNull
    protected Long id;

    @Column(name = "b_name")
    @Access(AccessType.PROPERTY)
    @NotBlank
    protected String name;

    @JsonIgnoreProperties("books")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "g_id")
    @Access(AccessType.PROPERTY)
    protected Genre genre;

    @JsonIgnoreProperties("books")
    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    @Access(AccessType.PROPERTY)
    @NotEmpty
    protected Set<Author> authors = new HashSet<>();
}
