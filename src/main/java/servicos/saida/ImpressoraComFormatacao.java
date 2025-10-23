package servicos.saida;

import entidades.Produto;

public class ImpressoraComFormatacao extends Impressora{
    @Override
    public void imprimir(String texto){
        int tamanhoTexto = texto.length();
        StringBuilder decor = new StringBuilder();
        decor.append("*".repeat(tamanhoTexto));
        System.out.printf("""
                %s
                %s
                %s
                """, decor, texto.toUpperCase(), decor);
    }

    public static String exibirDetalhesProduto(Produto produto){
        return "\n=== DETALHES DO PRODUTO ===" +
                String.format("Código: %d\n", produto.getCodigoProduto()) +
                String.format("Nome: %s\n", produto.getNome()) +
                String.format("Preço: R$ %.2f\n", produto.getPreco()) +
                "===========================\n";
    }
}
