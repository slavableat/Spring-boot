package com.example.springboothibernate.Service;

import com.example.springboothibernate.Model.Book;
import com.example.springboothibernate.Model.Genre;
import com.example.springboothibernate.Repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GenreService {
    @Autowired
    private  GenreRepository genreRepository;

    public Genre saveGenre(Genre genre){
        return genreRepository.save(genre);
    }
    public Genre findById(Long id){
        return genreRepository.getOne(id);
    }
    public List<Genre> findAll(){
        return genreRepository.findAll();
    }

    public void deleteById(Long id){
        genreRepository.deleteById(id);
    }

    public void updateGenre(Genre genre){

    }
}
