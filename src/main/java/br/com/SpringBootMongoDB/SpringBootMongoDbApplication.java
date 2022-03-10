package br.com.SpringBootMongoDB;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SpringBootMongoDbApplication {

	public static void main(String[] args) { SpringApplication.run(SpringBootMongoDbApplication.class, args); }

	@Bean
	CommandLineRunner runner(StudentRepository studentRepository, MongoTemplate mongoTemplate){
		return args -> {
			Address address = new Address(
					"Brasil",
					"Rio de Janeiro",
					"34800000"
			);

			String email = "cleonildo2.junior@hotmail.com";
			Student student = new Student(
					"Cleonildo",
					"Junior",
					email,
					Gender.MALE,
					address,
					List.of("Matemática", "Geografia", "Física"),
					BigDecimal.TEN,
					LocalDateTime.now()

			);


//			usingMongoTemplateAndQuery(studentRepository, mongoTemplate, email, student);

			studentRepository.findStudentByEmail(email)
							.ifPresentOrElse(s -> {
								throw new IllegalStateException(
										"Foram encontrados mais de um estudante como mesmo email: " + email);
							}, () -> {
								studentRepository.insert(student);
							});

		};
	}

	private void usingMongoTemplateAndQuery(StudentRepository studentRepository, MongoTemplate mongoTemplate, String email, Student student) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));

		List<Student> students = mongoTemplate.find(query, Student.class);

		if(students.size() > 0) throw new IllegalStateException(
				"Foram encontrados mais de um estudante como mesmo email: " + email);

		if(students.isEmpty()) studentRepository.insert(student);
	}
}
