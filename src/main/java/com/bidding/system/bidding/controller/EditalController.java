/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

// Define o pacote onde está localizada esta classe
package com.bidding.system.bidding.controller;


// Importa as classes necessárias para o controller
import com.bidding.system.bidding.model.EditalDTO;
import com.bidding.system.bidding.model.UserDTO;
import com.bidding.system.bidding.service.EditalService;
import com.bidding.system.bidding.service.TokenService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */

// Indica que esta classe é um controller REST
@RestController
// Define o caminho base para as rotas deste controller
@RequestMapping("/api/editais")
public class EditalController {
    // Injeta automaticamente uma instância de EditalService
    @Autowired
    private EditalService service;

    // Mapeia requisições POST para /api/editais
    @PostMapping
    public String cadastrarEdital(
            // Recebe o header Authorization com o token JWT
            @RequestHeader("Authorization") String auth,
            // Recebe o corpo da requisição com os dados do edital
            @RequestBody EditalDTO edital
    ) {
        // Remove o prefixo "Bearer " do token
        String token = auth.replace("Bearer ", "");
        // Chama o serviço para criar o edital
        service.criarEdital(edital, token);
        // Retorna mensagem de sucesso
        return "Edital Cadastrado com sucesso!";
    }
    
    @GetMapping
    public List<EditalDTO> listarTodos
            @RequestHeader("Authorization") String auth) {
    
    String token = auth.replace("Bearer", "");
    
    List<EditalDTO> lista = service.listarEditais(token);
    return lista;
    }
            
    @PostMapping("{id}/lances")
    public String registrarLance(
            @RequestHeader("Authorization") String auth,
            @RequestBody LanceDTO lance,
            @PathVariable Long id
    ) {
        String token = auth.replace("Bearer ", "");
        service.criarLance(id, lance, token);
        return "Lance Registrado com sucesso!";
    }
}

