package menu;

import entidades.Cliente;
import entidades.Produto;

import java.util.ArrayList;
import java.util.function.Predicate;

public class BancoObjetos {
    ArrayList<Produto> produtos;
    ArrayList<Cliente> clientes;

    public BancoObjetos() {
        this.produtos = new ArrayList<Produto>();
        this.clientes = new ArrayList<Cliente>();
    }

    public BancoObjetos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    //Operacoes Produtos
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

    //Operacoes de cliente
    public boolean existeCodigoPrduto(Integer codigoTeste){
        Predicate<Produto> predicate = obj ->
                obj.getCodigoProduto().equals(codigoTeste);
        boolean existe = produtos.stream().anyMatch(predicate);

        return existe;
    }

    public void adicionarCliente(Cliente cliente){
        clientes.add(cliente);
    }

    public void deletarCliente(int index){
        clientes.remove(index);
    }

    public ArrayList<Cliente> getClientes(){
        return clientes;
    }
}
