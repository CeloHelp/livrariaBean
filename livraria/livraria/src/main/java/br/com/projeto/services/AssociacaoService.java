package br.com.projeto.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AssociacaoService {
	
	private Connection connection;
	
	public AssociacaoService(Connection connection) {
		this.connection = connection;
	}
	
	public void associarClienteAVendedor(int clienteId, int vendedorId) throws SQLException {
		
		String sql = "INSERT INTO Cliente_Vendedor (cliente_ID, vendedor_ID) values (?, ?)";
		try(PreparedStatement stmt = connection.prepareStatement(sql)) {
			
			stmt.setInt(1, clienteId);
			stmt.setInt(2, vendedorId);
			stmt.executeUpdate();
		}
	}
	
	public void retornarClienteVendedor(int clienteId, int vendedorId) throws SQLException{
		String sql = "SELECT * FROM Cliente_Vendedor";
		try(PreparedStatement stmt1 = connection.prepareStatement(sql)){
			
		}
	}

}
