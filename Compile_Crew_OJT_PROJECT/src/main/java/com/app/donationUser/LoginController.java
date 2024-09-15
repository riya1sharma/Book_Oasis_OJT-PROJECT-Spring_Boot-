package com.app.donationUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.app.donationUser.LoginRepository; // Import your repository

@Controller
public class LoginController {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Display the login form
    @GetMapping("/login1")
    public String showLoginForm() {
        return "login1"; // login1.html is the Thymeleaf template for the form
    }
    
    // Display the signup form
    @GetMapping("/signup1")
    public String showSignupForm(Model model) {
        model.addAttribute("login", new Login()); // Use "login" as the attribute name
        return "signup1"; // Return the name of the signup view (e.g., signup1.html)
    }

    // Handle user registration
    @PostMapping("/signup1")
    public String registerUser(@ModelAttribute("login") Login login, Model model) {
        if (loginRepository.findByUsername(login.getUsername()) != null) {
            model.addAttribute("error", "Username already exists");
            return "signup1";
        }

        // Encode the password before saving
        login.setPassword(passwordEncoder.encode(login.getPassword()));
        loginRepository.save(login);

        return "redirect:/login1"; // Redirect to login page after successful registration
    }
    
    // Handle successful login and redirect to book donation form
    @PostMapping("/login1")
    public String loginUser(@ModelAttribute("login") Login login, Model model) {
        Login existingLogin = loginRepository.findByUsername(login.getUsername());
        
        if (existingLogin != null && passwordEncoder.matches(login.getPassword(), existingLogin.getPassword())) {
            // Successful login
            return "redirect:/bookDonationForm"; // Redirect to book donation form page
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login1"; // Redirect back to login page with an error
        }
    }
}
