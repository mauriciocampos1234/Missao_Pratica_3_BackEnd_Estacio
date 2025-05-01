package cadastrobd;

import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.PessoaFisicaDAO;
import cadastrobd.model.PessoaJuridicaDAO;

public class CadastroBDTeste {

    public static void main(String[] args) {
        // Instanciar DAOs
        PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();

        // OPERAÇÃO 1: Criar e persistir uma pessoa física
        System.out.println("=== CRIANDO PESSOA FÍSICA ===");
        PessoaFisica pessoaFisica = new PessoaFisica(0, "Maria Silva", "Rua Nova, 456", "Rio de Janeiro", "RJ", "2199999999", "maria@email.com", "333.654.321-00");

        if (!pessoaFisicaDAO.cpfExiste(pessoaFisica.getCpf())) {
            pessoaFisicaDAO.incluir(pessoaFisica);
            System.out.println("Pessoa física criada e persistida!");
        } else {
            System.out.println("Erro: CPF já existe no banco de dados!");
        }

        // OPERAÇÃO 2: Alterar os dados da pessoa física
        System.out.println("\n=== ALTERANDO PESSOA FÍSICA ===");
        pessoaFisica.setNome("Maria Silva Alterada");
        pessoaFisica.setCpf("876.543.210-00");
        pessoaFisicaDAO.alterar(pessoaFisica);
        System.out.println("Pessoa física alterada!");

        // OPERAÇÃO 3: Consultar todas as pessoas físicas
        System.out.println("\n=== LISTANDO TODAS AS PESSOAS FÍSICAS ===");
        for (PessoaFisica pf : pessoaFisicaDAO.getPessoas()) {
            pf.exibir();
            System.out.println("-----");
        }

        // OPERAÇÃO 4: Excluir a pessoa física
        System.out.println("\n=== EXCLUINDO PESSOA FÍSICA ===");
        pessoaFisicaDAO.excluir(pessoaFisica.getId());
        System.out.println("Pessoa física excluída!");

        // OPERAÇÃO 5: Criar e persistir uma pessoa jurídica
        System.out.println("\n=== CRIANDO PESSOA JURÍDICA ===");
        PessoaJuridica pessoaJuridica = new PessoaJuridica(0, "Nova Empresa Ltda.", "Rua Comercial, 789", "São Paulo", "SP", "1188888888", "novaempresa@email.com", "77.765.432/0001-00");

        if (!pessoaJuridicaDAO.cnpjExiste(pessoaJuridica.getCnpj())) {
            pessoaJuridicaDAO.incluir(pessoaJuridica);
            System.out.println("Pessoa jurídica criada e persistida!");
        } else {
            System.out.println("Erro: CNPJ já existe no banco de dados!");
        }

        // OPERAÇÃO 6: Alterar os dados da pessoa jurídica
        System.out.println("\n=== ALTERANDO PESSOA JURÍDICA ===");
        pessoaJuridica.setNome("Nova Empresa Alterada Ltda.");
        pessoaJuridica.setCnpj("87.654.321/0001-00");
        pessoaJuridicaDAO.alterar(pessoaJuridica);
        System.out.println("Pessoa jurídica alterada!");

        // OPERAÇÃO 7: Consultar todas as pessoas jurídicas
        System.out.println("\n=== LISTANDO TODAS AS PESSOAS JURÍDICAS ===");
        for (PessoaJuridica pj : pessoaJuridicaDAO.getPessoas()) {
            pj.exibir();
            System.out.println("-----");
        }

        // OPERAÇÃO 8: Excluir a pessoa jurídica
        System.out.println("\n=== EXCLUINDO PESSOA JURÍDICA ===");
        pessoaJuridicaDAO.excluir(pessoaJuridica.getId());
        System.out.println("Pessoa jurídica excluída!");
    }
}