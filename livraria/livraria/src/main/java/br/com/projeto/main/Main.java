package br.com.projeto.main;

import java.sql.Connection;

import br.com.projeto.dao.DbFunctions;

public class Main {
    public static void main(String[] args) {
    	
    	
    	
        DbFunctions db = new DbFunctions();
        Connection conn = db.connect_to_db("livrariadb", "postgres", "root");

        if (conn != null) {
            

            // Feche a conexão
             try {
                conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
