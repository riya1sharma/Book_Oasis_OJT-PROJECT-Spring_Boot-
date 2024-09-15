package com.app.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookDonationService {

    @Autowired
    private BookDonationRepository bookDonationRepository;

    public void saveDonation(BookDonationForm bookDonationForm) {
        // Save the form data to the database
        bookDonationRepository.save(bookDonationForm);
    }
}

