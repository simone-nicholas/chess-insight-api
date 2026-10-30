package com.company.chessinsightapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ChessInsightApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChessInsightApiApplication.class, args);
    }

}
