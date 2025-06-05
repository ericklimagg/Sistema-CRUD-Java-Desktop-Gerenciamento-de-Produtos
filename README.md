# Sistema CRUD Java Desktop - Gerenciamento de Produtos

## Descrição do Projeto

Este projeto consiste em um sistema CRUD (Create, Read, Update, Delete) desenvolvido em Java para ambiente desktop, focado no gerenciamento de produtos. A aplicação implementa os princípios da programação orientada a objetos e utiliza banco de dados MySQL para persistência de dados.

## Objetivos

- Desenvolver um sistema completo de cadastro, leitura, atualização e exclusão de produtos
- Aplicar conceitos de POO (Programação Orientada a Objetos)
- Implementar integração com banco de dados relacional
- Criar interface gráfica intuitiva e funcional
- Garantir segurança e integridade dos dados

## Funcionalidades

### Operações CRUD
- **Create**: Cadastro de novos produtos
- **Read**: Listagem e busca de produtos
- **Update**: Atualização de dados de produtos existentes
- **Delete**: Exclusão de produtos do sistema

### Recursos Adicionais
- Interface gráfica amigável com Swing
- Sistema de busca por nome do produto
- Validação de dados de entrada
- Formatação automática de valores monetários
- Gerenciamento de categorias dinâmico
- Confirmação para operações de exclusão

### Padrões Implementados
- **MVC (Model-View-Controller)**: Separação de responsabilidades
- **DAO (Data Access Object)**: Abstração do acesso a dados
- **Singleton**: Gerenciamento de conexão única com banco
- **Factory Method**: Criação de objetos Produto

## Pré-requisitos

### Software Necessário
- **Java JDK 8 ou superior**
- **MySQL Server 5.7 ou superior**
- **IDE Java** (IntelliJ IDEA, Eclipse, NetBeans, etc.)
- **MySQL Connector/J** (driver JDBC)

### Dependências
- MySQL Connector/J 8.0.33 ou superior

## Instalação e Configuração

### 1. Preparação do Ambiente

#### Instalação do MySQL
1. Baixe e instale o MySQL Server
2. Configure o usuário root com senha (ou mantenha sem senha)
3. Inicie o serviço MySQL

#### Download do MySQL Connector/J
1. Acesse: https://dev.mysql.com/downloads/connector/j/
2. Baixe o arquivo JAR
3. Adicione ao classpath do projeto

### 2. Configuração do Banco de Dados

O sistema cria automaticamente o banco e tabelas na primeira execução. Caso prefira criar manualmente:

```sql
-- Criar banco de dados
CREATE DATABASE sistema_crud;

-- Usar o banco
USE sistema_crud;

-- Criar tabela produtos
CREATE TABLE produtos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10,2) NOT NULL,
    quantidade INT NOT NULL,
    categoria VARCHAR(50) NOT NULL
);
```

### 3. Configuração da Conexão

Edite o arquivo `DatabaseConnection.java` se necessário:

```java
private static final String URL = "jdbc:mysql://localhost:3306/sistema_crud";
private static final String USER = "root";
private static final String PASSWORD = "sua_senha_aqui";
```

### 4. Compilação e Execução

#### Via IDE
1. Importe o projeto na sua IDE
2. Adicione o MySQL Connector/J ao classpath
3. Execute a classe `Main.java`

#### Via Linha de Comando
```bash
# Compilar
javac -cp ".:mysql-connector-java-8.0.33.jar" *.java **/*.java

# Executar
java -cp ".:mysql-connector-java-8.0.33.jar" Main
```

## Manual de Uso

### Tela Principal
A interface principal é dividida em quatro seções:

1. **Painel de Busca** (superior): Campo para pesquisar produtos por nome
2. **Tabela de Produtos** (centro): Lista todos os produtos cadastrados
3. **Formulário** (direita): Campos para inserir/editar dados do produto
4. **Botões de Ação** (inferior): Operações disponíveis

### Cadastrar Produto
1. Clique em "Novo" para limpar o formulário
2. Preencha todos os campos obrigatórios:
   - Nome do produto
   - Descrição (opcional)
   - Preço (formato: 99.99)
   - Quantidade (número inteiro)
   - Categoria
3. Clique em "Salvar"

### Buscar Produtos
1. Digite o nome (ou parte do nome) no campo de busca
2. Clique em "Buscar"
3. Para ver todos os produtos novamente, clique em "Limpar"

### Atualizar Produto
1. Selecione um produto na tabela
2. Os dados aparecerão no formulário
3. Modifique os campos desejados
4. Clique em "Atualizar"

### Excluir Produto
1. Selecione um produto na tabela
2. Clique em "Excluir"
3. Confirme a exclusão na caixa de diálogo

