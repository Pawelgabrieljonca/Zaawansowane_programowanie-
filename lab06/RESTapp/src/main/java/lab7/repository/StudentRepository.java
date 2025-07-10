package lab7.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lab7.domain.Student; // Adjust package based on your project

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}