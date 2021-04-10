package de.korittky.viergewinnt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("de.korittky")
public class VierGewinntApplication {

    public static void main(String[] args) {
        SpringApplication.run(VierGewinntApplication.class, args);
    }

}
