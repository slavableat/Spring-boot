package com.example.springboothibernate.Repository;

import com.example.springboothibernate.Model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}
