package cadastrobd.model;

import cadastrobd.model.util.ConectorBD;
import cadastrobd.model.util.SequenceManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaJuridicaDAO {

    // Método para obter uma pessoa jurídica pelo ID
    public PessoaJuridica getPessoa(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        PessoaJuridica pessoaJuridica = null;

        try {
            connection = ConectorBD.getConnection();

            // Consulta SQL ajustada
            String sql = "SELECT p.id_pessoa, p.nome, p.endereco, p.telefone, p.email, pj.cnpj " +
                         "FROM pessoa p INNER JOIN pessoa_juridica pj ON p.id_pessoa = pj.id_pessoa WHERE p.id_pessoa = ?";
            preparedStatement = ConectorBD.getPrepared(connection, sql);
            preparedStatement.setInt(1, id);

            resultSet = ConectorBD.getSelect(preparedStatement);

            if (resultSet != null && resultSet.next()) {
                pessoaJuridica = new PessoaJuridica(
                        resultSet.getInt("id_pessoa"),
                        resultSet.getString("nome"),
                        resultSet.getString("endereco"), // Logradouro mapeado para 'endereco'
                        "", // Cidade não existe no banco
                        "", // Estado não existe no banco
                        resultSet.getString("telefone"),
                        resultSet.getString("email"),
                        resultSet.getString("cnpj")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar pessoa jurídica!");
            e.printStackTrace();
        } finally {
            ConectorBD.close(resultSet);
            ConectorBD.close(preparedStatement);
            ConectorBD.close(connection);
        }

        return pessoaJuridica;
    }

    // Método para obter todas as pessoas jurídicas
    public List<PessoaJuridica> getPessoas() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<PessoaJuridica> pessoasJuridicas = new ArrayList<>();

        try {
            connection = ConectorBD.getConnection();

            // Consulta SQL ajustada
            String sql = "SELECT p.id_pessoa, p.nome, p.endereco, p.telefone, p.email, pj.cnpj " +
                         "FROM pessoa p INNER JOIN pessoa_juridica pj ON p.id_pessoa = pj.id_pessoa";
            preparedStatement = ConectorBD.getPrepared(connection, sql);

            resultSet = ConectorBD.getSelect(preparedStatement);

            while (resultSet != null && resultSet.next()) {
                PessoaJuridica pessoaJuridica = new PessoaJuridica(
                        resultSet.getInt("id_pessoa"),
                        resultSet.getString("nome"),
                        resultSet.getString("endereco"), // Logradouro mapeado para 'endereco'
                        "", // Cidade não existe no banco
                        "", // Estado não existe no banco
                        resultSet.getString("telefone"),
                        resultSet.getString("email"),
                        resultSet.getString("cnpj")
                );
                pessoasJuridicas.add(pessoaJuridica);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar pessoas jurídicas!");
            e.printStackTrace();
        } finally {
            ConectorBD.close(resultSet);
            ConectorBD.close(preparedStatement);
            ConectorBD.close(connection);
        }

        return pessoasJuridicas;
    }

    // Método para incluir uma pessoa jurídica
    public void incluir(PessoaJuridica pessoaJuridica) {
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
            preparedStatement.setString(2, pessoaJuridica.getNome());
            preparedStatement.setString(3, pessoaJuridica.getLogradouro()); // Logradouro mapeado para 'endereco'
            preparedStatement.setString(4, pessoaJuridica.getTelefone());
            preparedStatement.setString(5, pessoaJuridica.getEmail());
            preparedStatement.executeUpdate();
            ConectorBD.close(preparedStatement);

            // Inserir na tabela PessoaJuridica
            String sqlPessoaJuridica = "INSERT INTO pessoa_juridica (id_pessoa, cnpj) VALUES (?, ?)";
            preparedStatement = ConectorBD.getPrepared(connection, sqlPessoaJuridica);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, pessoaJuridica.getCnpj());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao incluir pessoa jurídica!");
            e.printStackTrace();
        } finally {
            ConectorBD.close(preparedStatement);
            ConectorBD.close(connection);
        }
    }

    // Método para alterar uma pessoa jurídica
    public void alterar(PessoaJuridica pessoaJuridica) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConectorBD.getConnection();

            // Atualizar na tabela Pessoa
            String sqlPessoa = "UPDATE pessoa SET nome = ?, endereco = ?, telefone = ?, email = ? WHERE id_pessoa = ?";
            preparedStatement = ConectorBD.getPrepared(connection, sqlPessoa);
            preparedStatement.setString(1, pessoaJuridica.getNome());
            preparedStatement.setString(2, pessoaJuridica.getLogradouro()); // Logradouro mapeado para 'endereco'
            preparedStatement.setString(3, pessoaJuridica.getTelefone());
            preparedStatement.setString(4, pessoaJuridica.getEmail());
            preparedStatement.setInt(5, pessoaJuridica.getId());
            preparedStatement.executeUpdate();
            ConectorBD.close(preparedStatement);

            // Atualizar na tabela PessoaJuridica
            String sqlPessoaJuridica = "UPDATE pessoa_juridica SET cnpj = ? WHERE id_pessoa = ?";
            preparedStatement = ConectorBD.getPrepared(connection, sqlPessoaJuridica);
            preparedStatement.setString(1, pessoaJuridica.getCnpj());
            preparedStatement.setInt(2, pessoaJuridica.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao alterar pessoa jurídica!");
            e.printStackTrace();
        } finally {
            ConectorBD.close(preparedStatement);
            ConectorBD.close(connection);
        }
    }

    // Método para excluir uma pessoa jurídica
    public void excluir(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConectorBD.getConnection();

            // Excluir da tabela PessoaJuridica
            String sqlPessoaJuridica = "DELETE FROM pessoa_juridica WHERE id_pessoa = ?";
            preparedStatement = ConectorBD.getPrepared(connection, sqlPessoaJuridica);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            ConectorBD.close(preparedStatement);

            // Excluir da tabela Pessoa
            String sqlPessoa = "DELETE FROM pessoa WHERE id_pessoa = ?";
            preparedStatement = ConectorBD.getPrepared(connection, sqlPessoa);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir pessoa jurídica!");
            e.printStackTrace();
        } finally {
            ConectorBD.close(preparedStatement);
            ConectorBD.close(connection);
        }
    }

	public boolean cnpjExiste(String cnpj) {
		// TODO Auto-generated method stub
		return false;
	}
}