## Estrutura do Código

### Classe Produto (Model)
Representa a entidade produto com:
- Atributos: id, nome, descrição, preço, quantidade, categoria
- Construtores e métodos getter/setter
- Método toString() para depuração

### Classe ProdutoDAO
Implementa operações de acesso a dados:
- `inserir()`: Adiciona novo produto
- `listarTodos()`: Retorna todos os produtos
- `buscarPorId()`: Busca produto específico
- `buscarPorNome()`: Busca por nome
- `atualizar()`: Modifica produto existente
- `excluir()`: Remove produto
- `listarCategorias()`: Retorna categorias únicas

### Classe DatabaseConnection
Gerencia conexão com banco:
- Padrão Singleton para conexão única
- Método de inicialização automática do banco
- Tratamento de erros de conexão

### Classe MainFrame
Interface gráfica principal:
- Layout organizado com BorderLayout
- Tabela com seleção única
- Formulário com validação
- Eventos de botões e seleção

## Tratamento de Erros

O sistema implementa tratamento robusto de erros:

### Erros de Banco de Dados
- Conexão indisponível
- Queries malformadas
- Violação de constraints

### Erros de Interface
- Validação de campos obrigatórios
- Formato de números inválidos
- Seleção inexistente

### Mensagens de Feedback
- Confirmações de sucesso
- Alertas de erro específicos
- Validações em tempo real

## Tecnologias Utilizadas

### Core
- **Java SE 8+**: Linguagem principal
- **Swing**: Interface gráfica
- **JDBC**: Conectividade com banco de dados

### Banco de Dados
- **MySQL**: Sistema de gerenciamento de banco
- **MySQL Connector/J**: Driver JDBC

### Padrões e Arquitetura
- **MVC**: Arquitetura Model-View-Controller
- **DAO**: Data Access Object
- **Singleton**: Instância única de conexão

## Melhorias Futuras

### Funcionalidades
- [ ] Relatórios em PDF
- [ ] Importação/Exportação CSV
- [ ] Sistema de usuários e permissões
- [ ] Backup automático
- [ ] Logs de auditoria

### Interface
- [ ] Temas personalizáveis
- [ ] Atalhos de teclado
- [ ] Filtros avançados
- [ ] Gráficos e estatísticas

### Arquitetura
- [ ] Migração para JavaFX
- [ ] API REST
- [ ] Testes unitários
- [ ] Docker containerization

## Troubleshooting

### Problemas Comuns

#### Erro de Conexão com MySQL
**Sintoma**: "Communications link failure"
**Solução**:
1. Verificar se MySQL está rodando
2. Conferir credenciais de acesso
3. Testar conectividade: `mysql -u root -p`

#### ClassNotFoundException: MySQL Driver
**Sintoma**: Driver não encontrado
**Solução**:
1. Baixar mysql-connector-java.jar
2. Adicionar ao classpath do projeto
3. Verificar versão compatível

#### Erro de Permissão no Banco
**Sintoma**: "Access denied for user"
**Solução**:
1. Verificar usuário e senha
2. Conceder permissões: `GRANT ALL ON sistema_crud.* TO 'root'@'localhost';`

#### Interface não Responsiva
**Sintoma**: Travamentos na UI
**Solução**:
1. Verificar se operações de banco estão na EDT
2. Implementar operações assíncronas se necessário

## Informações de Desenvolvimento

### Versão
- **Versão Atual**: 1.0.0
- **Data de Desenvolvimento**: Junho 2025
- **Compatibilidade**: Java 8+, MySQL 5.7+

### Equipe de Desenvolvimento
- Desenvolvedor: Erick Lima
- RA: 244950262
- Curso: Análise e Desenvolvimento de Sistemas
- Disciplina: Análise e Projeto Orientado a Objetos

- Desenvolvedor: André Roberto
- RA:
- Curso: Análise e Desenvolvimento de Sistemas
- Disciplina: Análise e Projeto Orientado a Objetos

- Desenvolvedor: Felipe Fernandes
- RA: 242062022
- Curso: Análise e Desenvolvimento de Sistemas
- Disciplina: Análise e Projeto Orientado a Objetos

- Desenvolvedor: Leonardo Tomaz
- RA: 240509852
- Curso: Análise e Desenvolvimento de Sistemas
- Disciplina: Análise e Projeto Orientado a Objetos


### Licença
Este projeto foi desenvolvido para fins acadêmicos como parte da avaliação da disciplina de Análise e Projeto Orientado a Objetos.

---

**Nota**: Este sistema foi desenvolvido seguindo as melhores práticas de programação orientada a objetos e padrões de projeto, visando demonstrar conhecimentos adquiridos na disciplina e preparar para aplicações reais no mercado de trabalho.