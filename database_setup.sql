-- ========================================
-- SCRIPT DE CONFIGURAÇÃO DO BANCO DE DADOS
-- Sistema CRUD Java Desktop
-- ========================================

-- Criar banco de dados se não existir
CREATE DATABASE IF NOT EXISTS sistema_crud
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Usar o banco de dados criado
USE sistema_crud;

-- Criar tabela produtos
CREATE TABLE IF NOT EXISTS produtos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10,2) NOT NULL CHECK (preco >= 0),
    quantidade INT NOT NULL CHECK (quantidade >= 0),
    categoria VARCHAR(50) NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_nome (nome),
    INDEX idx_categoria (categoria)
);

-- Inserir dados de exemplo (opcional)
INSERT INTO produtos (nome, descricao, preco, quantidade, categoria) VALUES
('Notebook Dell', 'Notebook Dell Inspiron 15 3000, Intel Core i5, 8GB RAM, 256GB SSD', 2499.99, 10, 'Informática'),
('Mouse Logitech', 'Mouse sem fio Logitech MX Master 3, sensor 4000 DPI', 299.90, 25, 'Periféricos'),
('Teclado Mecânico', 'Teclado mecânico RGB, switches blue, ABNT2', 189.90, 15, 'Periféricos'),
('Monitor Samsung', 'Monitor Samsung 24" Full HD, IPS, 75Hz', 799.99, 8, 'Monitores'),
('Smartphone Xiaomi', 'Xiaomi Redmi Note 12, 128GB, 6GB RAM, Câmera 50MP', 1299.99, 20, 'Celulares'),
('Headset Gamer', 'Headset Gamer HyperX Cloud II, 7.1 Surround, USB', 399.90, 12, 'Áudio'),
('SSD Kingston', 'SSD Kingston NV2 500GB, M.2 NVMe, Leitura 3.500 MB/s', 179.90, 30, 'Armazenamento'),
('Placa de Vídeo', 'Placa de Vídeo GTX 1660 Super, 6GB GDDR6', 1899.99, 5, 'Hardware'),
('Fonte Corsair', 'Fonte Corsair 650W, 80 Plus Bronze, Modular', 459.90, 18, 'Hardware'),
('Gabinete Gamer', 'Gabinete Gamer RGB, Vidro Temperado, 4 Coolers', 299.90, 7, 'Hardware');

-- Criar usuário específico para a aplicação (opcional - mais seguro)
-- CREATE USER IF NOT EXISTS 'sistema_crud'@'localhost' IDENTIFIED BY 'senha123';
-- GRANT SELECT, INSERT, UPDATE, DELETE ON sistema_crud.* TO 'sistema_crud'@'localhost';
-- FLUSH PRIVILEGES;

-- Verificar se a tabela foi criada corretamente
DESCRIBE produtos;

-- Verificar dados inseridos
SELECT COUNT(*) as total_produtos FROM produtos;

-- Mostrar categorias disponíveis
SELECT DISTINCT categoria FROM produtos ORDER BY categoria;

-- Estatísticas básicas
SELECT 
    categoria,
    COUNT(*) as quantidade_produtos,
    AVG(preco) as preco_medio,
    SUM(quantidade) as estoque_total
FROM produtos 
GROUP BY categoria
ORDER BY categoria;

-- ========================================
-- COMANDOS ÚTEIS PARA MANUTENÇÃO
-- ========================================

-- Backup da tabela
-- mysqldump -u root -p sistema_crud produtos > backup_produtos.sql

-- Restaurar backup
-- mysql -u root -p sistema_crud < backup_produtos.sql

-- Verificar tamanho da tabela
-- SELECT 
--     table_name AS 'Tabela',
--     round(data_length/1024/1024, 2) AS 'Tamanho (MB)'
-- FROM information_schema.tables 
-- WHERE table_schema = 'sistema_crud';

-- Otimizar tabela
-- OPTIMIZE TABLE produtos;

-- Verificar integridade
-- CHECK TABLE produtos;

-- ========================================
-- CONSULTAS DE EXEMPLO
-- ========================================

-- Produtos mais caros
-- SELECT nome, preco FROM produtos ORDER BY preco DESC LIMIT 5;

-- Produtos com estoque baixo (menos de 10 unidades)
-- SELECT nome, quantidade FROM produtos WHERE quantidade < 10 ORDER BY quantidade;

-- Buscar produtos por categoria
-- SELECT * FROM produtos WHERE categoria = 'Informática';

-- Produtos por faixa de preço
-- SELECT nome, preco FROM produtos WHERE preco BETWEEN 100 AND 500;

-- Buscar produtos por nome (busca parcial)
-- SELECT * FROM produtos WHERE nome LIKE '%samsung%';

-- Total de produtos por categoria
-- SELECT categoria, COUNT(*) as total FROM produtos GROUP BY categoria;

-- Valor total do estoque
-- SELECT SUM(preco * quantidade) as valor_total_estoque FROM produtos;