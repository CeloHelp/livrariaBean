package br.com.projeto.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.projeto.bean.LivroBean;
import br.com.projeto.dao.DbFunctions;

@ManagedBean
@SessionScoped
public class LivroService {
	
	 private List<LivroBean> livros = new ArrayList<>();
	 private DbFunctions dbFunctions;

	    // Método para listar todos os livros
	 public List<LivroBean> listarTodos() {
		    List<LivroBean> livros = new ArrayList<>();
		    String sql = "SELECT id, titulo, autor, isbn, preco FROM livro";
		    
		    try (Connection conn = dbFunctions.connect_to_db("livrariadb", "postgres", "root");
		         PreparedStatement stmt = conn.prepareStatement(sql);
		         ResultSet rs = stmt.executeQuery()) {
		        
		        while (rs.next()) {
		            LivroBean livro = new LivroBean();
		            livro.setId(rs.getInt("id"));
		            livro.setTitulo(rs.getString("titulo"));
		            livro.setAutor(rs.getString("autor"));
		            livro.setIsbn(rs.getString("isbn"));
		            livro.setPreco(rs.getBigDecimal("preco"));
		            livros.add(livro);
		        }
		        
		    } catch (SQLException e) {
		        System.err.println("Erro ao listar livros: " + e.getMessage());
		    }
		    
		    System.out.println("Total de livros retornados: " + livros.size());
		    return livros;
		}


	    // Método para buscar um livro por ID
	    public LivroBean buscarPorId(int id) {
	        return livros.stream()
	                     .filter(livro -> livro.getId() == id)
	                     .findFirst()
	                     .orElse(null);
	    }

	    // Método para salvar um novo livro
	    public void salvar(LivroBean livro) {
	        livros.add(livro);
	        System.out.println("Livro salvo: " + livro.getTitulo());
	    }

}
