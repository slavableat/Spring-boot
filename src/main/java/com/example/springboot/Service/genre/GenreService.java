package com.example.springboot.Service.genre;

import com.example.springboot.Model.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findAllGenres();
}
