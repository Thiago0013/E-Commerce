package com.example.commerce.dto;

import jakarta.validation.constraints.NotBlank;

public record ClientDTO(@NotBlank String nome,
                        @NotBlank String sobrenome,
                        @NotBlank String cpf,
                        @NotBlank String email,
                        @NotBlank String password,
                        String endereco,
                        String telefone) {
}
