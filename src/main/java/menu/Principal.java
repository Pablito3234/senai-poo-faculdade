package menu;

import java.util.ArrayList;
import java.util.Scanner;

public class Principal {
    private static Scanner entrada = new Scanner(System.in);
    private static BancoObjetos bancoObjetos = new BancoObjetos(new ArrayList<>());
    private static OperacoesObjetos operacoes = new OperacoesObjetos();

    static final char MENU_PRODUTOS = 'p';
    static final char MENU_CLIENTES = 'c';
    static final char MENU_ESTOQUE = 'e';
    static final char MENU_VENDAS = 'v';

    static final char OPCAO_CRIAR = 'c';
    static final char OPCAO_LISTAR = 'l';
    static final char OPCAO_DELETAR = 'd';
    static final char OPCAO_EDITAR = 'e';

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
                    System.out.println("Menu em construção");
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
        final char MENU_PRODUTOS = 'p';
        final char MENU_CLIENTES = 'c';
        final char MENU_ESTOQUE = 'e';
        final char MENU_VENDAS = 'v';
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
}
