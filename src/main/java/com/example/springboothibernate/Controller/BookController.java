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
import java.util.Scanner;

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

    @GetMapping("/book-create") //тест добавления
    public String createUserForm(){
        Book book=new Book();
        book.setName("Adventures");
        Genre genre=new Genre();
        genre.setName("Fantasy");
        Author author1=new Author();
        author1.setName("Tolstoy");
        Author author2=new Author();
        author2.setName("Esenin");
        Author author3=new Author();
        author3.setName("Dostoevskiy");
        book.setGenre(genre);
        book.getAuthors().add(author1);
        book.getAuthors().add(author2);
        book.getAuthors().add(author3);
        Genre findGEnre=genreService.findByName(book.getGenre().getName());
        if(findGEnre!=null) book.setGenre(findGEnre);
        book.getGenre().getBooks().add(book);
        genreService.saveGenre(book.getGenre());
        bookService.saveBook(book);
        for(Author auth :book.getAuthors())
        {

            if(authorService.findByName(auth.getName())!=null) {
                auth=authorService.findByName(auth.getName());
            }
            auth.getBooks().add(book);
            authorService.saveAuthor(auth);

        }
        //bookService.saveBook(book);
        return "redirect:/books";
    }

    @PostMapping("/book-create")
    public String createUser(Book book,Model model){
        List<Book> arr;
        String x=(String)model.getAttribute("name");
        return "redirect:/books";
    }
    //@GetMapping("/book-update/{id}")
    //public String updateUserForm(@PathVariable("id") Long id, Model model){
    //    User user = userService.findById(id);
    //    model.addAttribute("user", user);
    //    return "user-update";
    //}
//
    //@PostMapping("/user-update")
    //public String updateUser(User user){
    //    userService.saveUser(user);
    //    return "redirect:/users";
    //}

}
