package cadastrobd.model.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceManager {

    public static int getValue(String sequenceName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int nextValue = -1;

        try {
            connection = ConectorBD.getConnection();
            String sql = "SELECT nextval('" + sequenceName + "')";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet != null && resultSet.next()) {
                nextValue = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter o próximo valor da sequência!");
            e.printStackTrace();
        } finally {
            ConectorBD.close(resultSet);
            ConectorBD.close(preparedStatement);
            ConectorBD.close(connection);
        }

        return nextValue;
    }
}