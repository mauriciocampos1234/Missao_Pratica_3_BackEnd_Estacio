package cadastrobd;

import java.util.Scanner;
import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.PessoaFisicaDAO;
import cadastrobd.model.PessoaJuridicaDAO;

public class CadastroBDTeste2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();

        int opcao;
        do {
            // Exibir o menu
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Incluir");
            System.out.println("2 - Alterar");
            System.out.println("3 - Excluir");
            System.out.println("4 - Obter por ID");
            System.out.println("5 - Listar Todos");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (opcao) {
                case 1:
                    incluir(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    break;
                case 2:
                    alterar(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    break;
                case 3:
                    excluir(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    break;
                case 4:
                    obterPorId(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    break;
                case 5:
                    listarTodos(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    // Método para incluir uma nova pessoa
    private static void incluir(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) {
        System.out.println("\n--- INCLUIR ---");
        System.out.println("Tipo de pessoa (1 - Física, 2 - Jurídica): ");
        int tipo = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        if (tipo == 1) {
            System.out.println("Nome: ");
            String nome = scanner.nextLine();
            System.out.println("Endereço: ");
            String endereco = scanner.nextLine();
            System.out.println("Telefone: ");
            String telefone = scanner.nextLine();
            System.out.println("Email: ");
            String email = scanner.nextLine();
            System.out.println("CPF: ");
            String cpf = scanner.nextLine();

            PessoaFisica pessoaFisica = new PessoaFisica(0, nome, endereco, "", "", telefone, email, cpf);
            if (!pessoaFisicaDAO.cpfExiste(cpf)) {
                pessoaFisicaDAO.incluir(pessoaFisica);
                System.out.println("Pessoa física incluída com sucesso!");
            } else {
                System.out.println("Erro: CPF já existe no banco de dados!");
            }
        } else if (tipo == 2) {
            System.out.println("Nome: ");
            String nome = scanner.nextLine();
            System.out.println("Endereço: ");
            String endereco = scanner.nextLine();
            System.out.println("Telefone: ");
            String telefone = scanner.nextLine();
            System.out.println("Email: ");
            String email = scanner.nextLine();
            System.out.println("CNPJ: ");
            String cnpj = scanner.nextLine();

            PessoaJuridica pessoaJuridica = new PessoaJuridica(0, nome, endereco, "", "", telefone, email, cnpj);
            if (!pessoaJuridicaDAO.cnpjExiste(cnpj)) {
                pessoaJuridicaDAO.incluir(pessoaJuridica);
                System.out.println("Pessoa jurídica incluída com sucesso!");
            } else {
                System.out.println("Erro: CNPJ já existe no banco de dados!");
            }
        } else {
            System.out.println("Tipo inválido!");
        }
    }

    // Método para alterar uma pessoa existente
    private static void alterar(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) {
        System.out.println("\n--- ALTERAR ---");
        System.out.println("Tipo de pessoa (1 - Física, 2 - Jurídica): ");
        int tipo = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        System.out.println("ID da pessoa: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        if (tipo == 1) {
            PessoaFisica pessoaFisica = pessoaFisicaDAO.getPessoa(id);
            if (pessoaFisica != null) {
                System.out.println("Dados atuais:");
                pessoaFisica.exibir();

                System.out.println("Novo nome (ou pressione Enter para manter): ");
                String nome = scanner.nextLine();
                if (!nome.isEmpty()) pessoaFisica.setNome(nome);

                System.out.println("Novo endereço (ou pressione Enter para manter): ");
                String endereco = scanner.nextLine();
                if (!endereco.isEmpty()) pessoaFisica.setLogradouro(endereco);

                System.out.println("Novo telefone (ou pressione Enter para manter): ");
                String telefone = scanner.nextLine();
                if (!telefone.isEmpty()) pessoaFisica.setTelefone(telefone);

                System.out.println("Novo email (ou pressione Enter para manter): ");
                String email = scanner.nextLine();
                if (!email.isEmpty()) pessoaFisica.setEmail(email);

                System.out.println("Novo CPF (ou pressione Enter para manter): ");
                String cpf = scanner.nextLine();
                if (!cpf.isEmpty() && !pessoaFisicaDAO.cpfExiste(cpf)) {
                    pessoaFisica.setCpf(cpf);
                } else if (!cpf.isEmpty()) {
                    System.out.println("Erro: CPF já existe no banco de dados!");
                    return;
                }

                pessoaFisicaDAO.alterar(pessoaFisica);
                System.out.println("Pessoa física alterada com sucesso!");
            } else {
                System.out.println("Pessoa física não encontrada!");
            }
        } else if (tipo == 2) {
            PessoaJuridica pessoaJuridica = pessoaJuridicaDAO.getPessoa(id);
            if (pessoaJuridica != null) {
                System.out.println("Dados atuais:");
                pessoaJuridica.exibir();

                System.out.println("Novo nome (ou pressione Enter para manter): ");
                String nome = scanner.nextLine();
                if (!nome.isEmpty()) pessoaJuridica.setNome(nome);

                System.out.println("Novo endereço (ou pressione Enter para manter): ");
                String endereco = scanner.nextLine();
                if (!endereco.isEmpty()) pessoaJuridica.setLogradouro(endereco);

                System.out.println("Novo telefone (ou pressione Enter para manter): ");
                String telefone = scanner.nextLine();
                if (!telefone.isEmpty()) pessoaJuridica.setTelefone(telefone);

                System.out.println("Novo email (ou pressione Enter para manter): ");
                String email = scanner.nextLine();
                if (!email.isEmpty()) pessoaJuridica.setEmail(email);

                System.out.println("Novo CNPJ (ou pressione Enter para manter): ");
                String cnpj = scanner.nextLine();
                if (!cnpj.isEmpty() && !pessoaJuridicaDAO.cnpjExiste(cnpj)) {
                    pessoaJuridica.setCnpj(cnpj);
                } else if (!cnpj.isEmpty()) {
                    System.out.println("Erro: CNPJ já existe no banco de dados!");
                    return;
                }

                pessoaJuridicaDAO.alterar(pessoaJuridica);
                System.out.println("Pessoa jurídica alterada com sucesso!");
            } else {
                System.out.println("Pessoa jurídica não encontrada!");
            }
        } else {
            System.out.println("Tipo inválido!");
        }
    }

    // Método para excluir uma pessoa
    private static void excluir(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) {
        System.out.println("\n--- EXCLUIR ---");
        System.out.println("Tipo de pessoa (1 - Física, 2 - Jurídica): ");
        int tipo = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        System.out.println("ID da pessoa: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        if (tipo == 1) {
            PessoaFisica pessoaFisica = pessoaFisicaDAO.getPessoa(id);
            if (pessoaFisica != null) {
                pessoaFisicaDAO.excluir(id);
                System.out.println("Pessoa física excluída com sucesso!");
            } else {
                System.out.println("Pessoa física não encontrada!");
            }
        } else if (tipo == 2) {
            PessoaJuridica pessoaJuridica = pessoaJuridicaDAO.getPessoa(id);
            if (pessoaJuridica != null) {
                pessoaJuridicaDAO.excluir(id);
                System.out.println("Pessoa jurídica excluída com sucesso!");
            } else {
                System.out.println("Pessoa jurídica não encontrada!");
            }
        } else {
            System.out.println("Tipo inválido!");
        }
    }

    // Método para obter uma pessoa pelo ID
    private static void obterPorId(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) {
        System.out.println("\n--- OBTER POR ID ---");
        System.out.println("Tipo de pessoa (1 - Física, 2 - Jurídica): ");
        int tipo = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        System.out.println("ID da pessoa: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        if (tipo == 1) {
            PessoaFisica pessoaFisica = pessoaFisicaDAO.getPessoa(id);
            if (pessoaFisica != null) {
                pessoaFisica.exibir();
            } else {
                System.out.println("Pessoa física não encontrada!");
            }
        } else if (tipo == 2) {
            PessoaJuridica pessoaJuridica = pessoaJuridicaDAO.getPessoa(id);
            if (pessoaJuridica != null) {
                pessoaJuridica.exibir();
            } else {
                System.out.println("Pessoa jurídica não encontrada!");
            }
        } else {
            System.out.println("Tipo inválido!");
        }
    }

    // Método para listar todas as pessoas
    private static void listarTodos(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) {
        System.out.println("\n--- LISTAR TODOS ---");
        System.out.println("Tipo de pessoa (1 - Física, 2 - Jurídica): ");
        int tipo = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        if (tipo == 1) {
            System.out.println("Pessoas Físicas:");
            for (PessoaFisica pf : pessoaFisicaDAO.getPessoas()) {
                pf.exibir();
                System.out.println("-----");
            }
        } else if (tipo == 2) {
            System.out.println("Pessoas Jurídicas:");
            for (PessoaJuridica pj : pessoaJuridicaDAO.getPessoas()) {
                pj.exibir();
                System.out.println("-----");
            }
        } else {
            System.out.println("Tipo inválido!");
        }
    }
}
