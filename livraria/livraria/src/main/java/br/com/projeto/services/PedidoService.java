package br.com.projeto.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.projeto.bean.LivroBean;
import br.com.projeto.bean.PedidoBean;

@ManagedBean
@SessionScoped
public class PedidoService {
   
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/livrariadb";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "root";

    public void salvar(PedidoBean pedido) throws SQLException {
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não pode ser nulo.");
        }
        
        if (pedido.getLivros().isEmpty()) {
            throw new IllegalStateException("O pedido não possui itens.");
        }

        String sqlPedido = "INSERT INTO pedidos (clienteid, vendedorid, datapedido, valortotal) VALUES (?, ?, ?, ?) RETURNING pedidoid";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmtPedido = conn.prepareStatement(sqlPedido)) {
            
            // Configurando a inserção do pedido
            stmtPedido.setInt(1, pedido.getCliente().getId());
            stmtPedido.setInt(2, pedido.getVendedor().getId());
            stmtPedido.setDate(3, new java.sql.Date(pedido.getDataPedido().getTime()));
            stmtPedido.setBigDecimal(4, pedido.getValorTotal());

            // Executa a inserção do pedido e obtém o ID gerado
            ResultSet rs = stmtPedido.executeQuery();
            int pedidoId = 0;
            if (rs.next()) {
                pedidoId = rs.getInt(1);
            }

            // Agora, salva os livros na tabela intermediária
            for (LivroBean livro : pedido.getLivros()) {
                salvarItemPedido(conn, pedidoId, livro);
            }

            System.out.println("Pedido salvo com sucesso. ID: " + pedidoId);
        }
    }

    // Método auxiliar para salvar os livros do pedido na tabela intermediária
    private void salvarItemPedido(Connection conn, int pedidoId, LivroBean livro) throws SQLException {
        String sqlItem = "INSERT INTO pedido_item (pedidoid, livroid, quantidade, precounitario) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmtItem = conn.prepareStatement(sqlItem)) {
            stmtItem.setInt(1, pedidoId);
            stmtItem.setInt(2, livro.getId());
            stmtItem.setInt(3, 1); // Quantidade fixa como 1 por enquanto
            stmtItem.setBigDecimal(4, livro.getPreco());
            stmtItem.executeUpdate();
        }
    }
}
