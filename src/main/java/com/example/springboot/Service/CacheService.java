package com.example.springboot.Service;

import com.example.springboot.Model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CacheService {
    private List<Book> books = new ArrayList<>();

    public List<Book> getBooks() {
        return  this.books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void clearCache(){
        this.books.clear();
    }

    public Book getIfContainsElseGetNull(Long id){
        if(this.books.isEmpty()) return null;
        for (Book book: this.books) {
            if(book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }
}
