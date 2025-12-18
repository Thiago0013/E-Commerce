package com.example.commerce.repository;

import com.example.commerce.model.Client;
import com.example.commerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order>findAllByClient(Client client);
}
