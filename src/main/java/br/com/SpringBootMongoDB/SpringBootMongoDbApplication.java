package br.com.SpringBootMongoDB;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SpringBootMongoDbApplication {

	public static void main(String[] args) { SpringApplication.run(SpringBootMongoDbApplication.class, args); }

	@Bean
	CommandLineRunner runner(StudentRepository studentRepository){
		return args -> {
			Address address = new Address(
					"Brasil",
					"Rio de Janeiro",
					"34800000"
			);

			Student student = new Student(
					"Cleonildo",
					"Junior",
					"cleonildo.junior@hotmail.com",
					Gender.MALE,
					address,
					List.of("Matemática", "Geografia", "Física"),
					BigDecimal.TEN,
					LocalDateTime.now()

			);

			studentRepository.insert(student);
		};
	}

}
