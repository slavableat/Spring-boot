package com.example.springboothibernate.Controller;

import com.example.springboothibernate.Model.Book;
import com.example.springboothibernate.Model.Genre;
import com.example.springboothibernate.Service.AuthorService;
import com.example.springboothibernate.Service.BookService;
import com.example.springboothibernate.Service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private AuthorService authorService;

    @GetMapping("/books")
    public String findAll(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("listBook", books);
        return "list-book";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        Book book = (Book)bookService.findById(id);
        if (book == null) return "redirect:/books";
        book.getAuthors().clear();
        book.setGenre(null);
        bookService.deleteById(book.getId());
        return "redirect:/books";
    }

    @GetMapping("/book-create")
    public String createUserForm(Book book){
        return "book-form";
    }

    @PostMapping("/book-create")
    public String createUser(Book book){
        bookService.saveBook(book);
        return "redirect:/books";
    }

}
