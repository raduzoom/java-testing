package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.regex.Pattern;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {




        Pattern pattern = Pattern.compile("#([^#\\s]+)#");
        int i = 0;
        System.out.printf("Hello and welcome! %d", i);

        String cont = "This is a test #SP_NAME# test ";
        String contReplaced = pattern.matcher(cont).replaceAll(matchResult -> "ceva");

        System.out.println("This will be printed");
        System.out.println(cont);
        System.out.println(contReplaced);
        for (i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
        }

        SpringApplication.run(Main.class, args);
    }
}