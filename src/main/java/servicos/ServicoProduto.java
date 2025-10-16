package servicos;

import entidades.Produto;
import menu.BancoObjetos;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class ServicoProduto {
    private Scanner entrada;
    private BancoObjetos bancoObjetos;

    public ServicoProduto(BancoObjetos bancoObjetos, Scanner entrada) {
        this.entrada = entrada;
        this.bancoObjetos = bancoObjetos;
    }

    private int inputCodigoProdutoExistente() {
        try {
            System.out.println("Digite um código de produto");
            int inputCodigo = entrada.nextInt();
            entrada.nextLine();

            if (bancoObjetos.getProdutos().isEmpty()) {
                System.err.println("Não existe nenhum produto registrado");
                return -1;
            }

            if (!bancoObjetos.existeCodigoPrduto(inputCodigo)) {
                System.err.println("Produto não existe");
                return -1;
            }
            return inputCodigo;
        } catch (InputMismatchException e) {
            System.err.println("Entrada inválida");
            entrada.nextLine();
            return -1;
        }
    }

    private Produto inputProdutoUsuario(){
        while (true) {
            try {
                System.out.println("Digite um preco");
                Float preco = entrada.nextFloat();
                entrada.nextLine();

                System.out.println("Digite um nome");
                String nome = entrada.next();
                entrada.nextLine();

                return new Produto(preco, nome, gerarCodigoProduto());
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            } catch (InputMismatchException e) {
                System.err.println("Entrada Invalida");
                entrada.nextLine();
            }
        }
    }

    private Integer gerarCodigoProduto(){
        Integer codigo;
        while (true){
            Random random = new Random();
            codigo = random.nextInt(99999);
            if (bancoObjetos.existeCodigoPrduto(codigo)){
                continue;
            }
            break;
        }
        return codigo;
    }

    public void criarProduto() {
        Produto produtoUsuario = inputProdutoUsuario();
        bancoObjetos.adicionarProduto(produtoUsuario);
        System.out.println("Sucesso");
    }

    public void buscarProduto(){
        int entradaCodigo = inputCodigoProdutoExistente();
        if (entradaCodigo != -1){
            Produto produtoAchado = bancoObjetos.getProdutoById(entradaCodigo);
            System.out.printf("""
                O produto achado é:
                Codigo: %d
                Nome: %s
                Preço: %.2f
                """, produtoAchado.getCodigoProduto(), produtoAchado.getNome(), produtoAchado.getPreco());
        }
    }

    public void listarProdutos() {
        if (!bancoObjetos.getProdutos().isEmpty()) {
            System.out.println("Lista de produtos:\n");

            // Recupera a lista de produtos
            ArrayList<Produto> produtos = bancoObjetos.getProdutos();

            // Cabeçalho da tabela
            System.out.printf("%-10s %-30s %-10s\n", "Código", "Nome", "Preço");

            // Separador
            System.out.println("---------------------------------------------");

            // Percorre a lista de produtos
            for (Produto produto : produtos) {
                // Exibe os dados do produto com boa formatação
                System.out.printf("%-10d %-30s %-10.2f\n",
                        produto.getCodigoProduto(),
                        produto.getNome(),
                        produto.getPreco());
            }
        } else {
            System.err.println("Nenhum produto existente, crie um!");
        }
    }

    public void deletarProduto(){
        if (bancoObjetos.getProdutos().isEmpty()) {
            System.err.println("Não existe nenhum produto");
            return;
        }

        try {
            int inputCodigoProduto = inputCodigoProdutoExistente();
            if (inputCodigoProduto == -1) {
                return;
            }

            bancoObjetos.deletarProduto(inputCodigoProduto);
            System.out.println("Sucesso");

        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }
    }
}
