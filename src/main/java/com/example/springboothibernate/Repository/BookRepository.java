package com.example.springboothibernate.Repository;

import com.example.springboothibernate.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Book getById(long id);
}
