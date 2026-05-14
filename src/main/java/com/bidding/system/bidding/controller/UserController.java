/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

// Define o pacote onde está localizada esta classe
package com.bidding.system.bidding.controller;


// Importa as classes necessárias para o funcionamento do controller
import com.bidding.system.bidding.model.UserDTO;
import com.bidding.system.bidding.model.UserRequestDTO;
import com.bidding.system.bidding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */

// Indica que esta classe é um controller REST
@RestController
// Define o caminho base para as rotas deste controller
@RequestMapping("/api/auth")
public class UserController {
    // Injeta automaticamente uma instância de UserService
    @Autowired
    private UserService service;

    // Mapeia requisições POST para /api/auth/registrar
    @PostMapping("/registrar")
    public String registrar(@RequestBody UserDTO user) {
        // Chama o método de registro do serviço
        service.register(user);
        // Retorna mensagem de sucesso
        return "Cadastro Feito com sucesso!";
    }

    // Mapeia requisições POST para /api/auth/logar
    @PostMapping("/logar")
    public String login(@RequestBody UserRequestDTO user) {
        // Chama o método de login do serviço e retorna o resultado
        return service.logar(user);
    }
}
