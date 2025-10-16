package entidades;

public class Cliente {
    private String nome;
    private String email;
    private String endereco;

    public Cliente(String nome, String email, String endereco) {
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
    }

    private boolean isCpfCnpjValid(String CPF_CNPJ){
        if (CPF_CNPJ.length() < 8){
            return false;
        }
        return true;
    }

    private boolean isNomeValid(String nome){
        if (nome.length() < 3 || nome.length() > 50){
            return false;
        }
        return true;
    }

    private boolean isEnderecoValid(String endereco){
        if (endereco.length() > 30){
            return false;
        }
        return true;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }
}
