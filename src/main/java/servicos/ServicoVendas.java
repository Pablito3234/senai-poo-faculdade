package servicos;

import entidades.Estoque;
import entidades.Produto;
import entidades.Venda;
import excecoes.NegocioException;
import menu.BancoObjetos;
import servicos.saida.ImpressoraComFormatacao;

import static servicos.saida.ImpressoraComFormatacao.*;

import java.util.*;

public class ServicoVendas {
    private final BancoObjetos bancoObjetos;
    private final Scanner entrada;

    public ServicoVendas(BancoObjetos bancoObjetos, Scanner entrada) {
        this.bancoObjetos = bancoObjetos;
        this.entrada = entrada;
    }

    public void registrarVenda() throws NegocioException{
        if (bancoObjetos.getProdutos().isEmpty()){
            throw new NegocioException("Não existem produtos registrado para fazer uma venda. Cadatre um produto!");
        }
        HashMap<Produto, Integer> itensVenda = inputItens();
        Integer codigoAleatorio = gerarCodigoVenda();
        bancoObjetos.criarVenda(new Venda(codigoAleatorio, itensVenda));
        System.out.println("Sucesso ao registrar nova venda");
    }

    public void mostrarRelatorio() throws NegocioException{
        if (bancoObjetos.getVendas().isEmpty()){
            throw new NegocioException("Não tem vendas no banco");
        }
        System.out.println(relatorioVendas(bancoObjetos.getVendas()));
    }

    public void totalVendas(){
        System.out.println("A quantidade de vendas é de: "+bancoObjetos.getVendas().size()+"\n");
    }

    public void maiorMenorVenda(){
        ArrayList<Venda> vendas = bancoObjetos.getVendas();

        Optional<Venda> maiorVenda = vendas.stream().max(Comparator.comparing(Venda::totalVenda));

        if (maiorVenda.isPresent()){
            System.out.println("A maior venda é de: "+maiorVenda.get().totalVenda());
        } else {
            System.err.println("Nenhuma venda registrado");;
        }

        Optional<Venda> menorVenda = vendas.stream().min(Comparator.comparing(Venda::totalVenda));

        if (menorVenda.isPresent()){
            System.out.println("A maior venda é de: "+menorVenda.get().totalVenda());
        } else {
            System.err.println("Nenhuma venda registrado");;
        }
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

    private HashMap<Produto, Integer> inputItens() throws NegocioException {
        HashMap<Produto, Integer> itens = new HashMap<>();
        while (true){
            Integer inputCodigo = inputCodigoProduto();
            Produto produto = bancoObjetos.getProdutoById(inputCodigo);
            if (produto == null){
                throw new NegocioException("Erro interno, produto não achado");
            }

            Integer inputQuantidade = inputQuantidade(produto);

            System.out.printf("""
                    Confirmar adicionar este produto?
                    Nome: %s
                    Quantidade: %d
                    (s/n)
                    """, produto.getNome(), inputQuantidade);
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

    private int inputCodigoProduto() throws NegocioException{
        System.out.println("Digite um codigo de produto a ser adicionado na venda");
        try {
            int codigo = entrada.nextInt();
            entrada.nextLine();
            if (!bancoObjetos.existeCodigoPrduto(codigo)){
                throw new NegocioException("Código de produto não existe no banco");
            }
            if (!bancoObjetos.existeEstoque(codigo)){
                throw new NegocioException("Não existe estoque para este produto ainda");
            }
            return codigo;
        } catch (InputMismatchException e){
            entrada.nextLine();
            throw new NegocioException("Entrada inválida, digite numeros");
        }
    }

//    private Venda inputCodigoVenda(){
//        System.out.println("Digite um codigo de venda");
//        try {
//            int codigo = entrada.nextInt();
//            entrada.nextLine();
//
//            Venda venda = bancoObjetos.getVendaByCodigo(codigo);
//
//            if (venda == null){
//                System.err.println("Este codigo não existe");
//                return null;
//            }
//
//            return venda;
//
//        } catch (InputMismatchException e) {
//            System.err.println("Entrada inválida só digite numeros");
//            entrada.nextLine();
//            return null;
//        }
//    }

    private Integer inputQuantidade(Produto produto) throws NegocioException{
        System.out.println("Digite a quantidade do produto");
        try {
            int quantidade = entrada.nextInt();
            entrada.nextLine();
            if (quantidade < 1){
                throw new NegocioException("Quantidade invalida, compre ao menos um");
            }
            Estoque estoqueProdutoAtual = bancoObjetos.getEstoqueByProduto(produto);
            if (quantidade > estoqueProdutoAtual.getQuantidade()){
                throw new NegocioException("Quantidade no estoque insuficiente para este produto, só temos " + estoqueProdutoAtual.getQuantidade());
            }
            return quantidade;
        } catch (InputMismatchException e){
            throw new NegocioException("Entrada invalida, so digite numeros");
        }
    }
}
