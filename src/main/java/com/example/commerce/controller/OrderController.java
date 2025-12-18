package com.example.commerce.controller;

import com.example.commerce.dto.OrderDTO;
import com.example.commerce.model.Order;
import com.example.commerce.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class OrderController {
    final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public Order addOrder(@RequestBody OrderDTO dto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return orderService.addOrder(dto, auth.getName());
    }

    @GetMapping("/meus-pedidos")
    public List<Order> getOrders(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return orderService.getOrders(auth.getName());

    }
}
