package Controller;

import Helper.AllGenres;
import Helper.AuthorRepresentation;
import Model.Domain.Author;
import Model.Domain.Book;
import Model.Domain.enumOfGenres;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import Model.Service.BookShopService;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by user on 12.11.2019.
 */
@Controller
@RequestMapping("/")
public class WorkWithBook {

    @Autowired
    BookShopService bookShopService;

    @Autowired
    AllGenres allStringgenres;

    @Autowired
    AuthorRepresentation authorRepresentation;

    @GetMapping("/")
    public String Home(){
        return "index";
    }

    @GetMapping("/getAll")
    public String getAllUsers(Model model){
        model.addAttribute("books",bookShopService.GetAllBook());
        return "AllBooks";
    }


    @GetMapping("/addBook")
    //page in which we are creating new book
    //should have all possible GENRES
    public String addBook(Model model){
        model.addAttribute("AllGenres",allStringgenres.getAllGenres());
        return "NewBook";
    }

    @PostMapping("/BookIsCreated")
    //page after which we have book
    //should create complete book and send it to service
    public String CreateBook(@RequestParam(value = "EnumOfGenre", required = false) Set<String> checkboxValues,
                             @RequestParam(value = "Authors", required = false) String Authors,
                             @ModelAttribute("book")Book book) {
        Set<Author> authors=authorRepresentation.getAuthorsFromString(Authors);
        Set<enumOfGenres> genres=new HashSet<enumOfGenres>();
        //Set<String> checkboxValues  не явно создает коллекцию
        //если нету значений то null а не пустая коллекция!!!
        if(checkboxValues!=null) {
            for (String s : checkboxValues) {
                genres.add(enumOfGenres.valueOf(s));
            }
        }
        //creating all authors and hand complete book to SERVICE
        book.setAuthors(authors);
        book.setGenre(genres);
        bookShopService.createBook(book);
        return "BookIsCreated";
    }

    @GetMapping("ChangeBook/{book_id}")
    public String ChangeBook(@PathVariable(value = "book_id") int Book_id, Model model){
        Book book=bookShopService.getById(Book_id);
        model.addAttribute("AllGenres",allStringgenres.getAllGenres());
        model.addAttribute("CheckedGenres",allStringgenres.getGenresFromSet(book.getGenre()));
        model.addAttribute("Book_name",book.getName());
        model.addAttribute("Book_id",book.getId());
        model.addAttribute("Authors",authorRepresentation.getString(book.getAuthors()));
        //send all data to user and wait new value to change the book
        //changing the book with new values
        return "ChangeBook";
    }
    @PostMapping("BookIsChanged/{book_id}")
    public String ChangeBook(@RequestParam(value = "EnumOfGenre", required = false) Set<String> checkboxValues,
                             @RequestParam(value = "Authors", required = false) String Authors,
                             @RequestParam(value = "NewName", required = false) String NewName,
                             @PathVariable(value = "book_id") int Book_id, Model model){
        Book book=bookShopService.getById(Book_id);
        Set<enumOfGenres> genres=new HashSet<enumOfGenres>();
        if(checkboxValues!=null) {
            for (String s : checkboxValues) {
                genres.add(enumOfGenres.valueOf(s));
            }
        }
        book.setGenre(genres);
        book.setName(NewName);
        book.setAuthors(authorRepresentation.getAuthorsFromString(Authors));
        model.addAttribute("book_id",book.getId());
        bookShopService.updateBook(book);
        return "BookIsChanged";
    }
}


