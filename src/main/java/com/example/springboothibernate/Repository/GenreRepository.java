package com.example.springboothibernate.Repository;

import com.example.springboothibernate.Model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre,Long> {
}
