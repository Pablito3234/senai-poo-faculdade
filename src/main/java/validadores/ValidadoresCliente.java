package validadores;

/**
 * Classe responsável por validar dados de clientes
 */
public class ValidadoresCliente {

    /**
     * Valida se o nome contém apenas letras e espaços, e tem tamanho adequado
     * @param nome Nome a ser validado
     * @return true se válido, false caso contrário
     */
    public static boolean isNomeValido(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }

        String nomeTrimmed = nome.trim();

        if (nomeTrimmed.length() < 3 || nomeTrimmed.length() > 50) {
            return false;
        }

        // Aceita letras (incluindo acentuadas) e espaços
        return nomeTrimmed.matches("[a-zA-ZÀ-ÿ\\s]+");
    }

    /**
     * Valida se o CPF tem 11 dígitos (apenas números)
     * @param cpf CPF a ser validado
     * @return true se válido, false caso contrário
     */
    public static boolean isCpfValido(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return false;
        }

        // Remove caracteres não numéricos e valida tamanho
        String cpfNumeros = cpf.replaceAll("[^0-9]", "");
        return cpfNumeros.length() == 11;
    }

    /**
     * Valida se o CNPJ tem 14 dígitos (apenas números)
     * @param cnpj CNPJ a ser validado
     * @return true se válido, false caso contrário
     */
    public static boolean isCnpjValido(String cnpj) {
        if (cnpj == null || cnpj.trim().isEmpty()) {
            return false;
        }

        // Remove caracteres não numéricos e valida tamanho
        String cnpjNumeros = cnpj.replaceAll("[^0-9]", "");
        return cnpjNumeros.length() == 14;
    }

    /**
     * Valida endereço (deve ter entre 1 e 100 caracteres)
     * @param endereco Endereço a ser validado
     * @return true se válido, false caso contrário
     */
    public static boolean isEnderecoValido(String endereco) {
        if (endereco == null || endereco.trim().isEmpty()) {
            return false;
        }

        return endereco.trim().length() <= 100;
    }

    /**
     * Valida email (formato básico)
     * Email é opcional, então vazio é considerado válido
     * @param email Email a ser validado
     * @return true se válido, false caso contrário
     */
    public static boolean isEmailValido(String email) {
        // Email vazio é válido (campo opcional)
        if (email == null || email.trim().isEmpty()) {
            return true;
        }

        // Validação básica de email
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}
