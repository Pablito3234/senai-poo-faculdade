package entidades;

public class ClienteFisico extends Cliente{
    private String CPF;

    public ClienteFisico(String CPF, String nome, String email, String endereco) {
        super(nome, email, endereco);
        this.CPF = CPF;
    }

    public String getCPF() {
        return CPF;
    }
}
