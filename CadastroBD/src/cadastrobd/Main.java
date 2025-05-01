package cadastrobd;

import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaJuridica;

public class Main {
    public static void main(String[] args) {
        // Criando uma Pessoa Física
        PessoaFisica pessoaFisica = new PessoaFisica(1, "João Silva", "Rua A", "São Paulo", "SP", "1199999999", "joao@email.com", "123.456.789-00");
        System.out.println("Dados da Pessoa Física:");
        pessoaFisica.exibir();

        System.out.println();

        // Criando uma Pessoa Jurídica
        PessoaJuridica pessoaJuridica = new PessoaJuridica(2, "Empresa ABC", "Rua B", "Rio de Janeiro", "RJ", "2188888888", "empresa@email.com", "12.345.678/0001-90");
        System.out.println("Dados da Pessoa Jurídica:");
        pessoaJuridica.exibir();
    }
}
