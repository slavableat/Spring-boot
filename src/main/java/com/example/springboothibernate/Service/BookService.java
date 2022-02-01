package com.example.springboothibernate.Service;

import com.example.springboothibernate.Model.Book;
import com.example.springboothibernate.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private  BookRepository bookRepository;

    public Book findById(Long id){//КОСТЫЛИЩЕ, не подругражет экземпляр наголо, ттоько из кэша
        findAll();
        return bookRepository.getOne(id);
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book saveBook(Book book){
        return bookRepository.save(book);
    }

    public void delete(Book book){
        bookRepository.delete(book);
    }

    public void updateBook(Book book){

    }
}
