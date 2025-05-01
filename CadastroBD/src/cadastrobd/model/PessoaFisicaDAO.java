package cadastrobd.model;

import cadastrobd.model.util.ConectorBD;
import cadastrobd.model.util.SequenceManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaDAO {

    // Método para obter uma pessoa física pelo ID
    public PessoaFisica getPessoa(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        PessoaFisica pessoaFisica = null;

        try {
            connection = ConectorBD.getConnection();

            // Consulta SQL ajustada
            String sql = "SELECT p.id_pessoa, p.nome, p.endereco, p.telefone, p.email, pf.cpf " +
                         "FROM pessoa p INNER JOIN pessoa_fisica pf ON p.id_pessoa = pf.id_pessoa WHERE p.id_pessoa = ?";
            preparedStatement = ConectorBD.getPrepared(connection, sql);
            preparedStatement.setInt(1, id);

            resultSet = ConectorBD.getSelect(preparedStatement);

            if (resultSet != null && resultSet.next()) {
                pessoaFisica = new PessoaFisica(
                        resultSet.getInt("id_pessoa"),
                        resultSet.getString("nome"),
                        resultSet.getString("endereco"), // Logradouro mapeado para 'endereco'
                        "", // Cidade não existe no banco
                        "", // Estado não existe no banco
                        resultSet.getString("telefone"),
                        resultSet.getString("email"),
                        resultSet.getString("cpf")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar pessoa física!");
            e.printStackTrace();
        } finally {
            ConectorBD.close(resultSet);
            ConectorBD.close(preparedStatement);
            ConectorBD.close(connection);
        }

        return pessoaFisica;
    }

    // Método para obter todas as pessoas físicas
    public List<PessoaFisica> getPessoas() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<PessoaFisica> pessoasFisicas = new ArrayList<>();

        try {
            connection = ConectorBD.getConnection();

            // Consulta SQL ajustada
            String sql = "SELECT p.id_pessoa, p.nome, p.endereco, p.telefone, p.email, pf.cpf " +
                         "FROM pessoa p INNER JOIN pessoa_fisica pf ON p.id_pessoa = pf.id_pessoa";
            preparedStatement = ConectorBD.getPrepared(connection, sql);

            resultSet = ConectorBD.getSelect(preparedStatement);

            while (resultSet != null && resultSet.next()) {
                PessoaFisica pessoaFisica = new PessoaFisica(
                        resultSet.getInt("id_pessoa"),
                        resultSet.getString("nome"),
                        resultSet.getString("endereco"), // Logradouro mapeado para 'endereco'
                        "", // Cidade não existe no banco
                        "", // Estado não existe no banco
                        resultSet.getString("telefone"),
                        resultSet.getString("email"),
                        resultSet.getString("cpf")
                );
                pessoasFisicas.add(pessoaFisica);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar pessoas físicas!");
            e.printStackTrace();
        } finally {
            ConectorBD.close(resultSet);
            ConectorBD.close(preparedStatement);
            ConectorBD.close(connection);
        }

        return pessoasFisicas;
    }

    // Método para incluir uma pessoa física
    public void incluir(PessoaFisica pessoaFisica) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConectorBD.getConnection();

            // Obter o próximo ID da sequência
            int id = SequenceManager.getValue("seq_pessoa_id");

            // Inserir na tabela Pessoa
            String sqlPessoa = "INSERT INTO pessoa (id_pessoa, nome, endereco, telefone, email) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = ConectorBD.getPrepared(connection, sqlPessoa);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, pessoaFisica.getNome());
            preparedStatement.setString(3, pessoaFisica.getLogradouro()); // Logradouro mapeado para 'endereco'
            preparedStatement.setString(4, pessoaFisica.getTelefone());
            preparedStatement.setString(5, pessoaFisica.getEmail());
            preparedStatement.executeUpdate();
            ConectorBD.close(preparedStatement);

            // Inserir na tabela PessoaFisica
            String sqlPessoaFisica = "INSERT INTO pessoa_fisica (id_pessoa, cpf) VALUES (?, ?)";
            preparedStatement = ConectorBD.getPrepared(connection, sqlPessoaFisica);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, pessoaFisica.getCpf());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao incluir pessoa física!");
            e.printStackTrace();
        } finally {
            ConectorBD.close(preparedStatement);
            ConectorBD.close(connection);
        }
    }

    // Método para alterar uma pessoa física
    public void alterar(PessoaFisica pessoaFisica) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConectorBD.getConnection();

            // Atualizar na tabela Pessoa
            String sqlPessoa = "UPDATE pessoa SET nome = ?, endereco = ?, telefone = ?, email = ? WHERE id_pessoa = ?";
            preparedStatement = ConectorBD.getPrepared(connection, sqlPessoa);
            preparedStatement.setString(1, pessoaFisica.getNome());
            preparedStatement.setString(2, pessoaFisica.getLogradouro()); // Logradouro mapeado para 'endereco'
            preparedStatement.setString(3, pessoaFisica.getTelefone());
            preparedStatement.setString(4, pessoaFisica.getEmail());
            preparedStatement.setInt(5, pessoaFisica.getId());
            preparedStatement.executeUpdate();
            ConectorBD.close(preparedStatement);

            // Atualizar na tabela PessoaFisica
            String sqlPessoaFisica = "UPDATE pessoa_fisica SET cpf = ? WHERE id_pessoa = ?";
            preparedStatement = ConectorBD.getPrepared(connection, sqlPessoaFisica);
            preparedStatement.setString(1, pessoaFisica.getCpf());
            preparedStatement.setInt(2, pessoaFisica.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao alterar pessoa física!");
            e.printStackTrace();
        } finally {
            ConectorBD.close(preparedStatement);
            ConectorBD.close(connection);
        }
    }

    // Método para excluir uma pessoa física
    public void excluir(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConectorBD.getConnection();

            // Excluir da tabela PessoaFisica
            String sqlPessoaFisica = "DELETE FROM pessoa_fisica WHERE id_pessoa = ?";
            preparedStatement = ConectorBD.getPrepared(connection, sqlPessoaFisica);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            ConectorBD.close(preparedStatement);

            // Excluir da tabela Pessoa
            String sqlPessoa = "DELETE FROM pessoa WHERE id_pessoa = ?";
            preparedStatement = ConectorBD.getPrepared(connection, sqlPessoa);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir pessoa física!");
            e.printStackTrace();
        } finally {
            ConectorBD.close(preparedStatement);
            ConectorBD.close(connection);
        }
    }

	public boolean cpfExiste(String cpf) {
		// TODO Auto-generated method stub
		return false;
	}
}