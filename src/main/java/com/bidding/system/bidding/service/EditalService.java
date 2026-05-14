/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.service;

import com.bidding.system.bidding.model.EditalDTO;
import com.bidding.system.bidding.model.UserDTO;
import com.bidding.system.bidding.repository.EditalRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Usuario
 */
@Service
public class EditalService {
    
    @Autowired
    private EditalRepository repository;
    
    @Autowired
    private TokenService tokenService;
    
    public void criarEdital(EditalDTO novo, String token) {
        UserDTO userLogado = tokenService.extrairClaim(token);
        
        if (userLogado.getRole().equals("COMPRADOR")) {
            String mensagem = "";
            if (novo.getTitulo().equals("")) {
                mensagem += "Titulo não preenchido!\n";
            }
            if (novo.getDescricao().equals("")) {
                mensagem += "Descrição não preenchida!\n";
            }
            if (novo.getDataFechamento() == null) {
                mensagem += "Data não preenchida!\n";
            }
            
            if (!mensagem.equals("")) {
                throw new ResponseStatusException(HttpStatusCode.valueOf(400), mensagem);
            }
            
            novo.setStatus("ABERTO");
            int linhas = repository.criar(novo);
            if(linhas == 0) {
                throw new ResponseStatusException(HttpStatusCode.valueOf(500), 
                "Erro ao cadastrar no banco de dados");
            }
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), 
                    "Acesso não autorizado!");
        }
        
    }
    
    public List<EditalDTO> listarTodos(String token){
        UserDTO userlogado = tokenService.extrairClaim(token);
        
        return repository.listarTodos();
        
        
    }
}
