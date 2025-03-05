package br.com.projeto.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.projeto.bean.VendedorBean;

public class VendedorService {
          
	private Connection connection;

    public VendedorService(Connection connection) {
        this.connection = connection;
    }

    // Método para adicionar um novo vendedor
    public void adicionarVendedor(VendedorBean vendedor) throws SQLException {
        String sql = "INSERT INTO Vendedor (nm_vendedor, email, nr_telefone) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, vendedor.getNome());
            stmt.setString(2, vendedor.getEmail());
            stmt.setString(3, vendedor.getTelefone());
            stmt.executeUpdate();
        }
    }

    // Método para atualizar um vendedor existente
    public void atualizarVendedor(VendedorBean vendedor) throws SQLException {
        String sql = "UPDATE Vendedor SET nm_vendedor = ?, email = ?, nr_telefone = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, vendedor.getNome());
            stmt.setString(2, vendedor.getEmail());
            stmt.setString(3, vendedor.getTelefone());
            stmt.setInt(4, vendedor.getId());
            stmt.executeUpdate();
        }
    }

    // Método para remover um vendedor pelo ID
    public void removerVendedor(int vendedorId) throws SQLException {
        String sql = "DELETE FROM Vendedor WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, vendedorId);
            stmt.executeUpdate();
        }
    }

    // Método para listar todos os vendedores
    public List<VendedorBean> listarTodos() throws SQLException {
        List<VendedorBean> vendedores = new ArrayList<>();
        String sql = "SELECT id, nm_vendedor, email, nr_telefone FROM Vendedor";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                VendedorBean vendedor = new VendedorBean();
                vendedor.setId(rs.getInt("id"));
                vendedor.setNome(rs.getString("nm_vendedor"));
                vendedor.setEmail(rs.getString("email"));
                vendedor.setTelefone(rs.getString("nr_telefone"));
                vendedores.add(vendedor);
                System.out.println("Vendedor carregado: " + vendedor.getNome());
            }
        }
        System.out.println("Total de vendedores Carregados: " + vendedores.size());
        return vendedores;
    }
    
 // Método para buscar um vendedor pelo ID
    public VendedorBean buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nm_vendedor, email, nr_telefone FROM Vendedor WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                VendedorBean vendedor = new VendedorBean();
                vendedor.setId(rs.getInt("id"));
                vendedor.setNome(rs.getString("nm_vendedor"));
                vendedor.setEmail(rs.getString("email"));
                vendedor.setTelefone(rs.getString("nr_telefone"));
                return vendedor;
            }
        }
        return null; // Retorna null se o vendedor não for encontrado
    }

}
