package br.com.academic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AcademicWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcademicWebApplication.class, args);
		System.out.print(new BCryptPasswordEncoder().encode("123"));
	}

}
