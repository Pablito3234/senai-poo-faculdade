package menu;

import servicos.ServicoCliente;
import servicos.ServicoEstoque;
import servicos.ServicoProduto;
import servicos.ServicoVendas;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

public class Principal {
    private static final Scanner entrada = new Scanner(System.in);
    private static final BancoObjetos bancoObjetos = new BancoObjetos();

    // Serviços das entidades
    private static final ServicoCliente servicoCliente = new ServicoCliente(bancoObjetos, entrada);
    private static final ServicoProduto servicoProduto = new ServicoProduto(bancoObjetos, entrada);
    private static final ServicoEstoque servicoEstoque = new ServicoEstoque(bancoObjetos, entrada);
     private static final ServicoVendas servicoVendas = new ServicoVendas(bancoObjetos, entrada);

    private static final Map<String, String> prompts = new HashMap<>() {{
        put("principal", """
                =================
                Menu Principal
                =================
                
                [1]: Produtos
                [2]: Clientes
                [3]: Estoque
                [4]: Vendas
                [9]: Sair
                """);
        put("produtos", """
                Digite uma opção
                [1]: Criar novo produto
                [2]: Listar todos os produtos
                [3]: Buscar Produto (por codigo de produto)
                [4]: Deletar um produto (pelo codigo de produto)
                [9]: Voltar
                """);
        put("clientes", """
                Digite uma opção
                [1]: Criar novo cliente
                [2]: Listar todos os clientes
                [3]: Buscar Cliente
                [4]: Deletar Cliente
                [9]: Voltar
                """);
        put("estoque", """
                Digite uma opção
                [1]: Criar quantidade de items de um produto
                [2]: Quantidade de items de um produto
                [3]: Listar todos os produtos com estoque existentes
                [4]: Atualizar quantidade de um produto
                [9]: Voltar
                """);
        put("vendas", """
                Este é o menu de vendas você escolha uma opção:
                [1] Realizar uma venda
                [2] Relatório de vendas
                [3] Mostrar total de vendas
                [4] Mostrar maior e menor venda
                [9] Sair
                """);
    }};

    // Mapa de ações para cada módulo
    private static final Map<String, Map<Integer, Runnable>> acoes = new HashMap<>() {{
        put("produtos", Map.of(
                1, servicoProduto::criarProduto,
                2, servicoProduto::listarProdutos,
                3, servicoProduto::buscarProduto,
                4, servicoProduto::deletarProduto
        ));
        put("clientes", Map.of(
                1, servicoCliente::criarCliente,
                2, servicoCliente::listarClientes,
                3, servicoCliente::buscarCliente,
                4, servicoCliente::deletarCliente
        ));
        put("estoque", Map.of(
                1, servicoEstoque::criarEstoque,
                2, servicoEstoque::quantidadeProduto,
                3, servicoEstoque::listarQuantidades,
                4, servicoEstoque::editarQuantidade
        ));
         put("vendas", Map.of(
                 1, servicoVendas::registrarVenda,
                 2, servicoVendas::mostrarRelatorio,
                 3, servicoVendas::totalVendas,
                 4, servicoVendas::maiorMenorVenda
         ));
    }};

    public static void main(String[] args) {
        menuPrincipal();
        entrada.close();
    }

    private static int inputOpcaoMenu() {
        while (true) {
            try {
                System.out.print("Digite uma opção: ");
                int opcao = entrada.nextInt();
                entrada.nextLine(); // Limpar buffer
                if (opcao < 1 || opcao > 9) {
                    System.err.println("Opção Inválida");
                    continue;
                }
                return opcao;
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida, digite um número");
                entrada.nextLine(); // Limpar buffer
            }
        }
    }

    private static void menuPrincipal() {
        boolean sair = false;

        while (!sair) {
            System.out.println(prompts.get("principal"));
            int opcao = inputOpcaoMenu();

            switch (opcao) {
                case 1 -> executarModulo("produtos");
                case 2 -> executarModulo("clientes");
                case 3 -> executarModulo("estoque");
                case 4 -> executarModulo("vendas");
                case 9 -> {
                    System.out.println("Saindo...");
                    sair = true;
                }
                default -> System.err.println("Opção inválida");
            }
        }
    }

    private static void executarModulo(String modulo) {
        boolean voltar = false;

        while (!voltar) {
            System.out.println(prompts.get(modulo));
            int opcao = inputOpcaoMenu();

            if (opcao == 9) {
                voltar = true;
            } else {
                Map<Integer, Runnable> acoesModulo = acoes.get(modulo);

                if (acoesModulo != null && acoesModulo.containsKey(opcao)) {
                    try {
                        acoesModulo.get(opcao).run();
                    } catch (Exception e) {
                        System.err.println("Erro ao executar operação: " + e.getMessage());
                    }
                } else {
                    System.err.println("Opção não implementada ou inválida");
                }
            }
        }
    }
}