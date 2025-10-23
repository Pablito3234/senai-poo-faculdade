package entidades;

import java.util.Objects;

public class Produto {
    private Integer codigoProduto;
    private String nome;
    private Double preco;

    public Produto(Double preco, String nome, Integer codigoProduto) {
        this.nome = nome;
        this.preco = preco;
        this.codigoProduto = codigoProduto;
    }

    public Integer getCodigoProduto() {
        return codigoProduto;
    }

    public String getNome() {
        return nome;
    }

    public Double getPreco() {
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

    @Override
    public String toString() {
        return nome + ": " + preco.toString();
    }
}
