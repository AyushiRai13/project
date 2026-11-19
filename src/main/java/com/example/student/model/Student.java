package com.example.student.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Student Entity class representing a student.
 * Encapsulates all student attributes, constructors, getters, setters,
 * validation, and business object methods.
 */
@Entity
@Table(name = "students")
public class Student {

    @Id
    @Column(name = "student_id", nullable = false, length = 50)
    @NotBlank(message = "Student ID cannot be blank")
    private String studentId;

    @Column(name = "student_name", nullable = false, length = 100)
    @NotBlank(message = "Student name cannot be blank")
    private String studentName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "course_name", nullable = false, length = 100)
    @NotBlank(message = "Course name cannot be blank")
    private String courseName;

    @Column(name = "programming_score", nullable = false)
    @NotNull(message = "Programming score cannot be null")
    @Min(value = 0, message = "Programming score must be at least 0")
    @Max(value = 100, message = "Programming score cannot exceed 100")
    private Double programmingScore;

    @Column(name = "mathematics_score", nullable = false)
    @NotNull(message = "Mathematics score cannot be null")
    @Min(value = 0, message = "Mathematics score must be at least 0")
    @Max(value = 100, message = "Mathematics score cannot exceed 100")
    private Double mathematicsScore;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "student_technical_skills",
            joinColumns = @JoinColumn(name = "student_id")
    )
    @Column(name = "skill", nullable = false, length = 100)
    private List<String> technicalSkills = new ArrayList<>();

    // Default Constructor
    public Student() {
    }

    // Constructor without skills list
    public Student(String studentId, String studentName, String email, String courseName,
                   Double programmingScore, Double mathematicsScore) {
        setStudentId(studentId);
        setStudentName(studentName);
        setEmail(email);
        setCourseName(courseName);
        setProgrammingScore(programmingScore);
        setMathematicsScore(mathematicsScore);
        this.technicalSkills = new ArrayList<>();
    }

    // Full Parameterized Constructor
    public Student(String studentId, String studentName, String email, String courseName,
                   Double programmingScore, Double mathematicsScore, List<String> technicalSkills) {
        setStudentId(studentId);
        setStudentName(studentName);
        setEmail(email);
        setCourseName(courseName);
        setProgrammingScore(programmingScore);
        setMathematicsScore(mathematicsScore);
        setTechnicalSkills(technicalSkills);
    }

    // =========================================================================
    // Getters and Setters with Encapsulation & Validation (Requirement 8)
    // =========================================================================

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        this.studentId = studentId.trim();
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be null or empty");
        }
        this.studentName = studentName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        this.email = email.trim();
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be null or empty");
        }
        this.courseName = courseName.trim();
    }

    public Double getProgrammingScore() {
        return programmingScore;
    }

    public void setProgrammingScore(Double programmingScore) {
        validateScore("Programming", programmingScore);
        this.programmingScore = programmingScore;
    }

    public Double getMathematicsScore() {
        return mathematicsScore;
    }

    public void setMathematicsScore(Double mathematicsScore) {
        validateScore("Mathematics", mathematicsScore);
        this.mathematicsScore = mathematicsScore;
    }

    public List<String> getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(List<String> technicalSkills) {
        if (technicalSkills == null) {
            this.technicalSkills = new ArrayList<>();
        } else {
            this.technicalSkills = new ArrayList<>(technicalSkills);
        }
    }

    public void addSkill(String skill) {
        if (skill != null && !skill.trim().isEmpty()) {
            String trimmed = skill.trim();
            if (!this.technicalSkills.contains(trimmed)) {
                this.technicalSkills.add(trimmed);
            }
        }
    }

    public void removeSkill(String skill) {
        if (skill != null) {
            this.technicalSkills.remove(skill.trim());
        }
    }

    // Helper method for score validation (Requirement 8)
    private void validateScore(String subject, Double score) {
        if (score == null) {
            throw new IllegalArgumentException(subject + " score cannot be null");
        }
        if (score < 0.0 || score > 100.0) {
            throw new IllegalArgumentException(subject + " score must be between 0 and 100. Provided: " + score);
        }
    }

    // =========================================================================
    // Appropriate Object Methods (Requirements 1, 2, 3, 7)
    // =========================================================================

    /**
     * Requirement 1: Calculate student's average score
     * Formula: (programmingScore + mathematicsScore) / 2
     */
    public double calculateAverageScore() {
        double prog = (programmingScore != null) ? programmingScore : 0.0;
        double math = (mathematicsScore != null) ? mathematicsScore : 0.0;
        double avg = (prog + math) / 2.0;
        return Math.round(avg * 100.0) / 100.0;
    }

    /**
     * Requirement 2: Assign a performance category based on average:
     * - 85 or above: Excellent
     * - 70–84.99: Good
     * - 50–69.99: Satisfactory
     * - Below 50: Needs Improvement
     */
    public String getPerformanceCategory() {
        double avg = calculateAverageScore();
        if (avg >= 85.0) {
            return "Excellent";
        } else if (avg >= 70.0) {
            return "Good";
        } else if (avg >= 50.0) {
            return "Satisfactory";
        } else {
            return "Needs Improvement";
        }
    }

    /**
     * Requirement 3: Identify the stronger subject for each student
     * Compares programmingScore vs mathematicsScore
     */
    public String getStrongerSubject() {
        double prog = (programmingScore != null) ? programmingScore : 0.0;
        double math = (mathematicsScore != null) ? mathematicsScore : 0.0;
        if (prog > math) {
            return "Programming";
        } else if (math > prog) {
            return "Mathematics";
        } else {
            return "Equal in Both";
        }
    }

    /**
     * Requirement 7: Check if student needs improvement in programming (< 50)
     * but has a mathematics score of at least 75 (>= 75)
     */
    public boolean needsImprovementInProgrammingWithStrongMath() {
        double prog = (programmingScore != null) ? programmingScore : 0.0;
        double math = (mathematicsScore != null) ? mathematicsScore : 0.0;
        return (prog < 50.0 && math >= 75.0);
    }

    // Standard Object Methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", email='" + email + '\'' +
                ", courseName='" + courseName + '\'' +
                ", programmingScore=" + programmingScore +
                ", mathematicsScore=" + mathematicsScore +
                ", averageScore=" + calculateAverageScore() +
                ", performanceCategory='" + getPerformanceCategory() + '\'' +
                ", strongerSubject='" + getStrongerSubject() + '\'' +
                ", technicalSkills=" + technicalSkills +
                '}';
    }
}
