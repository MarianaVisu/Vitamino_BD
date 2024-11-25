package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {

    // Caminho para o banco de dados
    private static final String DATABASE_URL = "jdbc:sqlite:vitamino.db";

    /**
     * Estabelece uma conexão com o banco de dados SQLite.
     *
     * @return Conexão com o banco de dados
     * @throws SQLException se ocorrer um erro ao conectar
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    /**
     * Inicializa o banco de dados, criando tabelas se necessário.
     */
    public static void initializeDatabase() {
    try (Connection connection = getConnection();
         Statement statement = connection.createStatement()) {

        // Criar tabela CadastroPaciente
        String createCadastroPaciente =
            "CREATE TABLE IF NOT EXISTS CadastroPaciente (" +
            "CPF TEXT PRIMARY KEY UNIQUE NOT NULL, " +
            "Nome TEXT NOT NULL, " +
            "Email TEXT NOT NULL UNIQUE, " +
            "DataNascimento DATE NOT NULL, " +
            "Idade INTEGER NOT NULL, " +
            "Telefone TEXT NOT NULL" +
            ");";
        statement.execute(createCadastroPaciente);

        // Criar tabela CadastroRefeicoes
        String createCadastroRefeicoes =
            "CREATE TABLE IF NOT EXISTS CadastroRefeicoes (" +
            "ID_Refeicao INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, " +
            "TipoRefeicao TEXT NOT NULL, " +
            "DataRefeicao DATE NOT NULL" +
            ");";
        statement.execute(createCadastroRefeicoes);

        // Criar tabela Ingredientes
        String createIngredientes =
            "CREATE TABLE IF NOT EXISTS Ingredientes (" +
            "ID_Ingrediente INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, " +
            "Nome TEXT NOT NULL UNIQUE, " +
            "CaloriasPorGrama FLOAT NOT NULL" +
            ");";
        statement.execute(createIngredientes);

        // Criar tabela IngredientesRefeicao
        String createIngredientesRefeicao =
            "CREATE TABLE IF NOT EXISTS IngredientesRefeicao (" +
            "ID_IngredienteRefeicao INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, " +
            "ID_Refeicao INTEGER NOT NULL REFERENCES CadastroRefeicoes(ID_Refeicao), " +
            "ID_Ingrediente INTEGER NOT NULL REFERENCES Ingredientes(ID_Ingrediente), " +
            "Peso FLOAT NOT NULL, " +
            "CaloriasTotais FLOAT NOT NULL" +
            ");";
        statement.execute(createIngredientesRefeicao);

        System.out.println("Banco de dados inicializado com sucesso!");

    } catch (SQLException e) {
        System.err.println("Erro ao inicializar o banco de dados: " + e.getMessage());
        e.printStackTrace();
      }
    }
    
    public static void checkTables() {
        try (Connection connection = getConnection()) {
            var metaData = connection.getMetaData();
            var rs = metaData.getTables(null, null, "CadastroPaciente", null);

            if (rs.next()) {
                System.out.println("Tabela CadastroPaciente encontrada!");
            } else {
                System.out.println("Tabela CadastroPaciente NÃO encontrada!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar tabelas: " + e.getMessage());
        }
    }


    /**
     * Método principal para teste da conexão e inicialização do banco de dados.
     */
    public static void main(String[] args) {
        try {
            // Testa a conexão com o banco
            try (Connection connection = getConnection()) {
                System.out.println("Conexão com o banco de dados estabelecida!");
            }

            // Inicializa o banco de dados
            initializeDatabase();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
