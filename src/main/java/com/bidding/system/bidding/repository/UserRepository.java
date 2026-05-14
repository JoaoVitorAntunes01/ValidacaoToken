/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Define o pacote onde está localizada esta classe
package com.bidding.system.bidding.repository;

// Importa as classes necessárias para manipulação de usuários e banco de dados
import com.bidding.system.bidding.model.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Usuario
 */
@Repository
public class UserRepository {

    // Método para registrar um novo usuário no banco de dados
    public void register(UserDTO user) {
        try {
            // Obtém uma conexão com o banco de dados
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            // Prepara o comando SQL para inserir um novo usuário
            stmt = conn.prepareStatement("Insert into usuarios (nome, email, senha, role) values (?,?,?,?)");

            // Define os parâmetros do SQL com os dados do usuário
            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getSenha());
            stmt.setString(4, user.getRole());

            // Executa o comando de inserção
            int linhasAfetadas = stmt.executeUpdate();
            // Verifica se alguma linha foi afetada (inserida)
            if (linhasAfetadas == 0) {
                throw new SQLException("Falha na atualização - Nenhuma linha foi afetada");
            }
        } catch (SQLException e) {
            // Em caso de erro, imprime o stack trace
            e.printStackTrace();
        }
    }

    // Método para autenticar um usuário pelo email e senha
    public UserDTO logar(String email, String senha) {
        // Cria um objeto UserDTO para armazenar os dados do usuário
        UserDTO user = new UserDTO();

        try {
            // Obtém uma conexão com o banco de dados
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            // Prepara o comando SQL para buscar o usuário
            stmt = conn.prepareStatement("select * from usuarios where email = ? and senha = ?");

            // Define os parâmetros do SQL
            stmt.setString(1, email);
            stmt.setString(2, senha);

            // Executa a consulta
            rs = stmt.executeQuery();

            // Se encontrou um usuário, preenche o objeto UserDTO
            if (rs.next()) {
                user.setEmail(rs.getString("email"));
                user.setId(rs.getLong("id"));
                user.setNome(rs.getString("nome"));
                user.setRole(rs.getString("role"));
            }

        } catch (SQLException e) {
            // Em caso de erro, imprime o stack trace
            e.printStackTrace();

        }
        // Retorna o usuário encontrado (ou vazio)
        return user;
    }
}
