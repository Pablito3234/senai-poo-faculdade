package entidades;

public class Cliente {
    private String CPF_CNPJ;
    private String nome;
    private String email;
    private String endereco;

    public Cliente(String CPF_CNPJ, String nome, String email, String endereco) {
        if (!isCpfCnpjValid(CPF_CNPJ)){
            throw new IllegalArgumentException("CPF ou CNPJ inválido");
        }
        if (!isNomeValid(nome)){
            throw new IllegalArgumentException("Nome inválido");
        }
        if (!isEnderecoValid(endereco)){
            throw new IllegalArgumentException("Endereço Inválido");
        }
        this.CPF_CNPJ = CPF_CNPJ;
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
    }

    private boolean isCpfCnpjValid(String CPF_CNPJ){
        if (CPF_CNPJ.length() < 8){
            return false;
        }
        if (CPF_CNPJ.isEmpty()){
            return false;
        }
        return true;
    }

    private boolean isNomeValid(String nome){
        if (nome.length() < 3 || nome.length() > 50){
            return false;
        }
        if (nome.isEmpty()){
            return false;
        }
        return true;
    }

    private boolean isEnderecoValid(String endereco){
        if (endereco.length() > 30){
            return false;
        }
        if (endereco.isEmpty()){
            return false;
        }
        return true;
    }

    public String getCPF_CNPJ() {
        return CPF_CNPJ;
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
