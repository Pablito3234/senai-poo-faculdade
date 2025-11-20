package servicos;

import entidades.Cliente;
import entidades.ClienteFisico;
import entidades.ClienteJuridico;
import excecoes.NegocioException;
import menu.BancoObjetos;
import validadores.ValidadoresCliente;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static servicos.saida.ImpressoraComFormatacao.*;
import servicos.saida.ImpressoraComFormatacao;

/**
 * Classe encarregada de gerenciar métodos referente a clientes
 **/
public class ServicoCliente {
    private static final int OPCAO_PESSOA_FISICA = 1;
    private static final int OPCAO_PESSOA_JURIDICA = 2;

    private final BancoObjetos bancoObjetos;
    private final Scanner entrada;
    private final ImpressoraComFormatacao impressora = new ImpressoraComFormatacao();

    public ServicoCliente(BancoObjetos bancoObjetos, Scanner entrada) {
        this.bancoObjetos = bancoObjetos;
        this.entrada = entrada;
    }

    /**
     * Coleta dados do usuário e cria um novo cliente
     * @return Cliente criado (ClienteFisico ou ClienteJuridico)
     */
    private Cliente inputClienteUsuario() throws NegocioException {
        int tipoCliente = solicitarTipoCliente();

        String nome = solicitarNome();
        String cpfCnpj = solicitarCpfCnpj(tipoCliente);
        String endereco = solicitarEndereco();
        String email = solicitarEmail();

        if (tipoCliente == OPCAO_PESSOA_FISICA) {
            return new ClienteFisico(cpfCnpj, nome, email, endereco);
        } else if (tipoCliente == OPCAO_PESSOA_JURIDICA) {
            return new ClienteJuridico(cpfCnpj, nome, email, endereco);
        } else {
            throw new IllegalArgumentException("Tipo de cliente inválido: " + tipoCliente);
        }
    }

    /**
     * Solicita ao usuário o tipo de cliente (Pessoa Física ou Jurídica)
     * @return 1 para Pessoa Física, 2 para Pessoa Jurídica
     */
    private int solicitarTipoCliente(){
        while (true) {
            try {
                System.out.println("""
                    Pessoa Fisica ou Juridica?:
                    1 - Pessoa Fisica
                    2 - Pessoa Juridica
                    """);
                int opcao = entrada.nextInt();
                entrada.nextLine(); // consume newline

                if (opcao == OPCAO_PESSOA_FISICA || opcao == OPCAO_PESSOA_JURIDICA) {
                    return opcao;
                }
                System.err.println("Opção inválida. Digite 1 ou 2.");
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Digite um número.");
                entrada.nextLine(); // clear buffer
            }
        }
    }

    /**
     * Solicita o nome do cliente (pessoa ou empresa)
     * @return Nome validado
     */
    private String solicitarNome() throws NegocioException {
        System.out.print("Digite o nome: ");
        String nome = entrada.nextLine().trim();

        if (ValidadoresCliente.isNomeValido(nome)) {
            return nome;
        }
        throw new NegocioException("Nome inválido. Deve ter entre 3 e 50 caracteres e conter apenas letras.");
    }

    /**
     * Solicita o CPF ou CNPJ do cliente
     * @param tipoCliente Tipo do cliente (1 = Física, 2 = Jurídica)
     * @return CPF ou CNPJ validado
     */
    private String solicitarCpfCnpj(int tipoCliente) throws NegocioException{
        String prompt = (tipoCliente == OPCAO_PESSOA_FISICA)
                ? "Digite seu CPF (11 dígitos): "
                : "Digite o CNPJ da empresa (14 dígitos): ";
        String tipoDoc = (tipoCliente == OPCAO_PESSOA_FISICA) ? "CPF" : "CNPJ";
        String requisito = (tipoCliente == OPCAO_PESSOA_FISICA) ? "11 dígitos" : "14 dígitos";

        System.out.print(prompt);
        String documento = entrada.nextLine().trim();

        boolean valido = (tipoCliente == OPCAO_PESSOA_FISICA)
                ? ValidadoresCliente.isCpfValido(documento)
                : ValidadoresCliente.isCnpjValido(documento);

        if (valido) {
            // Remove caracteres não numéricos para armazenar
            return documento.replaceAll("[^0-9]", "");
        }
        System.err.println(tipoDoc + " inválido. Deve conter " + requisito + ". Tente novamente.");
        throw new NegocioException(tipoDoc + " inválido. Deve conter " + requisito + ". Tente novamente.");
    }

    /**
     * Solicita o endereço do cliente
     * @return Endereço validado
     */
    private String solicitarEndereco() {
        while (true) {
            System.out.print("Digite o endereço: ");
            String endereco = entrada.nextLine().trim();

            if (ValidadoresCliente.isEnderecoValido(endereco)) {
                return endereco;
            }
            System.err.println("Endereço inválido. Deve ter entre 1 e 100 caracteres. Tente novamente.");
        }
    }

