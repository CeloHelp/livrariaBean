package br.com.projeto.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

@ManagedBean
@SessionScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	
	
	 public LivroBean() {
	        System.out.println("LivroBean inicializado.");
	        
	    }

	private int id;
	private String titulo;
	private String autor;
	private String isbn;
	private BigDecimal preco;
	private Date dataLancamento;
	private String imagem;
	private int paginaAtual;
	private int paginasTotais;

	// Getters e Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getIsbn() {
		System.out.println("Obtendo ISBN: " + isbn);
		return isbn;
	}

	public void setIsbn(String isbn) {
		System.out.println("ISBN Definido: " + isbn);
		this.isbn = isbn;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Date getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(java.sql.Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
	public int getPaginaAtual() {
		return paginaAtual;
	}

	public void setPaginaAtual(int paginaAtual) {
		this.paginaAtual = paginaAtual;
	}

	public int getPaginasTotais() {
		return paginasTotais;
	}

	public void setPaginasTotais(int paginasTotais) {
		this.paginasTotais = paginasTotais;
	}

	public String salvar() {

		System.out.println("Livro salvo: " + titulo);
		return "confirmacao";
	}
	
	
	

}

