package entidades;

import java.util.Objects;

public class Produto {
    private Integer codigoProduto;
    private String nome;
    private Float preco;

    public Produto(Float preco, String nome, Integer codigoProduto) {
        if (!isNomeValido(nome)){
            throw new IllegalArgumentException("Nome Invalido");
        }
        if (!isPrecoValido(preco)){
            throw new IllegalArgumentException("Preco Invalido");
        }
        this.nome = nome;
        this.preco = preco;
        this.codigoProduto = codigoProduto;
    }

    private static boolean isNomeValido(String nome){
        if(nome.isEmpty()) return false;
        if(nome.length() < 4) return false;
        if(!nome.matches("^[A-Za-z ]+$")) return false;
        return true;
    }

    private static boolean isPrecoValido(Float preco){
        if (preco < 1.0 || preco > 10000.0) return false;
        return true;
    }

    public Integer getCodigoProduto() {
        return codigoProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setCodigoProduto(Integer codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public Float getPreco() {
        return preco;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(getCodigoProduto(), produto.getCodigoProduto());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCodigoProduto());
    }
}
