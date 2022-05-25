package com.example.springboot.Service.book;

import com.example.springboot.Model.Author;
import com.example.springboot.Model.Book;
import com.example.springboot.Model.Genre;
import com.example.springboot.Repository.AuthorRepository;
import com.example.springboot.Repository.BookRepository;
import com.example.springboot.Repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book addBook(Book book) {
        Genre findGenre = genreRepository.findByName(book.getGenre().getName());
        if (findGenre != null) {
            book.setGenre(findGenre);
        }
        book.getGenre().getBooks().add(book);
        genreRepository.save(book.getGenre());
        bookRepository.save(book);
        for (Author author : book.getAuthors()) {
            if (authorRepository.findByName(author.getName()) != null) {
                author = authorRepository.findByName(author.getName());
            }
            author.getBooks().add(book);
            authorRepository.save(author);
        }
        return book;
    }

    @Override
    public Book getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            return null;
        }
        return book.get();
    }

    @Override
    public Book editBook(Book book) {
        //поменял
        Book oldBook = bookRepository.findById(book.getId()).orElseGet(null);
        if (oldBook == null) {
            return null;
        }
        if (!book.getName().equals(oldBook.getName())) {
            oldBook.setName(book.getName());
        }
        List<Author> mustBEDeleted = new ArrayList<>();
        for (Author author : oldBook.getAuthors()) {
            mustBEDeleted.add(author);
            author.getBooks().remove(oldBook);
        }
        oldBook.getAuthors().clear();
        for (Author author : mustBEDeleted) {
            if (author.getBooks().isEmpty()) {
                authorRepository.deleteById(author.getId());
            }
        }
        mustBEDeleted.clear();
        for (Author author : book.getAuthors()) {
            Author oldAuthor = authorRepository.findByName(author.getName());
            if (oldAuthor != null) {
                author = oldAuthor;
            }
            author.getBooks().add(oldBook);
            authorRepository.save(author);
        }
        long mustBeDeletedGenreId = -99999; //магическое число - гарантировано удалится
        if (!book.getGenre().getName().equals(oldBook.getGenre().getName())) {
            oldBook.getGenre().getBooks().remove(oldBook);
            if (oldBook.getGenre().getBooks().isEmpty()) {
                mustBeDeletedGenreId = oldBook.getGenre().getId();
            }
            Genre oldGenre = genreRepository.findByName(book.getGenre().getName());
            if (oldGenre != null) {
                oldBook.setGenre(oldGenre);
            } else {
                oldBook.setGenre(book.getGenre());
            }
            oldBook.getGenre().getBooks().add(oldBook);
            genreRepository.save(oldBook.getGenre());
        }
        if (mustBeDeletedGenreId != -99999) {
            genreRepository.deleteById(mustBeDeletedGenreId);
        }
        return oldBook;
    }

    @Override
    public void deleteById(Long id) {
        Book book = bookRepository.findById(id).get();
        if (book == null) {
            return;
        }
        List<Author> mustBEDeleted = new ArrayList<>();
        for (Author author : book.getAuthors()) {
            mustBEDeleted.add(author);
            author.getBooks().remove(book);
        }
        book.getAuthors().clear();
        bookRepository.save(book);
        for (Author author : mustBEDeleted) {
            if (author.getBooks().isEmpty()) {
                authorRepository.deleteById(author.getId());
            }
        }
        mustBEDeleted.clear();
        book.getGenre().getBooks().remove(book);
        if (book.getGenre().getBooks().isEmpty()) {
            genreRepository.deleteById(book.getGenre().getId());
        } else {
            genreRepository.save(book.getGenre());
        }
        book.setGenre(null);
        bookRepository.delete(book);
    }
}
