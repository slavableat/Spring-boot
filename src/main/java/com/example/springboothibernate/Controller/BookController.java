package com.example.springboothibernate.Controller;

import com.example.springboothibernate.Model.Author;
import com.example.springboothibernate.Model.Book;
import com.example.springboothibernate.Model.Genre;
import com.example.springboothibernate.Service.AuthorService;
import com.example.springboothibernate.Service.BookService;
import com.example.springboothibernate.Service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private AuthorService authorService;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> findAllBooks() {
        List<Book> books = bookService.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> findAllGenres() {
        List<Genre> genres = genreService.findAll();
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }
    @GetMapping("/authors")
    public ResponseEntity<List<Author>> findAllAuthors() {
        List<Author> authors = authorService.findAll();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBook(@PathVariable("id") Long id) {
        Book book = (Book) bookService.findById(id);
        List<Author> mustBEDeleted = new ArrayList<>();
        for (Author author : book.getAuthors()) {
            mustBEDeleted.add(author);
            author.getBooks().remove(book);
        }
        book.getAuthors().clear();
        bookService.saveBook(book);
        for (Author author : mustBEDeleted) {
            if (author.getBooks().isEmpty()) authorService.deleteById(author.getId());
        }
        mustBEDeleted.clear();
        book.getGenre().getBooks().remove(book);
        if (book.getGenre().getBooks().isEmpty()) genreService.deleteById(book.getGenre().getId());
        else genreService.saveGenre(book.getGenre());
        book.setGenre(null);
        bookService.delete(book);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/book-create") //тест добавления
    public ResponseEntity<Map<String, Boolean>> addBook(@RequestBody Book book) {
        Genre findGEnre = genreService.findByName(book.getGenre().getName());
        if (findGEnre != null) book.setGenre(findGEnre);
        book.getGenre().getBooks().add(book);
        genreService.saveGenre(book.getGenre());
        bookService.saveBook(book);
        for (Author auth : book.getAuthors()) {
            if (authorService.findByName(auth.getName()) != null) {
                auth = authorService.findByName(auth.getName());
            }
            auth.getBooks().add(book);
            authorService.saveAuthor(auth);

        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("created", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/book-edit/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        Book book = bookService.findById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping("/book-edit/{id}")
    public ResponseEntity<Map<String, Boolean>> editBook(@RequestBody Book book) {
        Book oldBook = bookService.findById(book.getId());
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
        // bookService.saveBook(book);
        for (Author author : mustBEDeleted) {
            if (author.getBooks().isEmpty()) authorService.deleteById(author.getId());
        }
        mustBEDeleted.clear();
        Iterator<Author> iterator = book.getAuthors().iterator();
        while (iterator.hasNext()) {
            Author author = iterator.next();
            Author oldAuthor = authorService.findByName(author.getName());
            if (oldAuthor != null) author = oldAuthor;
            author.getBooks().add(oldBook);
            authorService.saveAuthor(author);
        }
        long mustBeDeletedGenreId = -99999;
        if (!book.getGenre().getName().equals(oldBook.getGenre().getName())) {
            oldBook.getGenre().getBooks().remove(oldBook);
            if (oldBook.getGenre().getBooks().isEmpty()) mustBeDeletedGenreId = oldBook.getGenre().getId();
            Genre oldGenre = genreService.findByName(book.getGenre().getName());
            if (oldGenre != null) oldBook.setGenre(oldGenre);
            else oldBook.setGenre(book.getGenre());
            oldBook.getGenre().getBooks().add(oldBook);
            genreService.saveGenre(oldBook.getGenre());
        }
        if (mustBeDeletedGenreId != -99999) genreService.deleteById(mustBeDeletedGenreId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("edit", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
