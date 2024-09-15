package com.app.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class BookDonationController {

    @Autowired
    private BookDonationService bookDonationService;

    @GetMapping("/bookDonationForm")
    public String showBookDonationForm(Model model) {
        model.addAttribute("bookDonationForm", new BookDonationForm());
        return "bookDonationForm";
    }

    @PostMapping("/submit_donate")
    public String submitBookDonation(@Valid @ModelAttribute("bookDonationForm") BookDonationForm bookDonationForm,
                                     BindingResult result,
                                     Model model) {
        if (result.hasErrors()) {
            // If there are validation errors, return to the form
            return "bookDonationForm";
        }

        // Process the donation form (e.g., save to the database)
        bookDonationService.saveDonation(bookDonationForm);

        // Add success message to the model
        model.addAttribute("successMessage", "Thank you for your donation!");

        // Redirect to a success page or the same form with success message
        return "redirect:/bookDonationSuccess";
    }

    // Success page endpoint
    @GetMapping("/bookDonationSuccess")
    public String showSuccessPage() {
        return "bookDonationSuccess";
        
        
        
    }
    
//    @GetMapping("/login2")
//    public String showlogin2Page() {
//    	return "redirect:/bookDonationForm";
//    }
}
