package com.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

//import com.bookStore.service.MyBookListService;

@Controller
public class MyBookListController {

    @Autowired
    private MyBookListService service;

    // Delete a book from the user's book list by its ID
    @RequestMapping("/deleteMyList/{id}")
    public String deleteMyList(@PathVariable("id") int id, Model model) {
        try {
            service.deleteById(id);
            model.addAttribute("message", "Book removed from your list successfully.");
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while trying to remove the book: " + e.getMessage());
        }
        return "redirect:/my_books"; // Redirect back to my books page
    }
}
