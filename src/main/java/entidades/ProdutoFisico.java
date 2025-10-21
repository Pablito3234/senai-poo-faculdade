package entidades;

public class ProdutoFisico extends Produto{
    private Float peso;
    private Float altura;
    private Float largura;

    public ProdutoFisico(Float preco, String nome, Integer codigoProduto, Float peso, Float altura, Float largura) {
        super(preco, nome, codigoProduto);
        this.altura = altura;
        this.peso = peso;
        this.largura = largura;
    }
}
