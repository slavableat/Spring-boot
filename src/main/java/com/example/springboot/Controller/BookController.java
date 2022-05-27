package com.example.springboot.Controller;

import com.example.springboot.Model.Book;
import com.example.springboot.Service.book.BookService;
import com.example.springboot.SpringbootHibernateApplication;
import com.example.springboot.exception.CustomException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
@RestController
@Validated
@RequestMapping("/books")
public class BookController {
    static Logger LOGGER;
    static{
        LOGGER.log(Level.INFO,"Start working BookController");
    }
    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public ResponseEntity findAllBooks() {
        LOGGER.log(Level.INFO,"BookControllers gets all books");
        List<Book> books = bookService.findAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity addBook(@Valid @RequestBody @NotNull Book book) {
        LOGGER.log(Level.INFO,"BookControllers add new book");
        bookService.addBook(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }


    @GetMapping("/edit/{id}")
    public ResponseEntity getById(@PathVariable @NotNull Long id) {
        try {
            Book book = bookService.getBookById(id);
            LOGGER.log(Level.INFO,"BookControllers get book by id");
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (CustomException e) {
            LOGGER.log(Level.WARN,"Book controller: ",e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity editBook(@Valid @RequestBody @NotNull Book book) {
        Book updatedBook = bookService.editBook(book);
        return new ResponseEntity(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBook(@PathVariable("id") @NotNull Long id) {
        LOGGER.log(Level.INFO,"BookControllers successfully deleted book with " + id + " id");
        bookService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        LOGGER.log(Level.INFO,"BookControllers get bad param");
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
