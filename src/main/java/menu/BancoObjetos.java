package menu;

import entidades.Cliente;
import entidades.Estoque;
import entidades.Produto;

import java.util.ArrayList;
import java.util.function.Predicate;

public class BancoObjetos {
    private ArrayList<Produto> produtos;
    private ArrayList<Cliente> clientes;
    private ArrayList<Estoque> estoques;

    public BancoObjetos() {
        this.produtos = new ArrayList<Produto>();
        this.clientes = new ArrayList<Cliente>();
        this.estoques = new ArrayList<Estoque>();
    }

    public BancoObjetos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    //Operacoes Produtos
    public void adicionarProduto(Produto produto){
        produtos.add(produto);
    }

    public void deletarProduto(int codProduto){
        try {
            int index = this.produtos.indexOf(getProdutoById(codProduto));
            produtos.remove(index);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Erro tentando deletar o produto no banco:" + e.getMessage());
        }
    }

    public void editarProduto(int codProduto, Produto produto){
        try {
            int index = this.produtos.indexOf(getProdutoById(codProduto));
            produtos.set(index, produto);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Erro tentando editar o produto no banco:" + e.getMessage());
        }
    }

    public ArrayList<Produto> getProdutos(){
        return produtos;
    }

    public Produto getProdutoById(Integer id){
        for (Produto produto : this.produtos){
            if (produto.getCodigoProduto().equals(id)){
                return produto;
            }
        }
        return null;
    }

    public boolean existeCodigoPrduto(Integer codigoTeste){
        Predicate<Produto> predicate = obj ->
                obj.getCodigoProduto().equals(codigoTeste);

        return produtos.stream().anyMatch(predicate);
    }

    //Operacoes de cliente
    public void adicionarCliente(Cliente cliente){
        clientes.add(cliente);
    }

    public void deletarCliente(int index){
        clientes.remove(index);
    }

    public ArrayList<Cliente> getClientes(){
        return clientes;
    }

    //Operacoes estoque
    private Estoque getEstoqueByProduto(Produto produto){
        for (Estoque estoque : this.estoques){
            if (estoque.getProduto().equals(produto)){
                return estoque;
            }
        }
        return null;
    }

    private Estoque getEstoqueByProduto(Integer id){
        for (Estoque estoque : this.estoques){
            if (estoque.getProduto().getCodigoProduto().equals(id)){
                return estoque;
            }
        }
        return null;
    }

    public boolean existeEstoque(Integer id){
        Estoque estoque = getEstoqueByProduto(id);
        return estoque != null;
    }

    public ArrayList<Estoque> getEstoques() {
        return this.estoques;
    }

    public Integer getQuantidadeProduto(Produto produto){
        Estoque estoque = getEstoqueByProduto(produto);
        if (estoque == null){
            return null;
        }
        return estoque.getQuantidade();
    }

    public Integer getQuantidadeProduto(Integer id){
        Estoque estoque = getEstoqueByProduto(id);
        if (estoque == null){
            return null;
        }
        return estoque.getQuantidade();
    }

    public void updateQuantidade(Integer novaQuantidade, Produto produtoAlvo){
        Estoque estoque = getEstoqueByProduto(produtoAlvo);
        if (estoque == null){
            throw new IllegalArgumentException("Produto não encontrado");
        }
        estoque.setQuantidade(novaQuantidade);
    }

    public void criarEstoque(Integer quantidade, Produto produto){
        this.estoques.add(new Estoque(produto, quantidade));
    }
}
