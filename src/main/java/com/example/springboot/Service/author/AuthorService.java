package com.example.springboot.Service.author;

import com.example.springboot.Model.Author;

import java.util.List;

public interface AuthorService {
   List<Author> findAllAuthors();
}
