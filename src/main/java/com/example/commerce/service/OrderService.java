package com.example.commerce.service;

import com.example.commerce.dto.OrderDTO;
import com.example.commerce.dto.OrderItemDTO;
import com.example.commerce.model.Client;
import com.example.commerce.model.Order;
import com.example.commerce.model.OrderItem;
import com.example.commerce.model.Product;
import com.example.commerce.repository.ClientRepository;
import com.example.commerce.repository.OrderRepository;
import com.example.commerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    final ProductRepository productRepository;
    final ClientRepository clientRepository;
    final OrderRepository orderRepository;

    public OrderService(ProductRepository productRepository, ClientRepository clientRepository, OrderRepository orderRepository){
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order addOrder(OrderDTO dto, String email){

        Client client = (Client) clientRepository.findByEmail(email);

        Order order = new Order();
        order.setClient(client);
        order.setDataPedido(LocalDateTime.now());
        order.setItens(new ArrayList<>());

        BigDecimal total = BigDecimal.ZERO;
        for(OrderItemDTO itemDTO : dto.itens()){
            Product produto = productRepository.findById(itemDTO.produtoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado ID: " + itemDTO.produtoId()));

            if(itemDTO.quantidade() > produto.getQuantidadeEstoque()){
                throw new RuntimeException("Estoque insuficiente para o produto de ID:" + itemDTO.produtoId());
            }

            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - itemDTO.quantidade());
            System.out.println(produto.getQuantidadeEstoque());
            productRepository.save(produto);

            OrderItem orderItem = new OrderItem();
            orderItem.setPedido(order);
            orderItem.setProduto(produto);
            orderItem.setQuantidade(itemDTO.quantidade());
            orderItem.setPrecoUnitario(produto.getPreco());

            BigDecimal quantidade = BigDecimal.valueOf(orderItem.getQuantidade());
            BigDecimal valorUni = orderItem.getPrecoUnitario();
            BigDecimal multi = quantidade.multiply(valorUni);
            total = total.add(multi);

            order.setValorTotal(total);

            order.getItens().add(orderItem);
        }
        return orderRepository.save(order);
    }

    public List<Order> getOrders(String email){
        Client client = (Client) clientRepository.findByEmail(email);

        return orderRepository.findAllByClient(client);
    }
}
