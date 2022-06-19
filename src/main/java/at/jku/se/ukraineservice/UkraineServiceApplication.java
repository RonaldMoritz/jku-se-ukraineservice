package at.jku.se.ukraineservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"at.jku.se.ukraineservice**"})
public class UkraineServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UkraineServiceApplication.class, args);
	}

}
