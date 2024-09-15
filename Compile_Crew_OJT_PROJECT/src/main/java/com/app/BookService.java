package com.app;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.bookStore.entity.Book;
//import com.bookStore.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bRepo;

    // Save a book to the repository
    public void save(Book b) {
        bRepo.save(b);
    }

    // Get all books from the repository
    public List<Book> getAllBook() {
        return bRepo.findAll();
    }

    // Get a book by its ID from the repository
    public Book getBookById(int id) {
        Optional<Book> optionalBook = bRepo.findById(id);
        return optionalBook.orElse(null); // Return null if not found; consider throwing an exception instead
    }

    // Delete a book by its ID from the repository
    public void deleteById(int id) {
        bRepo.deleteById(id);
    }
}
