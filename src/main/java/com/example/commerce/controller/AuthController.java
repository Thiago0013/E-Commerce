package com.example.commerce.controller;


import com.example.commerce.dto.ClientDTO;
import com.example.commerce.dto.LoginDTO;
import com.example.commerce.service.ClientService;
import com.example.commerce.model.Client;
import com.example.commerce.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    final AuthenticationManager authManager;
    final TokenService tokenService;
    final ClientService clientService;


    public AuthController(AuthenticationManager authManager, TokenService tokenService, ClientService clientService){
        this.authManager = authManager;
        this.tokenService = tokenService;
        this.clientService = clientService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO dto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = this.authManager.authenticate(usernamePassword);
        var token = this.tokenService.generateToken((Client) auth.getPrincipal());

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<Client> register(@RequestBody ClientDTO dto){
        Client newClient = clientService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
    }
}
