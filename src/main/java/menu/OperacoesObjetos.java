package menu;

import entidades.Cliente;
import entidades.Estoque;
import entidades.Produto;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class OperacoesObjetos {
    private static final Scanner entrada = new Scanner(System.in);
    private final BancoObjetos bancoObjetos;

    public OperacoesObjetos() {
        this.bancoObjetos = new BancoObjetos();
    }

    //funcoes comuns
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

    //operacoes produtos
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

    public void criarProduto() {
        Produto produtoUsuario = inputProdutoUsuario();
        bancoObjetos.adicionarProduto(produtoUsuario);
        System.out.println("Sucesso");
    }

//    public void editarProduto(){
//        int inputCodProduto = verificarProdutoExistente();
//        bancoObjetos.editarProduto(inputCodProduto, inputProdutoUsuario());
//        System.out.println("Sucesso");
//    }

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
    }

    public void deletarProduto(){
        try {
            int inputCodigoProduto = inputCodigoProdutoExistente();
            if (inputCodigoProduto != -1) {
                bancoObjetos.deletarProduto(inputCodigoProduto);
                System.out.println("Sucesso");
            }
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }
    }

    private Integer gerarCodigoProduto(){
        Integer codigo;
        while (true){
            Random random = new Random();
            codigo = random.nextInt(0, 99999);
            if (bancoObjetos.existeCodigoPrduto(codigo)){
                continue;
            }
            break;
        }
        return codigo;
    }

    //operacoes cliente
    private Cliente inputClienteUsuario(){
        while (true) {
            try {
                System.out.println("Digite um CPF ou CNPJ");
                String cpfCnpj = entrada.next();

                System.out.println("Digite um nome");
                String nome = entrada.next();

                String email = "";
                while (true){
                    System.out.println("""
                        Tem email
                        [S] Sim
                        [N] Não
                        """);
                    String opcaoEmail = entrada.next().toLowerCase();
                    char opcaoEmailChar = opcaoEmail.charAt(0);

                    //operacoes cliente
                    final char INPUT_AFIRMATIVO = 's';
                    if (opcaoEmailChar == INPUT_AFIRMATIVO){
                        System.out.println("Digite o email");
                        email = entrada.next();
                        break;
                    }
                    final char INPUT_NEGATIVO = 'n';
                    if (opcaoEmailChar == INPUT_NEGATIVO){
                        System.out.println("OK");
                        break;
                    }
                    System.out.println("Opção Inválida");
                }

                System.out.println("Digite um endereço completo");
                String endereco = entrada.next();

                return new Cliente(cpfCnpj, nome, email, endereco);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    public void criarCliente() {
        Cliente inputCliente = inputClienteUsuario();
        bancoObjetos.adicionarCliente(inputCliente);
        System.out.println("Sucesso");
    }

    public void listarClientes() {
        System.out.println("Lista de clientes:\n");
        ArrayList<Cliente> clientes = bancoObjetos.getClientes();

        System.out.printf("%-20s %-30s %-30s %-60s\n", "CPF/CNPJ", "Nome", "Email", "Endereco");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");

        for (Cliente cliente : clientes) {
            System.out.printf("%-20s %-30s %-30s %-60s\n",
                    cliente.getCPF_CNPJ(),
                    cliente.getNome(),
                    cliente.getEmail(),
                    cliente.getEndereco());
        }
    }

    // Operacoes Estoque
    private Integer inputQuantidade(){
        while (true){
            try {
                System.out.println("Digite a quantidade do produto");
                int inputUsuario = entrada.nextInt();
                entrada.nextLine();
                if (inputUsuario < 0){
                    System.err.println("Valor não pode ser menor que zero");
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
        if (bancoObjetos.getProdutos().isEmpty()){
            System.err.println("Não existem produtos, crie um!");
        } else {
            Integer entradaUsuarioCodProduto = inputCodigoProdutoExistente();
            Integer entradaUsuarioQuantidade = inputQuantidade();
            if (entradaUsuarioCodProduto != -1) {
                if (bancoObjetos.existeEstoque(entradaUsuarioCodProduto)){
                    System.out.println("Produto ja existe no sistema (use a funcionalidade de editar)");
                } else {
                    bancoObjetos.criarEstoque(entradaUsuarioQuantidade, bancoObjetos.getProdutoById(entradaUsuarioCodProduto));
                    System.out.println("Estoque criado com sucesso");
                }
            }
        }
    }

    public void quantidadeProduto(){
        Integer entradaUsuarioCodProduto = inputCodigoProdutoExistente();
        if (entradaUsuarioCodProduto != -1) {
            if (!bancoObjetos.existeEstoque(entradaUsuarioCodProduto)){
                System.err.println("Não existe quantidade estoque declarado para este produto");
            } else {
                String nomeProduto = bancoObjetos.getProdutoById(entradaUsuarioCodProduto).getNome();
                Integer quantidade = bancoObjetos.getQuantidadeProduto(entradaUsuarioCodProduto);
                System.out.printf("""
                        Nome do produto: %s
                        Quantidade: %d
                        """, nomeProduto, quantidade);
            }
        }
    }

    public void listarQuantidades(){
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
    }

    public void editarQuantidade(){
        if (bancoObjetos.getProdutos().isEmpty()){
            System.err.println("Não existem produtos, crie um!");
        } else {
            Integer entradaUsuarioCodProduto = inputCodigoProdutoExistente();
            if (entradaUsuarioCodProduto != -1) {
                Integer entradaUsuarioQuantidade = inputQuantidade();
                if (!bancoObjetos.existeEstoque(entradaUsuarioCodProduto)) {
                    System.out.println("Produto não existe no sistema (use a funcionalidade de criar)");
                } else {
                    Estoque estoqueEditado = new Estoque(bancoObjetos.getProdutoById(entradaUsuarioCodProduto), entradaUsuarioQuantidade);
                    bancoObjetos.updateQuantidade(entradaUsuarioQuantidade, bancoObjetos.getProdutoById(entradaUsuarioCodProduto));
                    System.out.println("Estoque editado com sucesso");
                }
            }
        }
    }
}