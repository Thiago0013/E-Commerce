package com.example.commerce.controller;

import com.example.commerce.dto.OrderDTO;
import com.example.commerce.service.OrderService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity addOrder(@RequestBody OrderDTO dto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(orderService.addOrder(dto, auth.getName()));
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity finish(@PathVariable Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(orderService.finish(auth.getName(), id));
    }

    @GetMapping("/meus-pedidos")
    public ResponseEntity getOrders(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(orderService.getOrders(auth.getName()));
    }
}
