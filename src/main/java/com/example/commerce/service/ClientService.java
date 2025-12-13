package com.example.commerce.service;

import com.example.commerce.dto.ClientDTO;
import com.example.commerce.model.Client;
import com.example.commerce.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public Client register(ClientDTO dto){
        Client newCliente = Client.builder()
                .nome(dto.nome())
                .cpf(dto.cpf())
                .email(dto.email())
                .endereco(dto.endereco())
                .telefone(dto.telefone())
                .build();

        return clientRepository.save(newCliente);
    }
}
