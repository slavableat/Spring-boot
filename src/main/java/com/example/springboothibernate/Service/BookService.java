package com.example.springboothibernate.Service;

import com.example.springboothibernate.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookService extends JpaRepository<Book,int> {

}
