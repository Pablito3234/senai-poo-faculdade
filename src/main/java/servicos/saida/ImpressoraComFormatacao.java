package servicos.saida;

import entidades.Cliente;
import entidades.ClienteFisico;
import entidades.ClienteJuridico;
import entidades.Produto;

import java.util.ArrayList;
import java.util.List;

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

    public static String detalhesProduto(Produto produto){
        return "\n=== DETALHES DO PRODUTO ===\n" +
                String.format("Código: %d\n", produto.getCodigoProduto()) +
                String.format("Nome: %s\n", produto.getNome()) +
                String.format("Preço: R$ %.2f\n", produto.getPreco()) +
                "===========================\n";
    }

    public static String detalhesClienteFormatado(Cliente cliente){
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

    public static String clientesFormatados(ArrayList<Cliente> clientes) {
        if (clientes == null || clientes.isEmpty()) {
            return "Nenhum cliente encontrado.\n";
        }

        StringBuilder sb = new StringBuilder();

        // Separar clientes jurídicos e físicos
        List<ClienteJuridico> clientesJuridicos = clientes.stream()
                .filter(c -> c instanceof ClienteJuridico)
                .map(c -> (ClienteJuridico) c)
                .toList();

        List<ClienteFisico> clientesFisicos = clientes.stream()
                .filter(c -> c instanceof ClienteFisico)
                .map(c -> (ClienteFisico) c)
                .toList();

        // Adicionar Clientes Jurídicos
        if (!clientesJuridicos.isEmpty()) {
            sb.append("\n=== CLIENTES JURÍDICOS ===\n");
            sb.append(String.format("%-20s %-30s %-30s %-60s\n", "CNPJ", "Nome Empresa", "Email", "Endereço"));
            sb.append("------------------------------------------------------------------------------------------------------------------------------------\n");

            for (ClienteJuridico cliente : clientesJuridicos) {
                sb.append(String.format("%-20s %-30s %-30s %-60s\n",
                        formatarCnpj(cliente.getCNPJ()),
                        cliente.getNome(),
                        cliente.getEmail().isEmpty() ? "Não informado" : cliente.getEmail(),
                        cliente.getEndereco()));
            }
        }

        // Adicionar Clientes Físicos
        if (!clientesFisicos.isEmpty()) {
            sb.append("\n=== CLIENTES FÍSICOS ===\n");
            sb.append(String.format("%-20s %-30s %-30s %-60s\n", "CPF", "Nome", "Email", "Endereço"));
            sb.append("------------------------------------------------------------------------------------------------------------------------------------\n");

            for (ClienteFisico cliente : clientesFisicos) {
                sb.append(String.format("%-20s %-30s %-30s %-60s\n",
                        formatarCpf(cliente.getCPF()),
                        cliente.getNome(),
                        cliente.getEmail().isEmpty() ? "Não informado" : cliente.getEmail(),
                        cliente.getEndereco()));
            }
        }

        // Caso não haja nenhum tipo de cliente
        if (clientesJuridicos.isEmpty() && clientesFisicos.isEmpty()) {
            sb.append("Nenhum cliente encontrado.\n");
        }

        return sb.toString();
    }

    /**
     * Formata CPF para exibição (XXX.XXX.XXX-XX)
     * @param cpf CPF sem formatação
     * @return CPF formatado
     */
    public static String formatarCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return cpf;
        }
        return cpf.substring(0, 3) + "." +
                cpf.substring(3, 6) + "." +
                cpf.substring(6, 9) + "-" +
                cpf.substring(9, 11);
    }

    /**
     * Formata CNPJ para exibição (XX.XXX.XXX/XXXX-XX)
     * @param cnpj CNPJ sem formatação
     * @return CNPJ formatado
     */
    public static String formatarCnpj(String cnpj) {
        if (cnpj == null || cnpj.length() != 14) {
            return cnpj;
        }
        return cnpj.substring(0, 2) + "." +
                cnpj.substring(2, 5) + "." +
                cnpj.substring(5, 8) + "/" +
                cnpj.substring(8, 12) + "-" +
                cnpj.substring(12, 14);
    }
}
