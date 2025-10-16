package menu;

import entidades.*;

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
    private boolean isAlpha(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
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

    private boolean isCpfCnpjValid(String CPF_CNPJ){
        return CPF_CNPJ.length() <= 8;
    }

    private boolean isNomeValid(String nome){
        if (nome.length() < 3 || nome.length() > 50){
            return false;
        }
        if (!isAlpha(nome)){
            return false;
        }
        return true;
    }

    private boolean isEnderecoValid(String endereco){
        return endereco.length() <= 30;
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

    //operacoes cliente
    private Cliente inputClienteUsuario() {
        final int OPCAO_P_FISICA = 1;
        final int OPCAO_P_JURIDICA = 2;

        // Step 1: Choose client type
        int tipoCliente = solicitarTipoCliente();

        // Step 2: Get common data
        String nome = solicitarNome(tipoCliente);
        String cpfCnpj = solicitarCpfCnpj(tipoCliente);
        String endereco = solicitarEndereco();
        String email = solicitarEmail();

        // Step 3: Create appropriate client
        if (tipoCliente == OPCAO_P_FISICA) {
            return new ClienteFisico(cpfCnpj, nome, email, endereco);
        } else if (tipoCliente == OPCAO_P_JURIDICA){
            return new ClienteJuridico(cpfCnpj, nome, email, endereco);
        } else {
            throw new IllegalArgumentException("Aconteceu um erro tentando pegar o tipo de cliente tentando criar um cliente novo");
        }
    }

    private int solicitarTipoCliente() {
        while (true) {
            try {
                System.out.println("""
                    Pessoa Fisica ou Juridica?:
                    1 - Pessoa Fisica
                    2 - Pessoa Juridica
                    """);
                int opcao = entrada.nextInt();
                entrada.nextLine(); // consume newline

                if (opcao == 1 || opcao == 2) {
                    return opcao;
                }
                System.err.println("Opção inválida. Digite 1 ou 2.");
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Digite um número.");
                entrada.nextLine(); // clear buffer
            }
        }
    }

    private String solicitarNome(int tipoCliente) {
        String prompt = (tipoCliente == 1) ? "Digite seu nome: " : "Digite o nome da Empresa: ";

        while (true) {
            System.out.print(prompt);
            String nome = entrada.nextLine().trim();

            if (isNomeValid(nome)) {
                return nome;
            }
            System.err.println("Nome inválido. Tente novamente.");
        }
    }

    private String solicitarCpfCnpj(int tipoCliente) {
        String prompt = (tipoCliente == 1) ? "Digite seu CPF: " : "Digite o CNPJ da empresa: ";
        String tipoDoc = (tipoCliente == 1) ? "CPF" : "CNPJ";

        while (true) {
            System.out.print(prompt);
            String documento = entrada.nextLine().trim();

            if (isCpfCnpjValid(documento)) {
                return documento;
            }
            System.err.println(tipoDoc + " inválido. Deve ter menos de 8 caracteres. Tente novamente.");
        }
    }

    private String solicitarEndereco() {
        while (true) {
            System.out.print("Digite o endereço: ");
            String endereco = entrada.nextLine().trim();

            if (isEnderecoValid(endereco)) {
                return endereco;
            }
            System.err.println("Endereço inválido. Deve ter menos de 30 caracteres. Tente novamente.");
        }
    }

    private String solicitarEmail() {
        while (true) {
            try {
                System.out.println("""
                    Quer informar o e-mail?:
                    S - Sim
                    N - Não
                    """);
                char opcao = entrada.nextLine().trim().toLowerCase().charAt(0);

                if (opcao == 's') {
                    System.out.print("Digite o e-mail: ");
                    return entrada.nextLine().trim();
                } else if (opcao == 'n') {
                    System.out.println("Criando usuário sem e-mail...");
                    return "";
                } else {
                    System.err.println("Opção inválida. Digite S ou N.");
                }
            } catch (StringIndexOutOfBoundsException e) {
                System.err.println("Entrada vazia. Digite S ou N.");
            }
        }
    }

    public void criarCliente() {
        Cliente inputCliente = inputClienteUsuario();
        bancoObjetos.adicionarCliente(inputCliente);
        System.out.println("Sucesso");
    }

    public void listarClientes() {
        if (bancoObjetos.getClientes().isEmpty()){
            System.err.println("Nenhum cliente criado");
            return;
        }

        // List Clientes Jurídicos
        if(!bancoObjetos.getClientesJuridicos().isEmpty()){
            System.out.println("\n=== CLIENTES JURÍDICOS ===");
            System.out.printf("%-20s %-30s %-30s %-60s\n", "CNPJ", "Nome Empresa", "Email", "Endereço");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------");

            for (ClienteJuridico cliente : bancoObjetos.getClientesJuridicos()) {
                System.out.printf("%-20s %-30s %-30s %-60s\n",
                        cliente.getCNPJ(),
                        cliente.getNome(),
                        cliente.getEmail(),
                        cliente.getEndereco());
            }
        }

        // List Clientes Físicos
        if(!bancoObjetos.getClientesFisicos().isEmpty()){
            System.out.println("\n=== CLIENTES FÍSICOS ===");
            System.out.printf("%-20s %-30s %-30s %-60s\n", "CPF", "Nome", "Email", "Endereço");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------");

            for (ClienteFisico cliente : bancoObjetos.getClientesFisicos()) {
                System.out.printf("%-20s %-30s %-30s %-60s\n",
                        cliente.getCPF(),
                        cliente.getNome(),
                        cliente.getEmail(),
                        cliente.getEndereco());
            }
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