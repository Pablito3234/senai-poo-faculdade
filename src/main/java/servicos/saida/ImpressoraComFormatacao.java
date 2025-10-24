package servicos.saida;

import entidades.Cliente;
import entidades.ClienteFisico;
import entidades.ClienteJuridico;
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
        return "\n=== DETALHES DO PRODUTO ===\n" +
                String.format("Código: %d\n", produto.getCodigoProduto()) +
                String.format("Nome: %s\n", produto.getNome()) +
                String.format("Preço: R$ %.2f\n", produto.getPreco()) +
                "===========================\n";
    }

    public static String exibirDetalhesCliente(Cliente cliente){
        String documento = "";
        try {
            ClienteFisico clienteFisico = (ClienteFisico) cliente;
            documento = "CPF: " + clienteFisico.getCPF();
        } catch (ClassCastException e){
            ClienteJuridico clienteJuridico = (ClienteJuridico) cliente;
            documento = "CNPJ: " + clienteJuridico.getCNPJ();
        }

        return "\n=== DETALHES DO CLIENTE ===\n" +
                String.format("%s\n", documento) +
                String.format("Nome: %s\n", cliente.getNome());
    }
}
