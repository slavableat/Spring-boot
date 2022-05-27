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
@Table(name = "genres")

public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    @Column(name = "g_id")
    @NotNull
    protected Long id;

    @Column(name = "g_name")
    @Access(AccessType.PROPERTY)
    @NotBlank
    protected String name;

    @JsonIgnoreProperties("genre")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "genre", orphanRemoval = true)
    @NotEmpty
    protected Set<Book> books = new HashSet<>();

}
