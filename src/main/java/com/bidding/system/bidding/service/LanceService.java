/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Aluno
 */
@Service
public class LanceService {
    
    @Autowired
    private TokenService tokenService;
    
    public void criarLance(Long id, LanceDTO lance, String token) {
        UserDTO userLogado = tokenService.extrairClaim(token)
    }
    
}
