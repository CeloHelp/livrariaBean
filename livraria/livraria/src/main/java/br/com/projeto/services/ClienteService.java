package br.com.projeto.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.projeto.bean.ClienteBean;

public class ClienteService {
	private Connection connection;

    public ClienteService(Connection connection) {
        this.connection = connection;
    }

    // Método para adicionar um novo cliente
    public void adicionarCliente(ClienteBean cliente) throws SQLException {
        String sql = "INSERT INTO Cliente (nm_cliente, email, nr_telefone) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefone());
            stmt.executeUpdate();
        }
    }

    // Método para atualizar um cliente existente
    public void atualizarCliente(ClienteBean cliente) throws SQLException {
        String sql = "UPDATE Cliente SET nm_cliente = ?, email = ?, telefone = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefone());
            stmt.setInt(4, cliente.getId());
            stmt.executeUpdate();
        }
    }

    // Método para remover um cliente pelo ID
    public void removerCliente(int clienteId) throws SQLException {
        String sql = "DELETE FROM Cliente WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            stmt.executeUpdate();
        }
    }

    // Método para listar todos os clientes
    public List<ClienteBean> listarTodos() throws SQLException {
        List<ClienteBean> clientes = new ArrayList<>();
        String sql = "SELECT id, nm_cliente, email, nr_telefone FROM Cliente";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ClienteBean cliente = new ClienteBean();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nm_cliente"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getString("nr_telefone"));
                clientes.add(cliente);
                System.out.println("Cliente carregado: " + cliente.getNome());
            }
        }
        System.out.println("Total de clientes carregados: " + clientes.size());
        return clientes;
    }
    
 // Método para buscar um cliente pelo ID
    public ClienteBean buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nm_cliente, email, nr_telefone FROM Cliente WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ClienteBean cliente = new ClienteBean();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nm_cliente"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getString("nr_telefone"));
                return cliente;
            }
        }
        return null; // Retorna null se o cliente não for encontrado
    }


}
