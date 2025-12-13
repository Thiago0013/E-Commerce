package com.example.commerce.dto;

import java.util.List;

public record OrderDTO(
        List<OrderItemDTO> itens
) {}