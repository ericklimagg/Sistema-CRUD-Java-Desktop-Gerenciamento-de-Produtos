package view;

import dao.ProdutoDAO;
import model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.List;

/**
 * Classe principal da interface gráfica
 * Implementa padrão MVC - View
 */
public class MainFrame extends JFrame {
    private ProdutoDAO produtoDAO;
    private JTable tabelaProdutos;
    private DefaultTableModel modeloTabela;
    private JTextField txtBusca;
    
    // Componentes do formulário
    private JTextField txtNome;
    private JTextArea txtDescricao;
    private JTextField txtPreco;
    private JTextField txtQuantidade;
    private JComboBox<String> cbCategoria;
    
    private Produto produtoSelecionado;
    
    public MainFrame() {
        this.produtoDAO = new ProdutoDAO();
        initComponents();
        carregarProdutos();
    }
    
    private void initComponents() {
        setTitle("Sistema CRUD - Gerenciamento de Produtos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Painel superior - Busca
        JPanel painelBusca = createPainelBusca();
        add(painelBusca, BorderLayout.NORTH);
        
        // Painel central - Tabela
        JScrollPane scrollTable = createTabelaProdutos();
        add(scrollTable, BorderLayout.CENTER);
        
        // Painel direito - Formulário
        JPanel painelFormulario = createPainelFormulario();
        add(painelFormulario, BorderLayout.EAST);
        
        // Painel inferior - Botões
        JPanel painelBotoes = createPainelBotoes();
        add(painelBotoes, BorderLayout.SOUTH);
        
        // Configurações da janela
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 500));
    }
    
    private JPanel createPainelBusca() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Busca"));
        
        panel.add(new JLabel("Buscar por nome:"));
        txtBusca = new JTextField(20);
        panel.add(txtBusca);
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarProdutos());
        panel.add(btnBuscar);
        
        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> {
            txtBusca.setText("");
            carregarProdutos();
        });
        panel.add(btnLimpar);
        
        return panel;
    }
    
    private JScrollPane createTabelaProdutos() {
        String[] colunas = {"ID", "Nome", "Descrição", "Preço", "Quantidade", "Categoria"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna a tabela não editável
            }
        };
        
        tabelaProdutos = new JTable(modeloTabela);
        tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaProdutos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selecionarProduto();
            }
        });
        
        // Configurar largura das colunas
        tabelaProdutos.getColumnModel().getColumn(0).setMaxWidth(50);
        tabelaProdutos.getColumnModel().getColumn(3).setMaxWidth(100);
        tabelaProdutos.getColumnModel().getColumn(4).setMaxWidth(100);
        
        return new JScrollPane(tabelaProdutos);
    }
    
    private JPanel createPainelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Dados do Produto"));
        panel.setPreferredSize(new Dimension(300, 0));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtNome = new JTextField(20);
        panel.add(txtNome, gbc);
        
        // Descrição
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH;
        txtDescricao = new JTextArea(3, 20);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        JScrollPane scrollDesc = new JScrollPane(txtDescricao);
        panel.add(scrollDesc, gbc);
        
        // Preço
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Preço (R$):"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtPreco = new JTextField(20);
        panel.add(txtPreco, gbc);
        
        // Quantidade
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Quantidade:"), gbc);
        gbc.gridx = 1;
        txtQuantidade = new JTextField(20);
        panel.add(txtQuantidade, gbc);
        
        // Categoria
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Categoria:"), gbc);
        gbc.gridx = 1;
        cbCategoria = new JComboBox<>();
        cbCategoria.setEditable(true);
        carregarCategorias();
        panel.add(cbCategoria, gbc);
        
        return panel;
    }
    
    private JPanel createPainelBotoes() {
        JPanel panel = new JPanel(new FlowLayout());
        
        JButton btnNovo = new JButton("Novo");
        btnNovo.addActionListener(e -> limparFormulario());
        panel.add(btnNovo);
        
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarProduto());
        panel.add(btnSalvar);
        
        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(e -> atualizarProduto());
        panel.add(btnAtualizar);
        
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> excluirProduto());
        panel.add(btnExcluir);
        
        return panel;
    }
    
    private void carregarProdutos() {
        modeloTabela.setRowCount(0);
        List<Produto> produtos = produtoDAO.listarTodos();
        
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        
        for (Produto produto : produtos) {
            Object[] row = {
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                currency.format(produto.getPreco()),
                produto.getQuantidade(),
                produto.getCategoria()
            };
            modeloTabela.addRow(row);
        }
    }
    
    private void buscarProdutos() {
        String termo = txtBusca.getText().trim();
        if (termo.isEmpty()) {
            carregarProdutos();
            return;
        }
        
        modeloTabela.setRowCount(0);
        List<Produto> produtos = produtoDAO.buscarPorNome(termo);
        
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        
        for (Produto produto : produtos) {
            Object[] row = {
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                currency.format(produto.getPreco()),
                produto.getQuantidade(),
                produto.getCategoria()
            };
            modeloTabela.addRow(row);
        }
    }
    
    private void selecionarProduto() {
        int selectedRow = tabelaProdutos.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (Integer) modeloTabela.getValueAt(selectedRow, 0);
            produtoSelecionado = produtoDAO.buscarPorId(id);
            
            if (produtoSelecionado != null) {
                preencherFormulario(produtoSelecionado);
            }
        }
    }
    
    private void preencherFormulario(Produto produto) {
        txtNome.setText(produto.getNome());
        txtDescricao.setText(produto.getDescricao());
        txtPreco.setText(String.valueOf(produto.getPreco()));
        txtQuantidade.setText(String.valueOf(produto.getQuantidade()));
        cbCategoria.setSelectedItem(produto.getCategoria());
    }
    
    private void limparFormulario() {
        txtNome.setText("");
        txtDescricao.setText("");
        txtPreco.setText("");
        txtQuantidade.setText("");
        cbCategoria.setSelectedIndex(-1);
        produtoSelecionado = null;
        tabelaProdutos.clearSelection();
    }
    
    private void salvarProduto() {
        if (!validarFormulario()) return;
        
        try {
            Produto produto = new Produto();
            produto.setNome(txtNome.getText().trim());
            produto.setDescricao(txtDescricao.getText().trim());
            produto.setPreco(Double.parseDouble(txtPreco.getText().replace(",", ".")));
            produto.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
            produto.setCategoria(cbCategoria.getSelectedItem().toString());
            
            if (produtoDAO.inserir(produto)) {
                JOptionPane.showMessageDialog(this, "Produto salvo com sucesso!");
                carregarProdutos();
                carregarCategorias();
                limparFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar produto!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valores numéricos inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void atualizarProduto() {
        if (produtoSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para atualizar!");
            return;
        }
        
        if (!validarFormulario()) return;
        
        try {
            produtoSelecionado.setNome(txtNome.getText().trim());
            produtoSelecionado.setDescricao(txtDescricao.getText().trim());
            produtoSelecionado.setPreco(Double.parseDouble(txtPreco.getText().replace(",", ".")));
            produtoSelecionado.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
            produtoSelecionado.setCategoria(cbCategoria.getSelectedItem().toString());
            
            if (produtoDAO.atualizar(produtoSelecionado)) {
                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
                carregarProdutos();
                carregarCategorias();
                limparFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar produto!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valores numéricos inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluirProduto() {
        if (produtoSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para excluir!");
            return;
        }
        
        int opcao = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente excluir o produto '" + produtoSelecionado.getNome() + "'?",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION
        );
        
        if (opcao == JOptionPane.YES_OPTION) {
            if (produtoDAO.excluir(produtoSelecionado.getId())) {
                JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!");
                carregarProdutos();
                carregarCategorias();
                limparFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir produto!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private boolean validarFormulario() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome é obrigatório!");
            txtNome.requestFocus();
            return false;
        }
        
        if (txtPreco.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preço é obrigatório!");
            txtPreco.requestFocus();
            return false;
        }
        
        if (txtQuantidade.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Quantidade é obrigatória!");
            txtQuantidade.requestFocus();
            return false;
        }
        
        if (cbCategoria.getSelectedItem() == null || cbCategoria.getSelectedItem().toString().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Categoria é obrigatória!");
            cbCategoria.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void carregarCategorias() {
        cbCategoria.removeAllItems();
        List<String> categorias = produtoDAO.listarCategorias();
        for (String categoria : categorias) {
            cbCategoria.addItem(categoria);
        }
        cbCategoria.setSelectedIndex(-1);
    }
}