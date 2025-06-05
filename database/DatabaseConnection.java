package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe responsável pela conexão com o banco de dados
 * Implementa padrão Singleton para garantir uma única instância
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_crud?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Altere conforme sua configuração
    
    private static DatabaseConnection instance;
    private Connection connection;
    
    private DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }
    
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter conexão: " + e.getMessage());
        }
        return connection;
    }
    
    /**
     * Método para criar o banco de dados e tabela se não existirem
     */
    public static void initializeDatabase() {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver"); // Carrega driver explicitamente
        
        // Conecta sem especificar o banco, mas com parâmetros para evitar erros
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=UTC", "root", "");
        Statement stmt = conn.createStatement();
        
        // Cria o banco se não existir
        stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS sistema_crud");
        conn.close();
        
        // Conecta ao banco criado
        DatabaseConnection dbConn = getInstance();
        Connection connection = dbConn.getConnection();
        Statement statement = connection.createStatement();
        
        // Cria a tabela produtos se não existir
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS produtos (
                id INT AUTO_INCREMENT PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                descricao TEXT,
                preco DECIMAL(10,2) NOT NULL,
                quantidade INT NOT NULL,
                categoria VARCHAR(50) NOT NULL
            )
        """;
        
        statement.executeUpdate(createTableSQL);
        statement.close();
        
        System.out.println("Banco de dados inicializado com sucesso!");
        
    } catch (ClassNotFoundException e) {
        System.err.println("Driver não encontrado: " + e.getMessage());
    } catch (SQLException e) {
        System.err.println("Erro ao inicializar banco de dados: " + e.getMessage());
    }
}
    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}