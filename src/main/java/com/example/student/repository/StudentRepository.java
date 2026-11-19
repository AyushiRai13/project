package com.example.student.repository;

import com.example.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    // Requirement 4: Search by Email
    Optional<Student> findByEmailIgnoreCase(String email);

    // Requirement 4: Search by Course Name
    List<Student> findByCourseNameIgnoreCase(String courseName);

    // Requirement 4: Omni-Search by ID, email, or course (and student name)
    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.studentId) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(s.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(s.courseName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(s.studentName) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Student> searchByIdOrEmailOrCourse(@Param("query") String query);

    // Requirement 5: Merit list sorted by average score descending
    @Query("SELECT s FROM Student s ORDER BY (s.programmingScore + s.mathematicsScore) DESC")
    List<Student> findAllOrderByAverageScoreDesc();

    // Requirement 6: Find students containing a specific skill
    @Query("SELECT DISTINCT s FROM Student s JOIN s.technicalSkills skill WHERE LOWER(skill) = LOWER(:skill)")
    List<Student> findBySkill(@Param("skill") String skill);

    // Requirement 7: Programming < 50 AND Mathematics >= 75
    @Query("SELECT s FROM Student s WHERE s.programmingScore < 50.0 AND s.mathematicsScore >= 75.0")
    List<Student> findStudentsNeedingProgImprovementWithStrongMath();

    // Requirement 8: Check if email already exists for another student
    boolean existsByEmailIgnoreCase(String email);
}
