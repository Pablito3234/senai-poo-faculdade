package menu;

import servicos.ServicoCliente;
import servicos.ServicoEstoque;
import servicos.ServicoProduto;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {
    private static final Scanner entrada = new Scanner(System.in);
    private static final BancoObjetos bancoObjetos = new BancoObjetos();

    //Serviços das emtidades
    private static final ServicoCliente servicoCliente = new ServicoCliente(bancoObjetos, entrada);
    private static final ServicoProduto servicoProduto = new ServicoProduto(bancoObjetos, entrada);
    private static final ServicoEstoque servicoEstoque = new ServicoEstoque(bancoObjetos, entrada);

    //Valores constantes
    private static final char OPCAO_CRIAR = 'c';
    private static final char OPCAO_LISTAR = 'l';
    private static final char OPCAO_DELETAR = 'd';
    private static final char OPCAO_EDITAR = 'e';
    private static final char OPCAO_QUANTIDADE = 'q';
    private static final char OPCAO_BUSCAR = 'b';

    public static void main(String[] args) {
        menuPrincipal();
    }

    private static void menuPrincipal() {
        boolean sair = false;
        final char MENU_PRODUTOS = 'p';
        final char MENU_CLIENTES = 'c';
        final char MENU_ESTOQUE = 'e';
        final char MENU_VENDAS = 'v';

        while (!sair){
            System.out.println("""
                    Digite uma opção
                    [P]: Produtos
                    [C]: Clientes
                    [E]: Estoque
                    [V]: Vendas
                    [Qualquer outra coisa]: Sair
                    """);
            String opcao = entrada.nextLine().toLowerCase();
            char opcaoChar = opcao.charAt(0);

            switch (opcaoChar) {
                case MENU_PRODUTOS:
                    opcoesProduto();
                    break;
                case MENU_CLIENTES:
                    opcoesCliente();
                    break;
                case MENU_ESTOQUE:
                    opcoesEstoque();
                    break;
                case MENU_VENDAS:
                    opcoesVendas();
                    break;
                default:
                    System.out.println("Saindo");
                    sair = true;
                    break;
            }
        }
    }

    private static void opcoesProduto() {
        System.out.println("""
                Digite uma opção
                [C]: Criar novo produto
                [L]: Listar todos os produtos
                [B]: Buscar Produto (por codigo de produto)
                [D]: Deletar um produto (pelo codigo de produto)
                [Qualquer outra coisa]: Voltar
                """);
        String opcao = entrada.nextLine().toLowerCase();
        char opcaoChar = opcao.charAt(0);

        switch (opcaoChar){
            case OPCAO_CRIAR:
                servicoProduto.criarProduto();
                break;
            case OPCAO_BUSCAR:
                servicoProduto.buscarProduto();
                break;
            case OPCAO_LISTAR:
                servicoProduto.listarProdutos();
                break;
            case OPCAO_DELETAR:
                servicoProduto.deletarProduto();
                break;
            default:
                menuPrincipal();
                break;
        }
    }

    private static void opcoesCliente(){
        System.out.println("""
                Digite uma opção
                [C]: Criar
                [L]: Listar
                [D]: Deletar
                [Qualquer outra coisa]: Voltar
                """);
        String opcao = entrada.nextLine().toLowerCase();
        char opcaoChar = opcao.charAt(0);

        switch (opcaoChar){
            case OPCAO_CRIAR:
                servicoCliente.criarCliente();
                break;
            case OPCAO_LISTAR:
                servicoCliente.listarClientes();
                break;
            case OPCAO_DELETAR:
                System.out.println("Menu em construção");
                break;
            default:
                menuPrincipal();
                break;
        }
    }

    private static void opcoesEstoque(){
        System.out.println("""
                Digite uma opção
                [C]: Criar quantidade de items de um produto
                [Q]: Quantidade de items de um produto
                [L]: Listar todos os produtos com estoque existentes
                [E]: Atualizar quantidade de um produto
                [Qualquer outra coisa]: Voltar
                """);

        String opcao = entrada.nextLine().toLowerCase();
        char opcaoChar = opcao.charAt(0);

        switch (opcaoChar){
            case OPCAO_CRIAR:
                servicoEstoque.criarEstoque();
                break;
            case OPCAO_QUANTIDADE:
                servicoEstoque.quantidadeProduto();
                break;
            case OPCAO_LISTAR:
                servicoEstoque.listarQuantidades();
                break;
            case OPCAO_EDITAR:
                servicoEstoque.editarQuantidade();
                break;
            default:
                menuPrincipal();
                break;
        }
    }

    private static void opcoesVendas(){
        System.out.println("""
                Este é o menu de vendas você escolha uma opção:
                [1] Realizar uma venda
                [2] Relatório de vendas
                [3] Mostrar total de vendas
                [4] Mostrar maior e menor venda
                [9] Sair
                """);

        int opcao = 0;
        try {
            opcao = entrada.nextInt();
        } catch (InputMismatchException e){
            opcao = 9;
        }

        switch (opcao){
            case 1:
                break;
            case 2:
                break;
            case 3, 4:
                System.out.println("Menu em construção");
                opcoesVendas();
                break;
            default:
                menuPrincipal();
                break;
        }
        menuPrincipal();
    }
}
