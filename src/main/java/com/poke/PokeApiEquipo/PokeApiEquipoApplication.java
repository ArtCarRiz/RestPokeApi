package com.poke.PokeApiEquipo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PokeApiEquipoApplication {
    public static void main(String[] args) {
        SpringApplication.run(PokeApiEquipoApplication.class, args);
    }
}

