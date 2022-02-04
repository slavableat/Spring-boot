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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private AuthorService authorService;

    @GetMapping("/books")
    public List<Book> findAll() {
        List<Book> books = bookService.findAll();
        return books;
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
        book.setName("Adventures");             //test book
        Genre genre=new Genre();                //test book
        genre.setName("Fantasy");               //test book
        Author author1=new Author();                //test book
        author1.setName("Tolstoy");             //test book
        Author author2=new Author();                //test book
        author2.setName("Esenin");              //test book
        Author author3=new Author();                //test book
        author3.setName("Dostoevskiy");             //test book
        book.setGenre(genre);               //test book
        book.getAuthors().add(author1);             //test book
        book.getAuthors().add(author2);             //test book
        book.getAuthors().add(author3);             //test book
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

    @GetMapping("/book-edit/{id}")
    public String editBook(@PathVariable("id") Long id){
        Book book=new Book();//test book
        book.setName("Adventures 2");//test book
        Genre genre=new Genre();//test book
        genre.setName("Mathanal");//test book
        Author author1=new Author();//test book
        author1.setName("Brodskiy");//test book
        Author author2=new Author();//test book
        author2.setName("Esenin");//test book
        Author author3=new Author();//test book
        author3.setName("Pavluk");//test book
        book.setGenre(genre);//test book
        book.getAuthors().add(author1);//test book
        book.getAuthors().add(author2);//test book
        book.getAuthors().add(author3);//test book

        Book oldBook=bookService.findById(id);
        if(oldBook==null) return "redirect:/books";
        if(!book.getName().equals(oldBook.getName())) oldBook.setName(book.getName());
        List<Author> mustBEDeleted=new ArrayList<>();
        for (Author author:oldBook.getAuthors()) {
            mustBEDeleted.add(author);
            author.getBooks().remove(oldBook);
        }
        oldBook.getAuthors().clear();
        // bookService.saveBook(book);
        for (Author author:mustBEDeleted) {
            if(author.getBooks().isEmpty()) authorService.deleteById(author.getId());
        }
        mustBEDeleted.clear();
        Iterator<Author> iterator=book.getAuthors().iterator();
        while(iterator.hasNext()){
            Author author=iterator.next();
            Author oldAuthor=authorService.findByName(author.getName());
            if(oldAuthor!=null) author=oldAuthor;
                author.getBooks().add(oldBook);
                authorService.saveAuthor(author);
        }
        long mustBeDeletedGenreId=-99999;
        if(!book.getGenre().getName().equals(oldBook.getGenre().getName())){
            oldBook.getGenre().getBooks().remove(oldBook);
            if(oldBook.getGenre().getBooks().isEmpty()) mustBeDeletedGenreId=oldBook.getGenre().getId();
            Genre oldGenre=genreService.findByName(book.getGenre().getName());
            if(oldGenre!=null) oldBook.setGenre(oldGenre);
            else oldBook.setGenre(book.getGenre());
            oldBook.getGenre().getBooks().add(oldBook);
            genreService.saveGenre(oldBook.getGenre());
        }
        if(mustBeDeletedGenreId!=-99999) genreService.deleteById(mustBeDeletedGenreId);
    return "redirect:/books";}
}
