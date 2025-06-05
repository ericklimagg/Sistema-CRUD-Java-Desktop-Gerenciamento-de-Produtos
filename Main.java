import database.DatabaseConnection;
import view.MainFrame;

import javax.swing.*;

/**
 * Classe principal da aplicação
 * Responsável por inicializar o sistema
 */
public class Main {
    public static void main(String[] args) {
        // Configurar Look and Feel do sistema operacional
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Erro ao definir Look and Feel: " + e.getMessage());
        }
        
        // Inicializar banco de dados
        System.out.println("Inicializando banco de dados...");
        DatabaseConnection.initializeDatabase();
        
        // Executar interface gráfica na thread do Swing
        SwingUtilities.invokeLater(() -> {
            try {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
                System.out.println("Sistema CRUD inicializado com sucesso!");
            } catch (Exception e) {
                System.err.println("Erro ao inicializar interface: " + e.getMessage());
                JOptionPane.showMessageDialog(
                    null,
                    "Erro ao conectar com o banco de dados!\nVerifique se o MySQL está rodando e as configurações de conexão.",
                    "Erro de Conexão",
                    JOptionPane.ERROR_MESSAGE
                );
                System.exit(1);
            }
        });
    }
}
