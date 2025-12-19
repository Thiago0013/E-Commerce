package com.example.commerce.service;

import com.example.commerce.dto.OrderDTO;
import com.example.commerce.dto.OrderItemDTO;
import com.example.commerce.enums.OrderStatus;
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

    public Order addOrder(OrderDTO dto, String email){

        Client client = (Client) clientRepository.findByEmail(email);

        Order order = new Order();
        order.setClient(client);
        order.setDataPedido(LocalDateTime.now());
        order.setItens(new ArrayList<>());
        order.setStatus(OrderStatus.AGUARDANDO_PAGAMENTO);

        BigDecimal total = BigDecimal.ZERO;
        for(OrderItemDTO itemDTO : dto.itens()){
            Product produto = productRepository.findById(itemDTO.produtoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado ID: " + itemDTO.produtoId()));

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

    @Transactional
    public Order finish(String email, Long id){

        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado."));

        if(!order.getClient().getEmail().equals(email)){
            throw new RuntimeException("Este pedido não pertence a você!");
        }
        if(order.getStatus() != OrderStatus.AGUARDANDO_PAGAMENTO){
            throw new RuntimeException("Erro: Este produto não pode ser finalizado!");
        }

        for(OrderItem item : order.getItens()) {
            Product product = item.getProduto();

            if (product.getQuantidadeEstoque() < item.getQuantidade()) {
                throw new RuntimeException("Erro: Quantidade em estoque insuficiente.");
            }

            product.setQuantidadeEstoque(product.getQuantidadeEstoque() - item.getQuantidade());
            productRepository.save(product);
        }
        order.setStatus(OrderStatus.FINALIZADO);
        return orderRepository.save(order);
    }
}
