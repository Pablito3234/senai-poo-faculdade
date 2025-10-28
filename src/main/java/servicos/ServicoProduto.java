package servicos;

import entidades.Produto;
import menu.BancoObjetos;
import validadores.ValidadoresProduto;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import static servicos.saida.ImpressoraComFormatacao.*;

public class ServicoProduto {
    private static final int CODIGO_INVALIDO = -1;
    private static final int CODIGO_MAXIMO = 99999;

    private final Scanner entrada;
    private final BancoObjetos bancoObjetos;

    public ServicoProduto(BancoObjetos bancoObjetos, Scanner entrada) {
        this.entrada = entrada;
        this.bancoObjetos = bancoObjetos;
    }

    /**
     * Solicita ao usuário um código de produto existente
     * @return Código do produto ou -1 se inválido/não encontrado
     */
    private int inputCodigoProdutoExistente() {
        try {
            System.out.print("Digite o código do produto: ");
            int inputCodigo = entrada.nextInt();
            entrada.nextLine(); // consume newline

            if (bancoObjetos.getProdutos().isEmpty()) {
                System.err.println("Não existe nenhum produto registrado.");
                return CODIGO_INVALIDO;
            }

            if (!bancoObjetos.existeCodigoPrduto(inputCodigo)) {
                System.err.println("Produto com código " + inputCodigo + " não encontrado.");
                return CODIGO_INVALIDO;
            }

            return inputCodigo;
        } catch (InputMismatchException e) {
            System.err.println("Entrada inválida. Digite um número.");
            entrada.nextLine(); // clear buffer
            return CODIGO_INVALIDO;
        }
    }

    /**
     * Coleta dados do produto do usuário com validação
     * @return Produto criado com dados validados
     */
    private Produto inputProdutoUsuario() {
        Double preco = solicitarPreco();
        String nome = solicitarNome();
        Integer codigo = gerarCodigoProduto();

        return new Produto(preco, nome, codigo);
    }

    /**
     * Solicita e valida o preço do produto
     * @return Preço validado
     */
    private Double solicitarPreco() {
        while (true) {
            try {
                System.out.print("Digite o preço do produto (R$): ");
                Double preco = entrada.nextDouble();
                entrada.nextLine(); // consume newline

                if (ValidadoresProduto.isPrecoValido(preco)) {
                    return preco;
                }

                System.err.println(ValidadoresProduto.getMensagemErroPreco());

            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Digite um número decimal (use vírgula).");
                entrada.nextLine(); // clear buffer
            }
        }
    }

    /**
     * Solicita e valida o nome do produto
     * @return Nome validado
     */
    private String solicitarNome() {
        while (true) {
            System.out.print("Digite o nome do produto: ");
            String nome = entrada.nextLine().trim();

            if (ValidadoresProduto.isNomeValido(nome)) {
                return nome;
            }

            System.err.println(ValidadoresProduto.getMensagemErroNome());
        }
    }

    /**
     * Gera um código único para o produto
     * @return Código único não utilizado
     */
    private Integer gerarCodigoProduto() {
        Random random = new Random();
        Integer codigo;

        do {
            codigo = random.nextInt(CODIGO_MAXIMO) + 1; // 1 a 99999
        } while (bancoObjetos.existeCodigoPrduto(codigo));

        return codigo;
    }

    /**
     * Cria um novo produto e adiciona ao banco de objetos
     */
    public void criarProduto() {
        try {
            Produto produtoUsuario = inputProdutoUsuario();
            bancoObjetos.adicionarProduto(produtoUsuario);
            System.out.println("Produto criado com sucesso! Código: " + produtoUsuario.getCodigoProduto());
        } catch (Exception e) {
            System.err.println("Erro ao criar produto: " + e.getMessage());
        }
    }

    /**
     * Busca e exibe um produto pelo código
     */
    public void buscarProduto() {
        int entradaCodigo = inputCodigoProdutoExistente();

        if (entradaCodigo != CODIGO_INVALIDO) {
            Produto produtoAchado = bancoObjetos.getProdutoById(entradaCodigo);

            if (produtoAchado != null) {
                System.out.println(detalhesProduto(produtoAchado));
            } else {
                System.err.println("Erro ao recuperar o produto.");
            }
        }
    }

    /**
     * Lista todos os produtos cadastrados em formato de tabela
     */
    public void listarProdutos() {
        ArrayList<Produto> produtos = bancoObjetos.getProdutos();

        if (produtos.isEmpty()) {
            System.err.println("Nenhum produto cadastrado. Crie um primeiro!");
            return;
        }

        System.out.println("\n=== LISTA DE PRODUTOS ===");
        System.out.printf("%-10s %-40s %-15s\n", "Código", "Nome", "Preço");
        System.out.println("-----------------------------------------------------------------------");

        for (Produto produto : produtos) {
            System.out.printf("%-10d %-40s R$ %-12.2f\n",
                    produto.getCodigoProduto(),
                    produto.getNome(),
                    produto.getPreco());
        }

        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Total de produtos: " + produtos.size() + "\n");
    }

    /**
     * Deleta um produto do banco de objetos
     */
    public void deletarProduto() {
        if (bancoObjetos.getProdutos().isEmpty()) {
            System.err.println("Não existe nenhum produto cadastrado. Crie um primeiro!");
            return;
        }

        int inputCodigoProduto = inputCodigoProdutoExistente();

        if (inputCodigoProduto == CODIGO_INVALIDO) {
            return;
        }

        try {
            // Confirmar exclusão
            Produto produto = bancoObjetos.getProdutoById(inputCodigoProduto);

            if (produto != null) {
                System.out.println(detalhesProduto(produto));
                System.out.println("Tem certeza que deseja deletar o produto '%s'? (S/N)");
                char confirmacao = entrada.nextLine().trim().toLowerCase().charAt(0);

                if (confirmacao == 's') {
                    bancoObjetos.deletarProduto(inputCodigoProduto);
                    System.out.println("Produto deletado com sucesso!");
                } else {
                    System.out.println("Operação cancelada.");
                }
            }
        } catch (NullPointerException e) {
            System.err.println("Erro ao deletar produto: " + e.getMessage());
        } catch (StringIndexOutOfBoundsException e) {
            System.err.println("Operação cancelada.");
        }
    }
}