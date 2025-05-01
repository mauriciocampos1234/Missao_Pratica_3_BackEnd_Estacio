package cadastrobd.model.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConectorBD {

    // Método para obter uma conexão com o banco de dados
    public static Connection getConnection() {
        String url = "jdbc:postgresql://localhost:5432/loja"; 
        String user = "loja"; 
        String password = "loja"; 

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados!");
            e.printStackTrace();
        }
        return null;
    }

    // Método para obter um PreparedStatement a partir de uma consulta SQL
    public static PreparedStatement getPrepared(Connection connection, String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao preparar a instrução SQL!");
            e.printStackTrace();
        }
        return null;
    }

    // Método para executar uma consulta SELECT e retornar o ResultSet
    public static ResultSet getSelect(PreparedStatement preparedStatement) {
        try {
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta!");
            e.printStackTrace();
        }
        return null;
    }

    // Métodos sobrecarregados para fechar recursos

    // Fechar ResultSet
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar ResultSet!");
                e.printStackTrace();
            }
        }
    }

    // Fechar Statement ou PreparedStatement
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar Statement!");
                e.printStackTrace();
            }
        }
    }

    // Fechar Connection
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar Connection!");
                e.printStackTrace();
            }
        }
    }
}
