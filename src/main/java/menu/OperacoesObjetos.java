package menu;

import entidades.Cliente;
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

    //operacoes produtos
    private Produto inputProdutoUsuario(){
        while (true) {
            try {
                System.out.println("Digite um preco");
                Float preco = entrada.nextFloat();

                System.out.println("Digite um nome");
                String nome = entrada.next();

                return new Produto(preco, nome, gerarCodigoProduto());
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    public void criarProduto() {
        Produto produtoUsuario = inputProdutoUsuario();
        bancoObjetos.adicionarProduto(produtoUsuario);
    }

    public void editarProduto(){
        while (true){
            int inputIndex = verificarProdutoExistente();
            if (inputIndex == -1){
                continue;
            }
            bancoObjetos.editarProduto(inputIndex, inputProdutoUsuario());
            break;
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
        while (true){
            int inputIndex = verificarProdutoExistente();
            if (inputIndex == -1){
                continue;
            }
            bancoObjetos.deletarProduto(inputIndex);
            break;
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

    private int verificarProdutoExistente() {
        int invalido = -1;
        try {
            System.out.println("Digite um código de produto");
            int inputCodigo = entrada.nextInt();

            if (bancoObjetos.getProdutos().isEmpty()) {
                System.err.println("Não existe nenhum produto registrado");
                return invalido;
            }

            if (!bancoObjetos.existeCodigoPrduto(inputCodigo)) {
                System.err.println("Produto não existe");
                return invalido;
            }

            return inputCodigo;
        } catch (InputMismatchException exception) {
            System.err.println("Entrada inválida");
            return invalido;
        }
    }

    //operacoes cliente
    private static char INPUT_AFIRMATIVO = 's';
    private static char INPUT_NEGATIVO = 'n';
    private Cliente inputClienteUsuario(){
        while (true) {
            try {
                System.out.println("Digite um CPF ou CNPJ");
                String cpfCnpj = entrada.nextLine();

                System.out.println("Digite um nome");
                String nome = entrada.nextLine();

                String email = "";
                while (true){
                    System.out.println("""
                        Tem email
                        [S] Sim
                        [N] Não
                        """);
                    String opcaoEmail = entrada.nextLine();
                    char opcaoEmailChar = opcaoEmail.charAt(0);

                    if (opcaoEmailChar == INPUT_AFIRMATIVO){
                        System.out.println("Digite o email");
                        email = entrada.nextLine();
                        break;
                    }
                    if (opcaoEmailChar == INPUT_NEGATIVO){
                        System.out.println("OK");
                        break;
                    }
                    System.out.println("Opção Inválida");
                }

                System.out.println("Digite um endereço completo");
                String endereco = entrada.nextLine();

                return new Cliente(cpfCnpj, nome, email, endereco);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    public void criarCliente() {
        Cliente inputCliente = inputClienteUsuario();
        bancoObjetos.adicionarCliente(inputCliente);
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
}
