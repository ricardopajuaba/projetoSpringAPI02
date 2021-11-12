package br.com.cotiinformatica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "br.com.cotiinformatica" })
public class ProjetoSpringApi02Application {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoSpringApi02Application.class, args);
	}

}
