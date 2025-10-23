package servicos;

import entidades.Estoque;
import entidades.Produto;
import entidades.Venda;
import menu.BancoObjetos;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class ServicoVendas {
    private final BancoObjetos bancoObjetos;
    private final Scanner entrada;

    public ServicoVendas(BancoObjetos bancoObjetos, Scanner entrada) {
        this.bancoObjetos = bancoObjetos;
        this.entrada = entrada;
    }

    public void registrarVenda(){
        if (bancoObjetos.getProdutos().isEmpty()){
            System.out.println("Não existem produtos registrado para fazer uma venda. Cadatre um produto!");
            return;
        }
        HashMap<Produto, Integer> itensVenda = inputItens();
        Integer codigoAleatorio = gerarCodigoVenda();
        bancoObjetos.criarVenda(new Venda(codigoAleatorio, itensVenda));
        System.out.println("Sucesso ao registrar nova venda");
    }

    private Integer gerarCodigoVenda(){
        Random random = new Random();
        while (true){
            int codigoAleatorio = random.nextInt(999999);
            if (bancoObjetos.existeCodigoVenda(codigoAleatorio)){
                continue;
            }
            return codigoAleatorio;
        }
    }

    private HashMap<Produto, Integer> inputItens(){
        HashMap<Produto, Integer> itens = null;
        while (true){
            Integer inputCodigo = inputCodigoProduto();
            if (inputCodigo == null){
                continue;
            }
            Produto produto = bancoObjetos.getProdutoById(inputCodigo);
            if (produto == null){
                System.err.println("Erro interno, produto não achado");
                continue;
            }
            Integer inputQuantidade = inputQuantidade(produto);
            if (inputQuantidade == null){
                continue;
            }

            System.out.printf("""
                    Confirmar adicionar este produto?
                    %s
                    Quantidade: %d
                    (s/n)
                    """, produto, inputQuantidade);
            char opcao = entrada.nextLine().toLowerCase().charAt(0);
            if (opcao == 's'){
                itens.put(produto, inputQuantidade);
            } else if(opcao == 'n') {
                continue;
            } else {
                System.out.println("Opção Inválida, operação cancelada");
                continue;
            }

            System.out.println("""
                    Digite 1 para sair, qualquer outro para adicionar outro produto
                    """);
            opcao = entrada.nextLine().toLowerCase().charAt(0);
            if (opcao == '1'){
                continue;
            } else {
                break;
            }
        }
        return itens;
    }

    private Integer inputCodigoProduto(){
        System.out.println("Digite um codigo de produto a ser adicionado na venda");
        try {
            int codigo = entrada.nextInt();
            if (!bancoObjetos.existeCodigoPrduto(codigo)){
                System.err.println("Código de produto não existe no banco");
                return null;
            }
            if (!bancoObjetos.existeEstoque(codigo)){
                System.err.println("Não existe estoque para este produto ainda");
                return null;
            }
            return codigo;
        } catch (InputMismatchException e){
            System.err.println("Entrada inválida, digite numeros");
            inputCodigoProduto();
        }
        System.err.println("Aconteceu um erro inesperado: inputCodigoProduto() saiu do bloco try catch");
        return null;
    }

    private Integer inputQuantidade(Produto produto){
        System.out.println("Digite a quantidade do produto");
        try {
            Integer quantidade = entrada.nextInt();
            if (quantidade < 1){
                System.err.println("Quantidade invalida, compre ao menos um");
                return null;
            }
            Estoque estoqueProdutoAtual = bancoObjetos.getEstoqueByProduto(produto);
            if (estoqueProdutoAtual.getQuantidade() > quantidade){
                System.err.println("Quantidade no estoque insuficiente para este produto, só temos " + estoqueProdutoAtual.getQuantidade());
                inputQuantidade(produto);
            }
            return quantidade;
        } catch (InputMismatchException e){
            System.err.println("Entrada invalida, so digite numeros");
            inputQuantidade(produto);
        }
        return null;
    }
}
