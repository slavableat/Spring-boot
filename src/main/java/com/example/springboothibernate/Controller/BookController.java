package com.example.springboothibernate.Controller;

import com.example.springboothibernate.Model.Book;
import com.example.springboothibernate.Service.BookService;
import com.example.springboothibernate.Service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BookController {
private final BookService bookService;
private final GenreService genreService;
@Autowired
    public BookController(BookService bookService, GenreService genreService) {
        this.bookService = bookService;
        this.genreService = genreService;
    }

    @GetMapping("/books")
    public String findAll(Model model){
    List<Book> books=bookService.findAll();
    model.addAttribute("books",books);
    return "book-list";
}
}
