/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

// Define o pacote onde está localizada esta classe
package com.bidding.system.bidding.repository;


// Importa as classes necessárias para conexão com o banco de dados
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Usuario
 */

// Classe responsável por gerenciar a conexão com o banco de dados
public class Conexao {
    // URL de conexão com o banco de dados MySQL
    private static final String url = "jdbc:mysql://localhost:3307/db_bidding_system";
    // Usuário do banco de dados
    private static final String user = "root";
    // Senha do banco de dados
    private static final String senha = "";
    // Objeto de conexão reutilizável
    private static Connection conn = null;

    // Construtor padrão
    public Conexao () {}

    // Método para obter uma conexão com o banco de dados de forma sincronizada
    public static synchronized Connection conectar() {
        try {
            // Se a conexão for nula ou estiver fechada, cria uma nova conexão
            if(conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(url, user, senha);
            }
        } catch(SQLException e ) {
            // Em caso de erro, imprime o stack trace
            e.printStackTrace();
        }
        // Retorna o objeto de conexão
        return conn;
    }
}
