package com.example.springboot.Controller;

import com.example.springboot.Model.Author;
import com.example.springboot.Model.Book;
import com.example.springboot.Model.Genre;
import com.example.springboot.Repository.AuthorRepository;
import com.example.springboot.Repository.BookRepository;
import com.example.springboot.Repository.GenreRepository;
import com.example.springboot.Service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
@RestController
public class BookController {

    @Autowired
    private MainService mainService;
    @GetMapping("/books")
    public ResponseEntity findAllBooks() {
        List<Book> books = mainService.findAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }


    @GetMapping("/genres")
    public ResponseEntity findAllGenres() {
        List<Genre> genres = mainService.findAllGenres();
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }


    @GetMapping("/authors")
    public ResponseEntity findAllAuthors() {
        List<Author> authors = mainService.findAllAuthors();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @PostMapping("/book-create")
    public ResponseEntity addBook(@RequestBody Book book) {
        mainService.addBook(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }


    @GetMapping("/book-edit/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        Book book = mainService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PutMapping("/book-edit/{id}")
    public ResponseEntity editBook(@RequestBody Book book) {
        Book updatedBook = mainService.editBook(book);
        return new ResponseEntity(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBook(@PathVariable("id") Long id) {
        mainService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    //FOR TEsts
    public void deleteDb() {
        List<Book> books =mainService.findAllBooks();
        for (Book book :
                books) {
            this.deleteBook(book.getId());
        }
    }
}