    /**
     * Pergunta ao usuário se deseja informar email e coleta o mesmo
     * @return Email informado ou string vazia
     */
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
                    return solicitarEmailInput();
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

    /**
     * Solicita e valida o email do usuário
     * @return Email validado
     */
    private String solicitarEmailInput() {
        while (true) {
            System.out.print("Digite o e-mail: ");
            String email = entrada.nextLine().trim();

            if (ValidadoresCliente.isEmailValido(email)) {
                return email;
            }
            System.err.println("E-mail inválido. Deve estar no formato correto (exemplo@dominio.com).");
        }
    }

    /**
     * Cria um novo cliente e adiciona ao banco de objetos
     */
    public void criarCliente() throws NegocioException {
        Cliente inputCliente = inputClienteUsuario();
        bancoObjetos.adicionarCliente(inputCliente);
        System.out.println("Cliente criado com sucesso!");
    }

    /**
     * Lista todos os clientes cadastrados (Jurídicos e Físicos)
     */
    public void listarClientes() {
        System.out.println(clientesFormatados(bancoObjetos.getClientes()));
    }

    public void deletarCliente() throws NegocioException{
        int tipoCliente = solicitarTipoCliente();
        String cpfCnpj = solicitarCpfCnpj(tipoCliente);
        Cliente clienteAchado = null;
        switch (tipoCliente){
            case 1 -> {
                clienteAchado = bancoObjetos.getClienteByCpf(cpfCnpj);
            }
            case 2 -> {
                clienteAchado = bancoObjetos.getClienteByCnpj(cpfCnpj);
            }
        }
        if (clienteAchado == null){
            System.err.println("Nenhum cliente achado com este documento");
            return;
        }

        boolean confirmado = confirmarDelecao(clienteAchado);
        if (confirmado){
            bancoObjetos.deletarCliente(clienteAchado);
            System.out.println("Sucesso");
        }
    }

    public void buscarCliente() throws NegocioException{
        try {
            System.out.println("""
                    Deseja buscar o cliente por documento ou nome?
                    1 - Documento (CPF/CNPJ)
                    2 - Nome
                    """);

            int opcao = entrada.nextInt();
            entrada.nextLine(); // consume newline

            ArrayList<Cliente> encontrados = null;

            switch (opcao) {
                case 1 -> {
                    String documento = solicitarCpfCnpj(solicitarTipoCliente());
                    encontrados = bancoObjetos.getClientesByDocumento(documento);
                }
                case 2 -> {
                    String nome = solicitarNome();
                    encontrados = bancoObjetos.getClientesByNome(nome);
                }
                default -> {
                    impressora.imprimir("Opção inválida");
                    return;
                }
            }

            // Evita duplicação: a parte comum vai aqui
            if (encontrados == null || encontrados.isEmpty()) {
                impressora.imprimir("Nenhum cliente encontrado.");
                return;
            }

            clientesFormatados(encontrados);

        } catch (InputMismatchException e) {
            System.err.println("Entrada inválida, só use números");
            entrada.nextLine(); // limpa entrada inválida
        }
    }

    /**
     * Formata CPF para exibição (XXX.XXX.XXX-XX)
     * @param cpf CPF sem formatação
     * @return CPF formatado
     */
    private String formatarCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return cpf;
        }
        return cpf.substring(0, 3) + "." +
                cpf.substring(3, 6) + "." +
                cpf.substring(6, 9) + "-" +
                cpf.substring(9, 11);
    }

    /**
     * Formata CNPJ para exibição (XX.XXX.XXX/XXXX-XX)
     * @param cnpj CNPJ sem formatação
     * @return CNPJ formatado
     */
    private String formatarCnpj(String cnpj) {
        if (cnpj == null || cnpj.length() != 14) {
            return cnpj;
        }
        return cnpj.substring(0, 2) + "." +
                cnpj.substring(2, 5) + "." +
                cnpj.substring(5, 8) + "/" +
                cnpj.substring(8, 12) + "-" +
                cnpj.substring(12, 14);
    }

    private boolean confirmarDelecao(Cliente cliente){
        System.out.printf("""
                Deseja deletar este cliente?
                %s
                (S/N)
                """, detalhesClienteFormatado(cliente));
        try {
            char confimacao = entrada.nextLine().toLowerCase().charAt(0);
            if (confimacao == 's'){
                return true;
            } else if (confimacao == 'n'){
                System.out.println("Cancelando");
                return false;
            } else {
                System.err.println("Entrada invalida, cancelando deleção...");
                return false;
            }
        } catch (InputMismatchException e) {
            System.err.println("Entrada invalida, cancelando deleção...");
            return false;
        }
    }
}