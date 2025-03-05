package br.com.projeto.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class VendedorBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	 private int id;
	    private String nome;
	    private String email;
	    private String telefone;
	    private String setor;

	    // Construtor
	    public VendedorBean() {
	        // Inicialização, se necessário
	    }

	    // Getters e Setters
	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public String getNome() {
	        return nome;
	    }

	    public void setNome(String nome) {
	        this.nome = nome;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getTelefone() {
	        return telefone;
	    }

	    public void setTelefone(String telefone) {
	        this.telefone = telefone;
	    }

	    public String getSetor() {
	        return setor;
	    }

	    public void setSetor(String setor) {
	        this.setor = setor;
	    }

}
