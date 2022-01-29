package com.example.springboothibernate.Repository;

import com.example.springboothibernate.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
