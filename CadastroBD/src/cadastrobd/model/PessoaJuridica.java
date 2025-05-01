package cadastrobd.model;

public class PessoaJuridica extends Pessoa {
    // Atributo específico
    private String cnpj;

    // Construtor padrão
    public PessoaJuridica() {
    }

    // Construtor completo
    public PessoaJuridica(int id, String nome, String logradouro, String cidade, String estado, String telefone, String email, String cnpj) {
        super(id, nome, logradouro, cidade, estado, telefone, email);
        this.cnpj = cnpj;
    }

    // Getter e Setter para CNPJ
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    // Sobrescrita do método exibir
    @Override
    public void exibir() {
        super.exibir(); // Chama o método da classe pai
        System.out.println("CNPJ: " + cnpj);
    }
}
