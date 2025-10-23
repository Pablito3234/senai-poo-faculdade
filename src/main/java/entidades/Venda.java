package entidades;

import java.util.HashMap;

/**
 * Classe que representa uma venda
 */
public class Venda {
    private Integer codigoVenda;
    private HashMap<Produto, Integer> itensVenda;

    public Venda(Integer codigoVenda, HashMap<Produto, Integer> itensVenda) {
        this.codigoVenda = codigoVenda;
        this.itensVenda = itensVenda;
    }

    public Double totalVenda(){
        return this.itensVenda.keySet()
                .stream()
                .mapToDouble(Produto::getPreco)
                .sum();
    }

    public Integer getCodigoVenda() {
        return codigoVenda;
    }
}
