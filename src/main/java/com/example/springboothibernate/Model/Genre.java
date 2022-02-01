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
@Table(name="genres")

public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    @Column(name = "g_id")
    protected Long id;

    @Column(name = "g_name")
    @Access(AccessType.PROPERTY)
    protected String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "genre", orphanRemoval = true) //или DETACH
    protected Set<Book> books = new HashSet<>();
}
