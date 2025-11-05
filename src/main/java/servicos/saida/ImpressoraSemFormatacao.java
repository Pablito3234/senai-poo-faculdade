package servicos.saida;

public class ImpressoraSemFormatacao extends Impressora{
    @Override
    public void imprimir(String texto){
        int tamanhoTexto = texto.length();
        StringBuilder decor = new StringBuilder();
        decor.append("-".repeat(tamanhoTexto));
        System.out.printf("""
                %s
                %s
                %s
                """, decor, texto, decor);
    }
}
