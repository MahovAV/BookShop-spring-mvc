package ru.cource.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ru.cource.model.domain.Book;
import ru.cource.model.domain.Genre;
import ru.cource.model.domain.User;
import ru.cource.model.service.BookShopServiceInterface;
import ru.cource.model.validation.BookValidator;


/**
 * 
 * @author AlexanderM-O
 *
 */
@Controller
@RequestMapping("/")
public class BookController {
	@Value("${upload.path}")
	String uploadPath;
	
    @Autowired
    BookShopServiceInterface bookShopService;
    
    @Autowired
    BookValidator bookValidator;
    
    static Set<String> allGenre;
    
    static {
    	 allGenre=Arrays.asList(Genre.values()).stream()
    												  .map(en->en.name())
    			                                      .collect(Collectors.toSet());
    }
    
    static void addErrorsAndBookToModel(BindingResult bindingResult,Book book,Model model) {
		Map<String,String> FiledErrors=ControllerUtils.getErrors(bindingResult);
		model.mergeAttributes(FiledErrors);
		model.addAttribute("AllGenres",allGenre);
		model.addAttribute("book",book);
    }
    
    @GetMapping("/Home")
    public String home(@AuthenticationPrincipal User user,Model model){
    	model.addAttribute("user", user);
        return "HomePage";
    }

    @GetMapping("/getAll")
    public String getAllUsers(Model model){
        model.addAttribute("books",bookShopService.GetAllBook());
        return "Book/AllBooks";
    }


    @GetMapping("/CreateBook")
    public String createBookPage(Model model){
        model.addAttribute("AllGenres",allGenre);
        return "Book/CreateBookPage";
    }

    @PostMapping("/CreateBook")
    public String creatingBook(
    		@Valid @ModelAttribute Book book,
    		BindingResult bindingResult,
    		Model model,
    		@RequestParam("file") MultipartFile file
    ) throws IllegalStateException, IOException {
    	bookValidator.validate(book, bindingResult);
    	if(bindingResult.hasErrors()) {
    		addErrorsAndBookToModel(bindingResult,book,model);
    		return "Book/CreateBookPage";
    	}
    	if(file.getSize()!=0) {
    		File uploadDir=new File(uploadPath);
    		if(!uploadDir.exists()) {
    			uploadDir.mkdir();
    		}
            book.setBookCoverFileName(ControllerUtils
            		.insertFileWithoutCollisions(file,uploadPath));
    	}
        bookShopService.createBook(book);
        return "redirect:/getAll";
    }

    @GetMapping("ChangeBook/{book_id}")
    public String changeBookPage(@PathVariable(value = "book_id") int Book_id, Model model){
        Book book=bookShopService.findBookById(Book_id);
        model.addAttribute("AllGenres",allGenre);
        model.addAttribute("book",book);
        return "Book/ChangeBookPage";
    }
    @PostMapping("ChangeBook/{book_id}")
    public String changingBook(
    		@Valid @ModelAttribute Book newbook,
    		BindingResult bindingResult,
    		@PathVariable(value = "book_id") int Book_id,
    		Model model,
    		@RequestParam("file") MultipartFile file
    ) throws IllegalStateException, IOException{
    	newbook.setId(Book_id);
        Book oldBook=bookShopService.findBookById(Book_id);       
        bookValidator.validate(newbook, bindingResult,oldBook);
    	if(bindingResult.hasErrors()) {
    		addErrorsAndBookToModel(bindingResult,newbook,model);
    		return "Book/ChangeBookPage";
    	}
    	if(file.getSize()!=0) {
    		ControllerUtils.deleteFileIfExists(oldBook.getBookCoverFileName(),uploadPath);
            newbook.setBookCoverFileName(ControllerUtils.
            		insertFileWithoutCollisions(file,uploadPath));
    	}    	
        bookShopService.updateBook(newbook);
        return "redirect:/getAll";
    }

    @GetMapping("/DeleteBook/{book_id}")
    public String deleteBookPage(@PathVariable(value = "book_id") int Book_id,Model model) {
        //Ask user "are you sure"
        model.addAttribute("Book_id",Book_id);
        return "Book/DeleteBookPage";
    }

    @PostMapping("/DeleteBook/{book_id}")
    public String deletingBook(@RequestParam(value = "descition", required = false) String decision,
                              @PathVariable(value = "book_id") int Book_id) {
        //user have made decision delete or not book
        if(decision.equals("YES")){
        	ControllerUtils.deleteFileIfExists(bookShopService.findBookById(Book_id).getBookCoverFileName(),uploadPath);
            bookShopService.deleteBookById(Book_id);
            return "redirect:/getAll";
        }
        return "redirect:/getAll";
    }
}


