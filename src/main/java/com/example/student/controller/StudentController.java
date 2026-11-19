package com.example.student.controller;

import com.example.student.dto.*;
import com.example.student.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Requirement 1, 2, 3: Fetch all students with average score, performance category & stronger subject
     */
    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    /**
     * Fetch student by ID (Requirement 4 & 8)
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable("id") String id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    /**
     * Requirement 8: Create student with validation & duplicate ID prevention
     */
    @PostMapping
    public ResponseEntity<StudentResponseDto> createStudent(@Valid @RequestBody StudentRequestDto request) {
        StudentResponseDto created = studentService.createStudent(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * Update existing student
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDto> updateStudent(@PathVariable("id") String id,
                                                           @Valid @RequestBody StudentRequestDto request) {
        return ResponseEntity.ok(studentService.updateStudent(id, request));
    }

    /**
     * Delete student
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") String id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Requirement 4: Search students by ID, email, course, or name
     */
    @GetMapping("/search")
    public ResponseEntity<List<StudentResponseDto>> searchStudents(
            @RequestParam(value = "query", required = false, defaultValue = "") String query) {
        return ResponseEntity.ok(studentService.searchStudents(query));
    }

    /**
     * Requirement 4: Search students by course
     */
    @GetMapping("/search/course")
    public ResponseEntity<List<StudentResponseDto>> searchByCourse(@RequestParam("name") String courseName) {
        return ResponseEntity.ok(studentService.searchByCourse(courseName));
    }

    /**
     * Requirement 4: Search student by email
     */
    @GetMapping("/search/email")
    public ResponseEntity<StudentResponseDto> searchByEmail(@RequestParam("address") String email) {
        return ResponseEntity.ok(studentService.searchByEmail(email));
    }

    /**
     * Requirement 5: Generate merit list sorted by average score descending
     */
    @GetMapping("/merit-list")
    public ResponseEntity<List<MeritStudentDto>> getMeritList() {
        return ResponseEntity.ok(studentService.getMeritList());
    }

    /**
     * Requirement 6: Students who share the exact same technical skill sets
     */
    @GetMapping("/identical-skills")
    public ResponseEntity<List<IdenticalSkillGroupDto>> getIdenticalSkills() {
        return ResponseEntity.ok(studentService.getStudentsWithIdenticalSkillSets());
    }

    /**
     * Requirement 6: Group students sharing each technical skill
     */
    @GetMapping("/grouped-by-skill")
    public ResponseEntity<List<SkillStudentGroupDto>> getGroupedBySkill() {
        return ResponseEntity.ok(studentService.getStudentsGroupedBySharedSkills());
    }

    /**
     * Requirement 6: Students who possess a given technical skill
     */
    @GetMapping("/by-skill/{skill}")
    public ResponseEntity<List<StudentResponseDto>> getStudentsBySkill(@PathVariable("skill") String skill) {
        return ResponseEntity.ok(studentService.getStudentsBySkill(skill));
    }

    /**
     * Requirement 7: Students needing improvement in programming (< 50) but have mathematics score >= 75
     */
    @GetMapping("/programming-needs-improvement-math-strong")
    public ResponseEntity<List<StudentResponseDto>> getStudentsNeedingProgImprovementWithStrongMath() {
        return ResponseEntity.ok(studentService.getStudentsNeedingProgImprovementWithStrongMath());
    }
}
