package com.app;

import java.util.List;

import javax.naming.AuthenticationException;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BookService bookService;  // Add this line
    private final MyBookListService myBookService; // Add this line
    
    @Autowired
    private AuthenticationManager authenticationManager; // Inject AuthenticationManager


    @Autowired
    public AuthController(UserRepository userRepository, 
                          @Lazy PasswordEncoder passwordEncoder,
                          BookService bookService,  // Add this line
                          MyBookListService myBookService) { // Add this line
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.bookService = bookService;  // Add this line
        this.myBookService = myBookService;  // Add this line
    }

    @GetMapping("/")
    public String showIndex() {
        return "index"; // Return the name of the index view (e.g., index.html)
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup"; // Return the name of the signup view (e.g., signup.html)
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Username already exists");
            return "signup";
        }

        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/login"; // Redirect to login page after successful registration
    }



    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Return login view (e.g., login.html)
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model) {
        // Perform authentication using the authentication manager
        org.springframework.security.core.Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );

        // Set the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // If authentication is successful, redirect to the home page
        return "redirect:/home";
    }
    
    
 //   }etMapping("/login1")
//    public String showLogi1nForm() {
//        return "login1"; // Return the name of the login view (e.g., login.html)
//    }

    @GetMapping("/cart")
    public String showCartPage() {
        return "cart"; // Return the name of the cart view (e.g., cart.html)
    }

//    @GetMapping("/contact")
//    public String showContactPage() {
//        return "contact"; // Return the name of the contact view (e.g., contact.html)
//    }
//	
	@GetMapping("/contact-page")
	public String getContact() {
	    return "contact";
	}

    @GetMapping("/form")
    public String showFormPage() {
        return "form"; // Return the name of the form view (e.g., form.html)
    }

    @GetMapping("/privacy")
    public String showPrivacyPage() {
        return "privacy"; // Return the name of the privacy policy view (e.g., privacy.html)
    }

    @GetMapping("/terms")
    public String showTermsPage() {
        return "terms-and-conditions"; // Return the name of the terms and conditions view (e.g., terms-and-conditions.html)
    }
    
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/book_register")
    public String bookRegister() {
        return "bookRegister";
    }

    @GetMapping("/available_books")
    public ModelAndView getAllBook() {
        List<Book> list = bookService.getAllBook();  // Use bookService here
        return new ModelAndView("bookList", "book", list);
    }

    @PostMapping("/save")
    public String addBook(@ModelAttribute Book b) {
        bookService.save(b);  // Use bookService here
        return "redirect:/available_books";
    }
//
//    @GetMapping("/my_books")
//    public String getMyBooks(Model model) {
//        List<MyBookList> list = myBookService.getAllMyBooks();  // Use myBookService here
//        model.addAttribute("book", list);
//        return "myBooks";
//    }

    @RequestMapping("/mylist/{id}")
    public String getMyList(@PathVariable("id") int id) {
        Book b = bookService.getBookById(id);  // Use bookService here
        MyBookList mb = new MyBookList(b.getId(), b.getName(), b.getAuthor(), b.getPrice());
        myBookService.saveMyBooks(mb);  // Use myBookService here
        return "redirect:/my_books";
    }

    @RequestMapping("/editBook/{id}")
    public String editBook(@PathVariable("id") int id, Model model) {
        Book b = bookService.getBookById(id);  // Use bookService here
        model.addAttribute("book", b);
        return "bookEdit";
    }

    @RequestMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.deleteById(id);  // Use bookService here
        return "redirect:/available_books";
    }
    
    
    
}