package com.example.springboot.Service.book;

import com.example.springboot.Model.Book;
import com.example.springboot.exception.CustomException;

import java.util.List;

public interface BookService {
    List<Book> findAllBooks();

    Book addBook(Book book);

    Book getBookById(Long id) throws CustomException;

    Book editBook(Book book);

    void deleteById(Long id);
}
