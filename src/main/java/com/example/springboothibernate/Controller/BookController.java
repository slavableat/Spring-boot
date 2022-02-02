package com.example.springboothibernate.Controller;

import com.example.springboothibernate.Model.Author;
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

import java.util.ArrayList;
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
        if (book.getName() == null) return "redirect:/books";
        List<Author> mustBEDeleted=new ArrayList<>();
        for (Author author:book.getAuthors()) {
            mustBEDeleted.add(author);
            author.getBooks().remove(book);
        }
        book.getAuthors().clear();
        bookService.saveBook(book);
        for (Author author:mustBEDeleted) {
            if(author.getBooks().isEmpty()) authorService.deleteById(author.getId());
        }
        mustBEDeleted.clear();
        book.getGenre().getBooks().remove(book);
        if(book.getGenre().getBooks().isEmpty()) genreService.deleteById(book.getGenre().getId());
        else genreService.saveGenre(book.getGenre());
        book.setGenre(null);
        bookService.delete(book);
        return "redirect:/books";
    }

    @GetMapping("/book-create")
    public String createUserForm(Book book){
        return "book-form";
    }

    @PostMapping("/book-create")
    public String createUser(Model model){
        Book newBook=new Book(model.getAttribute("name"));
        Genre genre=new Genre( model.getAttribute("genre"));
        String[] authors = request.getParameterValues("author");
        for (int i = 0; i < authors.length; i++) {
            if(authors[i].equals("")) continue;
            newBook.addAuthor(new Author(authors[i]));
        }
        newBook.setGenre(genre);
        bookService.saveBook(book);
        return "redirect:/books";
    }

}
