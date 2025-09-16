package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DatabaseTest implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) {
        System.out.println("Verificando conexión a la base de datos...");

        try (Connection conn = dataSource.getConnection()) {
            System.out.println("¡Conexión exitosa! URL: " + conn.getMetaData().getURL());
            System.out.println("Usuario conectado: " + conn.getMetaData().getUserName());
        } catch (Exception e) {
            System.err.println("Error de conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
