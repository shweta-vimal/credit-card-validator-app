package com.ubs.sample.card.validator.app;

import com.ubs.sample.card.validator.app.assembly.ProgramAssembly;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ProgramAssembly.class)
public class CardValidatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardValidatorApplication.class, args);
    }

}
