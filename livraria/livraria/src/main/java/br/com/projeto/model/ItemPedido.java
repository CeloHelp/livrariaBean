package br.com.projeto.model;

import java.math.BigDecimal;

import br.com.projeto.bean.LivroBean;
import br.com.projeto.bean.PedidoBean;

public class ItemPedido {
	private PedidoBean pedido;
    private LivroBean livro;
    private int quantidade;
    private BigDecimal precoUnitario;
    

    // Construtor padrão
    public ItemPedido() {
    }

    // Construtor com parâmetros
    public ItemPedido(PedidoBean pedido, LivroBean livro, int quantidade, BigDecimal precoUnitario) {
        this.pedido = pedido;
        this.livro = livro;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        
    }

    // Getters e Setters
    public PedidoBean getPedido() {
        return pedido;
    }

    public void setPedido(PedidoBean pedido) {
        this.pedido = pedido;
    }

    public LivroBean getLivro() {
        return livro;
    }

    public void setLivro(LivroBean livro) {
        this.livro = livro;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
    
    

    

	// Método para calcular o valor total do item
    public BigDecimal getValorTotal() {
        return precoUnitario.multiply(new BigDecimal(quantidade));
    }
    
    

}
