package com.example.springboothibernate.Repository;

import com.example.springboothibernate.Model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {
    Author findByName(String name);
}
