package servicos;

import entidades.Cliente;
import entidades.ClienteFisico;
import entidades.ClienteJuridico;
import menu.BancoObjetos;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ServicoCliente {
    BancoObjetos bancoObjetos;
    Scanner entrada;

    public ServicoCliente(BancoObjetos bancoObjetos, Scanner entrada) {
        this.bancoObjetos = bancoObjetos;
        this.entrada = entrada;
    }

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

    private Cliente inputClienteUsuario() {
        final int OPCAO_P_FISICA = 1;
        final int OPCAO_P_JURIDICA = 2;

        int tipoCliente = solicitarTipoCliente();

        String nome = solicitarNome(tipoCliente);
        String cpfCnpj = solicitarCpfCnpj(tipoCliente);
        String endereco = solicitarEndereco();
        String email = solicitarEmail();

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
}
