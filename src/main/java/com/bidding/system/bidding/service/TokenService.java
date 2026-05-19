/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

// Define o pacote onde está localizada esta classe
package com.bidding.system.bidding.service;


// Importa as classes necessárias para manipulação de tokens JWT e usuários
import com.bidding.system.bidding.model.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;



// Indica que esta classe é um serviço do Spring
@Service
public class TokenService {
    // Injeta o valor do segredo do token a partir do application.properties
    @Value("${api.security.token.secret}")
    private String secret;

    // Método para obter a chave secreta para assinar o token
    public SecretKey getKeySign() {
        // Decodifica o segredo em base64 e retorna a chave
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Método para gerar um token JWT para o usuário
    public String gerarToken(UserDTO user) {
        // Verifica se algum campo obrigatório está faltando
        if(
            user.getId() == 0 || user.getId() == null ||
            user.getNome().equals("") ||
            user.getEmail().equals("") ||
            user.getRole().equals("")
        ) {
            // Lança exceção se faltar algum campo
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), 
            "Um ou mais campos faltantes");
        }

        // Cria e retorna o token JWT
        return Jwts.builder()
                .subject(user.getNome()) // Define o nome como subject
                .claim("id", user.getId())
                .claim("nome", user.getNome())
                .claim("role", user.getRole())
                .issuedAt(new Date()) // Data de emissão
                .expiration(new Date(System.currentTimeMillis() + 3000000)) // Expiração em ~50 minutos
                .signWith(this.getKeySign()) // Assina com a chave secreta
                .compact();
    }

    // Método para extrair o usuário do token JWT
    public UserDTO extrairClaim(String token) {
        // Faz o parsing do token e obtém os claims
        Claims claims = Jwts.parser()
                .verifyWith(this.getKeySign())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        // Recupera o usuário do claim "usuario"
        UserDTO user = new UserDTO();
        user.setId(claims.get("id", Long.class));
        user.setNome(claims.get("nome", String.class));
        user.setRole(claims.get("role", String.class));
        return user;
    }
    
       public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(getKeySign())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
}
    

