package br.com.projeto.main;

import java.sql.Connection;
import io.github.cdimascio.dotenv.Dotenv;

import br.com.projeto.dao.DbFunctions;

public class Main {
    public static void main(String[] args) {
    	
    	Dotenv dotenv = Dotenv.load();
    	
    	 dotenv.get("DB_NAME");
         dotenv.get("DB_USER");
         dotenv.get("DB_PASSWORD");
    	
        DbFunctions db = new DbFunctions();
        Connection conn = db.connect_to_db("DB_NAME", "DB_USER", "DB_PASSWORD");

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
