package servicos;

import entidades.Estoque;
import entidades.Produto;
import menu.BancoObjetos;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ServicoEstoque {
    private Scanner entrada;
    private BancoObjetos bancoObjetos;

    public ServicoEstoque(BancoObjetos bancoObjetos, Scanner entrada) {
        this.bancoObjetos = bancoObjetos;
        this.entrada = entrada;
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

    private Integer inputQuantidade(){
        while (true){
            try {
                System.out.println("Digite a quantidade do produto");
                int inputUsuario = entrada.nextInt();
                entrada.nextLine();
                if (inputUsuario < 0){
                    System.err.println("Valor não pode ser menor que zero");
                    entrada.nextLine();
                    continue;
                }
                return inputUsuario;
            } catch (InputMismatchException exception) {
                System.err.println("Entrada invalida");
                entrada.nextLine();
            }
        }

    }

    public void criarEstoque() {
        if (bancoObjetos.getProdutos().isEmpty()) {
            System.err.println("Não existem produtos, crie um!");
            return;
        }

        Integer entradaUsuarioCodProduto = inputCodigoProdutoExistente();
        if (entradaUsuarioCodProduto == -1) {
            return;
        }

        Integer entradaUsuarioQuantidade = inputQuantidade();

        if (bancoObjetos.existeEstoque(entradaUsuarioCodProduto)) {
            System.out.println("Produto já existe no sistema (use a funcionalidade de editar)");
            return;
        }

        bancoObjetos.criarEstoque(
                entradaUsuarioQuantidade,
                bancoObjetos.getProdutoById(entradaUsuarioCodProduto)
        );
        System.out.println("Estoque criado com sucesso");
    }

    public void quantidadeProduto(){
        Integer entradaUsuarioCodProduto = inputCodigoProdutoExistente();
        if (entradaUsuarioCodProduto == -1) {
            return;
        }

        if (!bancoObjetos.existeEstoque(entradaUsuarioCodProduto)){
            System.err.println("Não existe quantidade estoque declarado para este produto");
            return;
        }

        String nomeProduto = bancoObjetos.getProdutoById(entradaUsuarioCodProduto).getNome();
        Integer quantidade = bancoObjetos.getQuantidadeProduto(entradaUsuarioCodProduto);
        System.out.printf("""
            Nome do produto: %s
            Quantidade: %d
            """, nomeProduto, quantidade);
    }


    public void listarQuantidades(){
        if (!bancoObjetos.getEstoques().isEmpty()) {
            System.out.printf("%-20s %-10s %-10s%n", "Produto", "Preço", "Quantidade");
            System.out.println("--------------------------------------------------");

            // Dados da tabela
            for (Estoque e : bancoObjetos.getEstoques()) {
                Produto prod = e.getProduto();
                System.out.printf("%-20s R$ %-8.2f %-10d%n",
                        prod.getNome(),
                        prod.getPreco(),
                        e.getQuantidade());
            }
        } else {
            System.err.println("Não existe nenhum estoque registrado");
        }
    }

    public void editarQuantidade(){
        if (bancoObjetos.getProdutos().isEmpty()){
            System.err.println("Não existem produtos, crie um!");
            return;
        }

        Integer entradaUsuarioCodProduto = inputCodigoProdutoExistente();
        if (entradaUsuarioCodProduto == -1) {
            return;
        }

        Integer entradaUsuarioQuantidade = inputQuantidade();

        if (!bancoObjetos.existeEstoque(entradaUsuarioCodProduto)) {
            System.out.println("Produto não tem estoque no sistema (use a funcionalidade de criar)");
            return;
        }

        bancoObjetos.updateQuantidade(entradaUsuarioQuantidade,
                bancoObjetos.getProdutoById(entradaUsuarioCodProduto));
        System.out.println("Estoque editado com sucesso");
    }
}
