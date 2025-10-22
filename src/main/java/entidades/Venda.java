package entidades;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

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

    public long totalVenda(){
        AtomicLong precoTotal = new AtomicLong();
        this.itensVenda.forEach((produto, _) -> precoTotal.addAndGet(produto.getPreco()));
        return precoTotal.get();
    }
}
