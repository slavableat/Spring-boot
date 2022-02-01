package com.example.springboothibernate.Service;

import com.example.springboothibernate.Model.Author;
import com.example.springboothibernate.Model.Book;
import com.example.springboothibernate.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private  AuthorRepository authorRepository;


    public Author getById(Long id){
        return authorRepository.getOne(id);
    }
    public List<Author> findAll(){
        return authorRepository.findAll();
    }

    public Author saveAuthor(Author author){
        return authorRepository.save(author);
    }

    public void deleteById(Long id){authorRepository.deleteById(id);
    }

}
