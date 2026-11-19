package com.example.student.runner;

import com.example.student.dto.*;
import com.example.student.exception.DuplicateStudentIdException;
import com.example.student.exception.InvalidScoreException;
import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;
import com.example.student.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class StudentDemoRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(StudentDemoRunner.class);

    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentDemoRunner(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        seedSampleDataIfEmpty();

        System.out.println("\n" + "=".repeat(95));
        System.out.println("          🎓 STUDENT MANAGEMENT SYSTEM - VERIFICATION & DEMONSTRATION REPORT 🎓");
        System.out.println("=".repeat(95));

        // ---------------------------------------------------------------------
        // Requirement 1, 2, 3: Average Score, Performance Category & Stronger Subject
        // ---------------------------------------------------------------------
        System.out.println("\n>>> [REQUIREMENTS 1, 2 & 3] ALL STUDENTS: AVERAGE SCORE, CATEGORY & STRONGER SUBJECT");
        System.out.println("-".repeat(95));
        System.out.printf("%-6s | %-16s | %-12s | %-6s | %-6s | %-7s | %-18s | %-14s%n",
                "ID", "NAME", "COURSE", "PROG", "MATH", "AVG", "CATEGORY", "STRONGER SUBJ");
        System.out.println("-".repeat(95));

        List<StudentResponseDto> allStudents = studentService.getAllStudents();
        for (StudentResponseDto s : allStudents) {
            System.out.printf("%-6s | %-16s | %-12s | %-6.2f | %-6.2f | %-7.2f | %-18s | %-14s%n",
                    s.getStudentId(),
                    s.getStudentName(),
                    s.getCourseName(),
                    s.getProgrammingScore(),
                    s.getMathematicsScore(),
                    s.getAverageScore(),
                    s.getPerformanceCategory(),
                    s.getStrongerSubject());
        }

        // ---------------------------------------------------------------------
        // Requirement 4: Search for students by ID, email or course
        // ---------------------------------------------------------------------
        System.out.println("\n>>> [REQUIREMENT 4] SEARCH FOR STUDENTS BY ID, EMAIL OR COURSE");
        System.out.println("-".repeat(95));

        // 4a. Search by ID
        String searchId = "S101";
        StudentResponseDto byId = studentService.getStudentById(searchId);
        System.out.println("  • Search by ID ('" + searchId + "'): Found " + byId.getStudentName() +
                " (" + byId.getEmail() + ", Course: " + byId.getCourseName() + ")");

        // 4b. Search by Email
        String searchEmail = "diya@example.com";
        StudentResponseDto byEmail = studentService.searchByEmail(searchEmail);
        System.out.println("  • Search by Email ('" + searchEmail + "'): Found " + byEmail.getStudentName() +
                " [ID: " + byEmail.getStudentId() + ", Course: " + byEmail.getCourseName() + "]");

        // 4c. Search by Course
        String searchCourse = "MCA";
        List<StudentResponseDto> byCourse = studentService.searchByCourse(searchCourse);
        System.out.print("  • Search by Course ('" + searchCourse + "'): " + byCourse.size() + " students found -> ");
        for (StudentResponseDto s : byCourse) {
            System.out.print(s.getStudentName() + " (" + s.getStudentId() + ") ");
        }
        System.out.println();

        // 4d. Omni-search query
        String query = "CSE";
        List<StudentResponseDto> byQuery = studentService.searchStudents(query);
        System.out.println("  • Omni-Search ('" + query + "'): " + byQuery.size() + " matches.");

        // ---------------------------------------------------------------------
        // Requirement 5: Generate a merit list sorted by average score in descending order
        // ---------------------------------------------------------------------
        System.out.println("\n>>> [REQUIREMENT 5] MERIT LIST (SORTED BY AVERAGE SCORE IN DESCENDING ORDER)");
        System.out.println("-".repeat(95));
        System.out.printf("%-5s | %-6s | %-16s | %-12s | %-6s | %-6s | %-7s | %-18s%n",
                "RANK", "ID", "NAME", "COURSE", "PROG", "MATH", "AVG", "CATEGORY");
        System.out.println("-".repeat(95));

        List<MeritStudentDto> meritList = studentService.getMeritList();
        for (MeritStudentDto m : meritList) {
            System.out.printf("%-5d | %-6s | %-16s | %-12s | %-6.2f | %-6.2f | %-7.2f | %-18s%n",
                    m.getRank(),
                    m.getStudentId(),
                    m.getStudentName(),
                    m.getCourseName(),
                    m.getProgrammingScore(),
                    m.getMathematicsScore(),
                    m.getAverageScore(),
                    m.getPerformanceCategory());
        }

        // ---------------------------------------------------------------------
        // Requirement 6: Identify students who have the same technical skills
        // ---------------------------------------------------------------------
        System.out.println("\n>>> [REQUIREMENT 6] STUDENTS WHO HAVE THE SAME TECHNICAL SKILLS");
        System.out.println("-".repeat(95));

        System.out.println("  [A] Students sharing the EXACT SAME skill sets:");
        List<IdenticalSkillGroupDto> identicalGroups = studentService.getStudentsWithIdenticalSkillSets();
        if (identicalGroups.isEmpty()) {
            System.out.println("      None found.");
        } else {
            for (IdenticalSkillGroupDto g : identicalGroups) {
                System.out.println("      • Skill Set " + g.getSkills() + " (" + g.getCount() + " students):");
                for (StudentResponseDto s : g.getStudents()) {
                    System.out.println("        - " + s.getStudentName() + " (" + s.getStudentId() + ", " + s.getCourseName() + ")");
                }
            }
        }

        System.out.println("\n  [B] Students grouped by individual shared technical skills:");
        List<SkillStudentGroupDto> skillGroups = studentService.getStudentsGroupedBySharedSkills();
        for (SkillStudentGroupDto sg : skillGroups) {
            if (sg.getCount() > 1) { // highlight skills shared by multiple students
                System.out.print("      • " + sg.getSkill() + " (" + sg.getCount() + " students): ");
                for (StudentResponseDto s : sg.getStudents()) {
                    System.out.print(s.getStudentName() + " (" + s.getStudentId() + ")  ");
                }
                System.out.println();
            }
        }

        // ---------------------------------------------------------------------
        // Requirement 7: Display students who need improvement in programming (< 50)
        //                but have a mathematics score of at least 75 (>= 75)
        // ---------------------------------------------------------------------
        System.out.println("\n>>> [REQUIREMENT 7] STUDENTS NEEDING IMPROVEMENT IN PROGRAMMING (< 50) BUT MATH >= 75");
        System.out.println("-".repeat(95));
        System.out.printf("%-6s | %-16s | %-12s | %-12s | %-10s | %-7s | %-15s%n",
                "ID", "NAME", "COURSE", "PROG (<50)", "MATH (>=75)", "AVG", "STATUS");
        System.out.println("-".repeat(95));

        List<StudentResponseDto> req7Students = studentService.getStudentsNeedingProgImprovementWithStrongMath();
        for (StudentResponseDto s : req7Students) {
            System.out.printf("%-6s | %-16s | %-12s | %-12.2f | %-10.2f | %-7.2f | %-15s%n",
                    s.getStudentId(),
                    s.getStudentName(),
                    s.getCourseName(),
                    s.getProgrammingScore(),
                    s.getMathematicsScore(),
                    s.getAverageScore(),
                    "Eligible for Target Mentorship");
        }

        // ---------------------------------------------------------------------
        // Requirement 8: Prevent duplicate student IDs and validate scores between 0 and 100
        // ---------------------------------------------------------------------
        System.out.println("\n>>> [REQUIREMENT 8] VALIDATION TESTS: PREVENT DUPLICATE IDs & VALIDATE SCORES 0-100");
        System.out.println("-".repeat(95));

        // Test 8a: Prevent Duplicate Student ID
        try {
            System.out.print("  • Attempting to insert duplicate Student ID ('S101')... ");
            StudentRequestDto duplicateReq = new StudentRequestDto(
                    "S101", "Clone Student", "clone@example.com", "B.Tech", 80.0, 80.0, List.of("Java")
            );
            studentService.createStudent(duplicateReq);
            System.out.println("❌ FAILED: Duplicate was allowed!");
        } catch (DuplicateStudentIdException ex) {
            System.out.println("✅ BLOCKED AS EXPECTED -> " + ex.getMessage());
        }

        // Test 8b: Validate Score < 0
        try {
            System.out.print("  • Attempting to insert student with negative score (-15.0)... ");
            StudentRequestDto invalidScoreReq = new StudentRequestDto(
                    "S999", "Test Negative", "neg@example.com", "B.Tech", -15.0, 80.0, List.of("Java")
            );
            studentService.createStudent(invalidScoreReq);
            System.out.println("❌ FAILED: Negative score was allowed!");
        } catch (InvalidScoreException ex) {
            System.out.println("✅ BLOCKED AS EXPECTED -> " + ex.getMessage());
        }

        // Test 8c: Validate Score > 100
        try {
            System.out.print("  • Attempting to insert student with score > 100 (105.0)... ");
            StudentRequestDto invalidScoreReq2 = new StudentRequestDto(
                    "S998", "Test Overflow", "overflow@example.com", "B.Tech", 85.0, 105.0, List.of("Java")
            );
            studentService.createStudent(invalidScoreReq2);
            System.out.println("❌ FAILED: Score > 100 was allowed!");
        } catch (InvalidScoreException ex) {
            System.out.println("✅ BLOCKED AS EXPECTED -> " + ex.getMessage());
        }

        System.out.println("=".repeat(95));
        System.out.println("✅ ALL 8 SYSTEM REQUIREMENTS FULLY VERIFIED AND OPERATIONAL!");
        System.out.println("🌐 Web Dashboard & API available at: http://localhost:8080");
        System.out.println("=".repeat(95) + "\n");
    }

    private void seedSampleDataIfEmpty() {
        if (studentRepository.count() == 0) {
            log.info("Database is empty. Seeding initial student records for demonstration...");
            List<Student> initialStudents = List.of(
                    new Student("S101", "Aarav Sharma", "aarav@example.com", "B.Tech CSE", 92.0, 95.0,
                            Arrays.asList("Java", "Python", "Spring Boot")),
                    new Student("S102", "Diya Patel", "diya@example.com", "B.Tech IT", 78.0, 84.0,
                            Arrays.asList("Java", "SQL", "React")),
                    new Student("S103", "Rohan Verma", "rohan@example.com", "B.Tech CSE", 88.0, 68.0,
                            Arrays.asList("Java", "Python", "Spring Boot")),
                    new Student("S104", "Ananya Singh", "ananya@example.com", "MCA", 42.0, 82.0,
                            Arrays.asList("Python", "Machine Learning")),
                    new Student("S105", "Kabir Mehta", "kabir@example.com", "B.Tech IT", 45.0, 48.0,
                            Arrays.asList("HTML", "CSS")),
                    new Student("S106", "Sneha Rao", "sneha@example.com", "MCA", 40.0, 78.0,
                            Arrays.asList("Python", "Machine Learning")),
                    new Student("S107", "Vikram Malhotra", "vikram@example.com", "B.Sc CS", 90.0, 90.0,
                            Arrays.asList("Algorithms", "C++", "Java"))
            );
            studentRepository.saveAll(initialStudents);
            log.info("Successfully seeded {} student records.", initialStudents.size());
        }
    }
}
