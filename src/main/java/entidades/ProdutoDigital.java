package entidades;

public class ProdutoDigital extends Produto{
    private Long tamanhoProgramaBytes;

    public ProdutoDigital(Float preco, String nome, Integer codigoProduto, Long tamanhoProgramaBytes) {
        super(preco, nome, codigoProduto);
        this.tamanhoProgramaBytes = tamanhoProgramaBytes;
    }
}
