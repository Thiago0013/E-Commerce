package com.example.commerce.repository;

import com.example.commerce.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface ClientRepository extends JpaRepository<Client, Long>{

    UserDetails findByEmail(String email);
}
