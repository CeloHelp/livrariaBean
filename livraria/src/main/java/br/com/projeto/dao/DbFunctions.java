package br.com.projeto.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbFunctions {
	public Connection connect_to_db(String dbname, String user, String pass) {
		Connection conn = null;

		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);
			if (conn != null) {
				System.out.println("Conexão Estabelecida!");
			} else {
				System.out.println("Conexão Falhou!");
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return conn;
	}
}

//	public void createTable(Connection conn, String table_name) {
//		Statement statement;
//		try {
//			String query = "create table " + table_name
//					+ "(id SERIAL,titulo varchar(200),isbn varchar(200),preco DECIMAL,data_lancamento DATE, primary key(id));";
//			statement = conn.createStatement();
//			statement.executeUpdate(query);
//			System.out.println("Table Criada");
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}

	/*public void addColumn(Connection conn, String tableName, String columnName, String columnDefinition) {
        Statement statement;
        try {
            String query = "ALTER TABLE " + tableName + " ADD COLUMN IF NOT EXISTS " + columnName + "  "
                    + columnDefinition + ";";
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Coluna " + columnName + " adicionada à tabela " + tableName + ".");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        DbFunctions dbFunctions = new DbFunctions();
        Connection conn = dbFunctions.connect_to_db("livrariadb", "postgres", "root");

        if (conn != null) {
            // Adicionando coluna PaginaAtual
            dbFunctions.addColumn(conn, "livro", "PaginaAtual", "INT");

            // Adicionando coluna PaginasTotais
            dbFunctions.addColumn(conn, "livro", "PaginasTotais", "INT");
        }
    }

}
*/