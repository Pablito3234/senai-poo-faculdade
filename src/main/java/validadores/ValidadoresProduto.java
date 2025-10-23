package validadores;

/**
 * Classe com métodos para validar os dados de um Produto e se assegurar que sejam de acordo com regra de negócio
 */
public class ValidadoresProduto {

    private static final int NOME_MIN_LENGTH = 4;
    private static final int NOME_MAX_LENGTH = 50;
    private static final float PRECO_MINIMO = 0.01f;
    private static final float PRECO_MAXIMO = 10000.0f;

    /**
     * Validador de nome do produto
     * @param nome Nome do produto a ser validado
     * @return true se válido, false se não
     */
    public static boolean isNomeValido(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }

        String nomeTrimmed = nome.trim();

        if (nomeTrimmed.length() < NOME_MIN_LENGTH || nomeTrimmed.length() > NOME_MAX_LENGTH) {
            return false;
        }

        // Aceita letras (incluindo acentuadas), números e espaços
        return nomeTrimmed.matches("^[A-Za-zÀ-ÿ0-9 ]+$");
    }

    /**
     * Validador de preço do produto
     * @param preco Preço do produto a ser validado
     * @return true se válido, false se não
     */
    public static boolean isPrecoValido(Double preco) {
        if (preco == null) {
            return false;
        }
        return preco >= PRECO_MINIMO && preco <= PRECO_MAXIMO;
    }

    /**
     * Obtém a mensagem de erro para nome inválido
     * @return Mensagem descritiva do erro
     */
    public static String getMensagemErroNome() {
        return String.format("Nome inválido. Deve ter entre %d e %d caracteres e conter apenas letras e números.",
                NOME_MIN_LENGTH, NOME_MAX_LENGTH);
    }

    /**
     * Obtém a mensagem de erro para preço inválido
     * @return Mensagem descritiva do erro
     */
    public static String getMensagemErroPreco() {
        return String.format("Preço inválido. Deve estar entre R$ %.2f e R$ %.2f.",
                PRECO_MINIMO, PRECO_MAXIMO);
    }
}