package menu;

import entidades.Produto;

import java.util.ArrayList;
import java.util.function.Predicate;

public class BancoObjetos {
    ArrayList<Produto> produtos;

    public BancoObjetos() {
        this.produtos = new ArrayList<Produto>();
    }

    public BancoObjetos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    public void adicionarProduto(Produto produto){
        produtos.add(produto);
    }

    public void deletarProduto(int index){
        produtos.remove(index);
    }

    public void editarProduto(int index, Produto produto){
        produtos.set(index, produto);
    }

    public ArrayList<Produto> getProdutos(){
        return produtos;
    }

    public boolean existeCodigoPrduto(Integer codigoTeste){
        Predicate<Produto> predicate = obj ->
                obj.getCodigoProduto().equals(codigoTeste);
        boolean existe = produtos.stream().anyMatch(predicate);

        return existe;
    }
}
