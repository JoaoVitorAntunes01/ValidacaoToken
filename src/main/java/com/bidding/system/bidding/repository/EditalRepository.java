/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

// Define o pacote onde está localizada esta classe
package com.bidding.system.bidding.repository;


// Importa as classes necessárias para manipulação de editais e banco de dados
import com.bidding.system.bidding.model.EditalDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Usuario
 */

// Indica que esta classe é um repositório do Spring
@Repository
public class EditalRepository {

    // Método para criar um novo edital no banco de dados
    public int criar(EditalDTO novo) {
        try {
            // Obtém uma conexão com o banco de dados
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            // Prepara o comando SQL para inserir um novo edital
            stmt = conn.prepareStatement("INSERT INTO editais (titulo, descricao, data_fechamento, status)"
                    + "VALUES (?, ?, ?, ?)");
            // Define os parâmetros do SQL com os dados do edital
            stmt.setString(1, novo.getTitulo());
            stmt.setString(2, novo.getDescricao());
            stmt.setDate(3, novo.getDataFechamento());
            stmt.setString(4, novo.getStatus());

            // Executa o comando de inserção e retorna o número de linhas afetadas
            return stmt.executeUpdate();
        } catch(SQLException e ) {
            // Em caso de erro, imprime o stack trace
            e.printStackTrace();
        }
        // Retorna 0 caso não consiga inserir
        return 0;
    }
    
    public List<EditalDTO> listarTodos(){
        
        List<EditalDTO> lista = new ArrayList<>();
        
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            
            stmt = conn.prepareStatement("Select * from editais");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                EditalDTO edital = new EditalDTO();
                edital.setTitulo(rs.getString("Titulo"));
                edital.setDescricao(rs.getString("Descrição"));
                edital.setStatus(rs.getString("Status"));
                edital.setDataFechamento(rs.getDate("Data de Fechamento"));
                
                lista.add(edital);
            }
                    
        } catch(SQLException e){
            e.printStackTrace();
        }
        return lista;
    }
}
