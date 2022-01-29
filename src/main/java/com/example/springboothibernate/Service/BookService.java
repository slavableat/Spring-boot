package com.example.springboothibernate.Service;

import com.example.springboothibernate.Model.Book;
import com.example.springboothibernate.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book findById(Long id){
        return bookRepository.getOne(id);
    }
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book saveUser(Book book){
        return bookRepository.save(book);
    }

    public void deleteById(Long id){
        bookRepository.deleteById(id);
    }

    public void updateBook(Book book){

    }
}
