package entidades;

public class ClienteJuridico extends Cliente{
    private String CNPJ;

    public ClienteJuridico(String CNPJ, String nome, String email, String endereco) {
        super(nome, email, endereco);
        this.CNPJ = CNPJ;
    }

    public String getCNPJ() {
        return CNPJ;
    }
}
