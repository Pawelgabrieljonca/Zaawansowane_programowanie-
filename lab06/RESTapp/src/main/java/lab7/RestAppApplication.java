package lab7;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lab7.domain.Student;
import lab7.repository.StudentRepository;

@SpringBootApplication
public class RestAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner demoData(StudentRepository studentRepository) {
        return args -> {
            // Dodawanie przykładowych studentów
            studentRepository.save(new Student("Jan", "Kowalski", "123456"));
            studentRepository.save(new Student("Anna", "Nowak", "654321"));
            studentRepository.save(new Student("Piotr", "Wójcik", "987654"));
            studentRepository.save(new Student("Zofia", "Wiśniewska", "112233"));


        };
    }
}