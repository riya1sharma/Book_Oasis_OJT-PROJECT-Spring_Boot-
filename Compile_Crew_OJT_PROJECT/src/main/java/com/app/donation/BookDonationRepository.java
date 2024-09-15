package com.app.donation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDonationRepository extends JpaRepository<BookDonationForm, Long> {
    // Custom queries can be added here if needed in the future
   

}
