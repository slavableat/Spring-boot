package com.example.springboothibernate.Controller;

import com.example.springboothibernate.Model.Author;
import com.example.springboothibernate.Model.Book;
import com.example.springboothibernate.Model.Genre;
import com.example.springboothibernate.Repository.AuthorRepository;
import com.example.springboothibernate.Repository.BookRepository;
import com.example.springboothibernate.Repository.GenreRepository;
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
        List<Book> books = bookRepository.findAll();

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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBook(@PathVariable("id") Long id) {
        Book book =  bookRepository.findById(id).get();
        if(book==null) {
            Map<String, Boolean> response = new HashMap<>();
            response.put("not-deleted", Boolean.FALSE);
            return ResponseEntity.ok(response);
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
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/book-create")
    public ResponseEntity addBook(@RequestBody Book book) {
        Genre findGEnre = genreRepository.findByName(book.getGenre().getName());
        if (findGEnre != null) book.setGenre(findGEnre);
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
        Map<String, Boolean> response = new HashMap<>();
        response.put("created", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/book-edit/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        Book book = bookRepository.findById(id).get();
        if(book==null) return  ResponseEntity.badRequest().body(null);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PutMapping("/book-edit/{id}")
    public ResponseEntity editBook(@RequestBody Book book) {
        Book oldBook = bookRepository.findById(book.getId()).get();
        if (oldBook == null) {
            Map<String, Boolean> response = new HashMap<>();
            response.put("edit", Boolean.FALSE);
            return ResponseEntity.ok(response);
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
        Map<String, Boolean> response = new HashMap<>();
        response.put("edit", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
