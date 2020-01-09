package ru.cource.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import ru.cource.model.domain.Author;
import ru.cource.model.domain.Book;
import ru.cource.model.service.BookShopServiceInterface;
import ru.cource.model.validation.AuthorValidator;
import ru.cource.model.validation.BookValidator;
/**
 * 
 * @author AlexanderM-O
 *
 */
@Controller
@RequestMapping("/")
public class AuthorController {
	@Value("${upload.path}")
	String uploadPath;

	@Autowired
	BookShopServiceInterface bookShopService;

	@Autowired
	AuthorValidator authorValidator;

	@GetMapping("/getAllAuthor")
	public String getAllAuthor(Model model) {
		model.addAttribute("authors", bookShopService.GetAllAuthors());
		return "Author/AllAuthor";
	}

	@GetMapping("ChangeAuthor/{author.id}")
	public String changeAuthorPage(@PathVariable(value = "author.id") int Author_id, Model model) {
		Author author = bookShopService.findAuthorById(Author_id);
		model.addAttribute("author", author);
		return "Author/ChangeAuthorPage";
	}

	@PostMapping("ChangeAuthor/{author.id}")
	public String changingAuthor(@Valid @ModelAttribute Author newAuthor, BindingResult bindingResult,
			@PathVariable(value = "author.id") int author_id, Model model, @RequestParam("file") MultipartFile file)
			throws IllegalStateException, IOException {
		newAuthor.setId(author_id);
		Author oldAuthor = bookShopService.findAuthorById(author_id);
		authorValidator.validate(newAuthor, bindingResult);
		if (bindingResult.hasErrors()) {
			ControllerUtils.addErrorsAndObjectToModel(bindingResult, newAuthor, "author", model);
			return "Author/ChangeAuthorPage";
		}
		if (file.getSize() != 0) {
			ControllerUtils.deleteFileIfExists(oldAuthor.getAvatarFileName(), uploadPath);
			newAuthor.setAvatarFileName(ControllerUtils.insertFileWithoutCollisions(file, uploadPath));
		}
		bookShopService.updateAuthor(newAuthor);
		return "redirect:/getAllAuthor";
	}
	
	@GetMapping("/DeleteAuthor/{author.id}")
    public String deleteAuthorPage(@PathVariable(value = "author.id") int author_id,Model model) {
        //Ask user "are you sure"
        model.addAttribute("author_id",author_id);
        return "Author/DeleteAuthorPage";
    }

    @PostMapping("/DeleteAuthor/{author.id}")
    public String deletingBook(@RequestParam(value = "descition", required = false) String decision,
                              @PathVariable(value = "author.id") int author_id) {
        //user have made decision delete or not author
        if(decision.equals("YES")){
        	ControllerUtils.deleteFileIfExists(bookShopService.findAuthorById(author_id).getAvatarFileName(),uploadPath);
            bookShopService.deleteAuthorById(author_id);
        }
        return "redirect:/getAllAuthor";
    }

}
