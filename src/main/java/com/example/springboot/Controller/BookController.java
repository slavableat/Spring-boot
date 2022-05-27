package com.example.springboot.Controller;

import com.example.springboot.Model.Book;
import com.example.springboot.Service.book.BookService;
import com.example.springboot.exception.CustomException;

import org.apache.logging.log4j.Level;
import org.hibernate.exception.ConstraintViolationException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public ResponseEntity findAllBooks() {
        List<Book> books = bookService.findAllBooks();
        LOGGER.info("Successfully get all books");
        return new ResponseEntity<>(books, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity addBook(@Valid @RequestBody @NotNull Book book) {
        try {
            bookService.addBook(book);
            LOGGER.info("Successfully added new book");
            return new ResponseEntity(book, HttpStatus.CREATED);
        } catch (CustomException e) {
            LOGGER.info(e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }


    @GetMapping("/edit/{id}")
    public ResponseEntity getById(@PathVariable @NotNull Long id) {
        try {
            Book book = bookService.getBookById(id);
            LOGGER.info("Successfully get book by id");
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (CustomException e) {
            LOGGER.warn(e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity editBook(@Valid @RequestBody @NotNull Book book) {
        try {
            Book updatedBook = bookService.editBook(book);
            LOGGER.info("Successfully edited book");
            return new ResponseEntity(updatedBook, HttpStatus.OK);
        } catch (CustomException e) {
            LOGGER.warn(e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBook(@PathVariable("id") Long id) {
        try {
            bookService.deleteById(id);
            LOGGER.info("Successfully deleted book with " + id + " id");
            return new ResponseEntity(HttpStatus.OK);
        } catch (CustomException e) {
            LOGGER.warn(e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        LOGGER.info("Get bad param");
        return new ResponseEntity<>("Invalid param, " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
