package Controller;

import Model.Domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import Model.Service.BookShopService;

/**
 * Created by user on 12.11.2019.
 */
@Controller
@RequestMapping("/")
public class Simple {

    @Autowired
    BookShopService bookShopService;

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
    public String addBook(Model model){
        return "NewBook";
    }

    @PostMapping("/BookIsCreated")
    //we send all data and validate created book
    public String BookCreatedGoToStore(@ModelAttribute("book")Book book){
        bookShopService.createBook(book);
        return "BookIsCreated";
    }
}
