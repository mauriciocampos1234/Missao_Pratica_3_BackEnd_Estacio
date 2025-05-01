package cadastrobd.model;

public class PessoaFisica extends Pessoa {
    // Atributo específico
    private String cpf;

    // Construtor padrão
    public PessoaFisica() {
    }

    // Construtor completo
    public PessoaFisica(int id, String nome, String logradouro, String cidade, String estado, String telefone, String email, String cpf) {
        super(id, nome, logradouro, cidade, estado, telefone, email);
        this.cpf = cpf;
    }

    // Getter e Setter para CPF
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    // Sobrescrita do método exibir
    @Override
    public void exibir() {
        super.exibir(); // Chama o método da classe pai
        System.out.println("CPF: " + cpf);
    }
}
