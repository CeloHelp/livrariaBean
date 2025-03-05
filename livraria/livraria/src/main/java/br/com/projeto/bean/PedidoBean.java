package br.com.projeto.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PedidoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private ClienteBean cliente;
    private VendedorBean vendedor;
    private Date dataPedido;
    private BigDecimal valorTotal;
    private List<LivroBean> livros; // Lista de livros ao invés de itensPedido

    public PedidoBean() {
        this.livros = new ArrayList<>();
        this.valorTotal = BigDecimal.ZERO;
        this.dataPedido = new Date(System.currentTimeMillis());
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClienteBean getCliente() {
        return cliente;
    }

    public void setCliente(ClienteBean cliente) {
        this.cliente = cliente;
    }

    public VendedorBean getVendedor() {
        return vendedor;
    }

    public void setVendedor(VendedorBean vendedor) {
        this.vendedor = vendedor;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public List<LivroBean> getLivros() {
        return livros;
    }

    public void setLivros(List<LivroBean> livros) {
        this.livros = livros;
        calcularValorTotal();
    }

    public void adicionarLivro(LivroBean livro) {
        this.livros.add(livro);
        calcularValorTotal();
    }

    public void removerLivro(LivroBean livro) {
        this.livros.remove(livro);
        calcularValorTotal();
    }

    private void calcularValorTotal() {
        this.valorTotal = livros.stream()
                .map(LivroBean::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean isEmpty() {
        return livros.isEmpty();
    }

    public int quantidadeItens() {
        return livros.size();
    }

    public void limparPedido() {
        this.livros.clear();
        calcularValorTotal();
    }
}
