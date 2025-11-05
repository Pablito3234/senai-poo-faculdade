package menu;

import entidades.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BancoObjetos {
    private ArrayList<Produto> produtos;
    private ArrayList<Cliente> clientes;
    private ArrayList<Estoque> estoques;
    private ArrayList<Venda> vendas;

    public BancoObjetos() {
        this.produtos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.estoques = new ArrayList<>();
        this.vendas = new ArrayList<>();
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

    public ArrayList<Cliente> getClientes(){
        return clientes;
    }

    public ArrayList<ClienteJuridico> getClientesJuridicos(){

        return clientes.stream()
                .filter(cliente -> cliente instanceof ClienteJuridico)
                .map(cliente -> (ClienteJuridico) cliente)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<ClienteFisico> getClientesFisicos(){

        return clientes.stream()
                .filter(cliente -> cliente instanceof ClienteFisico)
                .map(cliente -> (ClienteFisico) cliente)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ClienteFisico getClienteByCpf(String cpf){
        Optional<ClienteFisico> cliente =  getClientesFisicos().stream()
                .filter(clienteFisico -> clienteFisico.getCPF().equals(cpf))
                .findFirst();
        return cliente.orElse(null);
    }

    public ClienteJuridico getClienteByCnpj(String cnpj){
        Optional<ClienteJuridico> cliente =  getClientesJuridicos().stream()
                .filter(clienteFisico -> clienteFisico.getCNPJ().equals(cnpj))
                .findFirst();
        return cliente.orElse(null);
    }

    public ArrayList<Cliente> getClientesByNome(String nome){

        return (ArrayList<Cliente>) getClientes().stream()
                .filter(cliente -> cliente.getNome().equals(nome))
                .toList();
    }

    public ArrayList<Cliente> getClientesByDocumento(String documento) {
        return Stream.concat(
                        getClientesJuridicos().stream()
                                .filter(c -> c.getCNPJ().equals(documento)),
                        getClientesFisicos().stream()
                                .filter(c -> c.getCPF().equals(documento))
                )
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void deletarCliente(Cliente cliente){
        this.clientes.remove(cliente);
    }

    //Operacoes estoque
    public Estoque getEstoqueByProduto(Produto produto){
        for (Estoque estoque : this.estoques){
            if (estoque.getProduto().equals(produto)){
                return estoque;
            }
        }
        return null;
    }

    public Estoque getEstoqueByProduto(Integer id){
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

    //operacoes vendas
    public void criarVenda(Venda venda){
        this.vendas.add(venda);
    }

    public boolean existeCodigoVenda(Integer codigo){
        return this.vendas.stream().anyMatch(
                venda -> venda.getCodigoVenda().equals(codigo)
        );
    }

    public Venda getVendaByCodigo(Integer codigo){
        for (Venda venda : this.vendas){
            if (venda.getCodigoVenda().equals(codigo)){
                return venda;
            }
        }
        return null;
    }

    public ArrayList<Venda> getVendas(){
        return this.vendas;
    }
}
