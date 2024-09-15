package com.app.Book_Oasis.contact;



import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/contact")
    public String showContactForm(Model model) {
        model.addAttribute("form", new Contact());
        return "contact";
    }

    @PostMapping("/submit")
    public String ContactForm(@ModelAttribute Contact form) {
        contactService.ContactForm(form);
        return "redirect:/submit";
    }
   
    @GetMapping("/submit")
    public String success() {
        return "submitted";
    }
}