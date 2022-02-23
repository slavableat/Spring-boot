package com.example.springboot.Repository;

import com.example.springboot.Model.Author;
import com.example.springboot.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
}
