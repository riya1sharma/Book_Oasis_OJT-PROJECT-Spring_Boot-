package com.app.donationUser;


import org.springframework.data.repository.CrudRepository;
import com.app.User;

public interface LoginRepository extends CrudRepository<Login, Long> {
    Login findByUsername(String username);
}
