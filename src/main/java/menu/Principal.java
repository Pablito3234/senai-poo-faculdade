package menu;

import java.util.ArrayList;
import java.util.Scanner;

public class Principal {
    private static Scanner entrada = new Scanner(System.in);
    private static BancoObjetos bancoObjetos = new BancoObjetos(new ArrayList<>());
    private static OperacoesObjetos operacoes = new OperacoesObjetos();

    private static final char MENU_PRODUTOS = 'p';
    private static final char MENU_CLIENTES = 'c';
    private static final char MENU_ESTOQUE = 'e';
    private static final char MENU_VENDAS = 'v';

    private static final char OPCAO_CRIAR = 'c';
    private static final char OPCAO_LISTAR = 'l';
    private static final char OPCAO_DELETAR = 'd';
    private static final char OPCAO_EDITAR = 'e';

    public static void main(String[] args) {
        menuPrincipal();
    }

    private static void menuPrincipal() {
        boolean sair = false;


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
                    opcoesCrudProduto();
                    break;
                case MENU_CLIENTES:
                    opcoesCrudCliente();
                    break;
                case MENU_ESTOQUE:
                    System.out.println("Menu em construção");
                    break;
                case MENU_VENDAS:
                    System.out.println("Menu em construção");
                    break;
                default:
                    System.out.println("Saindo");
                    sair = true;
                    break;
            }
        }
    }

    private static void opcoesCrudProduto() {
        System.out.println("""
                Digite uma opção
                [C]: Criar
                [L]: Listar
                [A]: Atualizar
                [D]: Deletar
                [Qualquer outra coisa]: Voltar
                """);
        String opcao = entrada.nextLine().toLowerCase();
        char opcaoChar = opcao.charAt(0);

        switch (opcaoChar){
            case OPCAO_CRIAR:
                operacoes.criarProduto();
                break;
            case OPCAO_EDITAR:
                operacoes.editarProduto();
                break;
            case OPCAO_LISTAR:
                operacoes.listarProdutos();
                break;
            case OPCAO_DELETAR:
                operacoes.deletarProduto();
                break;
            default:
                System.out.println("Opcao invalida");
                break;
        }
    }

    private static void opcoesCrudCliente(){
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
                operacoes.criarCliente();
                break;
            case OPCAO_LISTAR:
                operacoes.listarClientes();
                break;
            case OPCAO_DELETAR:
                System.out.println("Menu em construção");
                break;
            default:
                System.out.println("Opcao invalida");
                break;
        }
    }
}
