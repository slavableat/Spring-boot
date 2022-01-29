package com.example.springboothibernate.Service;

import com.example.springboothibernate.Model.Author;
import com.example.springboothibernate.Model.Book;
import com.example.springboothibernate.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
@Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author findById(Long id){
        return authorRepository.getOne(id);
    }
    public List<Author> findAll(){
        return authorRepository.findAll();
    }

    public Author saveUser(Author author){
        return authorRepository.save(author);
    }

    public void deleteById(Long id){
        authorRepository.deleteById(id);
    }

    public void updateAuthor(Author author){

    }
}
