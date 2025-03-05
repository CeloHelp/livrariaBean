package br.com.projeto.bean;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.projeto.dao.DbFunctions;

public class LivroDAO {

	private DbFunctions dbFunctions;


	public LivroDAO() {
		this.dbFunctions = new DbFunctions();
	}

	public void salvar(LivroBean livro) {
		String sql = "INSERT INTO livro (titulo, autor, isbn, preco, data_lancamento, imagem) VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection connection = dbFunctions.connect_to_db("livrariadb", "postgres", "root");
				PreparedStatement statement = connection.prepareStatement(sql)) {

			System.out.println("Conexão Estabelecida");

			statement.setString(1, livro.getTitulo());
			statement.setString(2, livro.getAutor());
			statement.setString(3, livro.getIsbn());

			BigDecimal preco = livro.getPreco();
			statement.setBigDecimal(4, preco);

			statement.setDate(5, livro.getDataLancamento());

			String imagem = livro.getImagem();
			statement.setString(6, imagem);

			int rowsAffected = statement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Livro salvo com sucesso.");
			} else {
				System.out.println("Nenhum registro foi inserido.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao salvar o livro: " + e.getMessage());
		}
	}

	public List<LivroBean> listarLivros() {
		List<LivroBean> livros = new ArrayList<>();
		String sql = "SELECT titulo,autor,isbn,preco, data_lancamento, imagem FROM livro";

		try (Connection connection = dbFunctions.connect_to_db("livrariadb", "postgres", "root");
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				LivroBean livro = new LivroBean();
				livro.setTitulo(resultSet.getString("titulo"));
				livro.setAutor(resultSet.getString("autor"));
				livro.setIsbn(resultSet.getString("isbn"));
				livro.setPreco(resultSet.getBigDecimal("preco"));
				livro.setDataLancamento(resultSet.getDate("data_lancamento"));
				livro.setImagem(resultSet.getString("imagem"));
				
				

				livros.add(livro);
				
				

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return livros;
	}
	
	//-----------Excluir Livro--------//
	
	
		
	public void excluirLivro(String isbn) throws SQLException {
		String sql= "DELETE FROM livro WHERE isbn = ?";
		
		try (Connection connection = dbFunctions.connect_to_db("livrariadb", "postgres", "root");
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, isbn);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Erro ao excluir o livro do Banco de dados" + isbn, e );
		}
		
		
	}
              //-------DETALHES DO LIVRO--------//
	public LivroBean buscarLivroPorIsbn(String isbn) {
		LivroBean livro = null;
		try(Connection conn = getConnection()){
			String sql = "SELECT * FROM livro WHERE isbn = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, isbn);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				livro = new LivroBean();
				livro.setTitulo(rs.getString("titulo"));
	            livro.setAutor(rs.getString("autor"));
	            livro.setIsbn(rs.getString("isbn"));
	            livro.setPreco(rs.getBigDecimal("preco"));
	            livro.setImagem(rs.getString("imagem"));;
	            livro.setPaginaAtual(rs.getInt("paginaatual"));
	            livro.setPaginasTotais(rs.getInt("paginastotais"));
	            
	   
			}
			  			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return livro;
	}

	private Connection getConnection() throws SQLException {
	    try {
	        Class.forName("org.postgresql.Driver"); 
	        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/livrariadb", "postgres", "root");
	    } catch (ClassNotFoundException e) {
	        throw new SQLException("Driver não encontrado", e);
	    }
	}
	
	public void atualizarProgressoLeitura(LivroBean livro) {
	    String sqlu = "UPDATE livro SET paginaatual = ?, paginastotais = ? WHERE isbn = ?";
	    try (Connection conn = getConnection();
	         PreparedStatement ps = conn.prepareStatement(sqlu)) {
	    	
	    	conn.setAutoCommit(false);
	    	
	        ps.setInt(1, livro.getPaginaAtual());
	        ps.setInt(2, livro.getPaginasTotais());
	        ps.setString(3, livro.getIsbn());

	        int rowsAffected = ps.executeUpdate();
	        
	        

	        if (rowsAffected > 0) {
	        	conn.commit();
	            System.out.println("Progresso atualizado com sucesso para o livro com ISBN: " + livro.getIsbn());
	        } else {
	        	conn.rollback();
	            System.out.println("Nenhum livro encontrado com o ISBN: " + livro.getIsbn());
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Erro ao atualizar o progresso de leitura para o livro com ISBN: " + livro.getIsbn());
	    }
	}
}
