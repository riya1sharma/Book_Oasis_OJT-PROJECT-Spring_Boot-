package com.app.Book_Oasis.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ContactService {
	@Autowired
    private ContactRepository contactRepository;

    public void ContactForm(Contact form) {
        contactRepository.save(form);
    }
}
