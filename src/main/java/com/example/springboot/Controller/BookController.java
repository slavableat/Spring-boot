package com.example.springboot.Controller;

import com.example.springboot.Model.Author;
import com.example.springboot.Model.Book;
import com.example.springboot.Model.Genre;
import com.example.springboot.Repository.AuthorRepository;
import com.example.springboot.Repository.BookRepository;
import com.example.springboot.Repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@CrossOrigin(origins = {"http://localhost:3000","http://localhost:4200"})
@RestController
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("/books")
    public ResponseEntity findAllBooks() {
        List<Book> books =bookRepository.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    @GetMapping("/genres")
    public ResponseEntity findAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }
    @GetMapping("/authors")
    public ResponseEntity findAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }
    @PostMapping("/book-create")
    public ResponseEntity addBook(@RequestBody Book book) {
        Genre findGenre = genreRepository.findByName(book.getGenre().getName());
        if (findGenre != null) book.setGenre(findGenre);
        book.getGenre().getBooks().add(book);
        genreRepository.save(book.getGenre());
        bookRepository.save(book);
        for (Author auth : book.getAuthors()) {
            if (authorRepository.findByName(auth.getName()) != null) {
                auth = authorRepository.findByName(auth.getName());
            }
            auth.getBooks().add(book);
            authorRepository.save(auth);
        }
        return new ResponseEntity<>(book,HttpStatus.CREATED);
    }

    @GetMapping("/book-edit/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty()) return ResponseEntity.badRequest().body("Book with"+id+"doesnt exist");
        return new ResponseEntity<>(book.get(), HttpStatus.OK);
    }

    @PutMapping("/book-edit/{id}")
    public ResponseEntity editBook(@RequestBody Book book) {
        Book oldBook = bookRepository.findById(book.getId()).get();
        if (oldBook == null) {
            return new ResponseEntity(book,HttpStatus.CONFLICT);
        }
        if (!book.getName().equals(oldBook.getName())) oldBook.setName(book.getName());
        List<Author> mustBEDeleted = new ArrayList<>();
        for (Author author : oldBook.getAuthors()) {
            mustBEDeleted.add(author);
            author.getBooks().remove(oldBook);
        }
        oldBook.getAuthors().clear();
        for (Author author : mustBEDeleted) {
            if (author.getBooks().isEmpty()) authorRepository.deleteById(author.getId());
        }
        mustBEDeleted.clear();
        Iterator<Author> iterator = book.getAuthors().iterator();
        while (iterator.hasNext()) {
            Author author = iterator.next();
            Author oldAuthor = authorRepository.findByName(author.getName());
            if (oldAuthor != null) author = oldAuthor;
            author.getBooks().add(oldBook);
            authorRepository.save(author);
        }
        long mustBeDeletedGenreId = -99999; //магическое число - гарантировано удалится
        if (!book.getGenre().getName().equals(oldBook.getGenre().getName())) {
            oldBook.getGenre().getBooks().remove(oldBook);
            if (oldBook.getGenre().getBooks().isEmpty()) mustBeDeletedGenreId = oldBook.getGenre().getId();
            Genre oldGenre = genreRepository.findByName(book.getGenre().getName());
            if (oldGenre != null) oldBook.setGenre(oldGenre);
            else oldBook.setGenre(book.getGenre());
            oldBook.getGenre().getBooks().add(oldBook);
            genreRepository.save(oldBook.getGenre());
        }
        if (mustBeDeletedGenreId != -99999) genreRepository.deleteById(mustBeDeletedGenreId);
        return new ResponseEntity(oldBook,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBook(@PathVariable("id") Long id) {
        Book book =  bookRepository.findById(id).get();
        if(book==null) {
            return new ResponseEntity("Book with "+id+" doesnt exist",HttpStatus.CONFLICT);
        }
        List<Author> mustBEDeleted = new ArrayList<>();
        for (Author author : book.getAuthors()) {
            mustBEDeleted.add(author);
            author.getBooks().remove(book);
        }
        book.getAuthors().clear();
        bookRepository.save(book);
        for (Author author : mustBEDeleted) {
            if (author.getBooks().isEmpty()) authorRepository.deleteById(author.getId());
        }
        mustBEDeleted.clear();
        book.getGenre().getBooks().remove(book);
        if (book.getGenre().getBooks().isEmpty()) genreRepository.deleteById(book.getGenre().getId());
        else genreRepository.save(book.getGenre());
        book.setGenre(null);
        bookRepository.delete(book);
        return new ResponseEntity(HttpStatus.OK);
    }



}
