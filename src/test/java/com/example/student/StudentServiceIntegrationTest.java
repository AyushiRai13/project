package com.example.student;

import com.example.student.dto.IdenticalSkillGroupDto;
import com.example.student.dto.MeritStudentDto;
import com.example.student.dto.StudentRequestDto;
import com.example.student.dto.StudentResponseDto;
import com.example.student.exception.DuplicateStudentIdException;
import com.example.student.exception.InvalidScoreException;
import com.example.student.service.StudentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Spring Boot Service & Database Integration Tests")
class StudentServiceIntegrationTest {

    @Autowired
    private StudentService studentService;

    @Test
    @DisplayName("Verify Database Connection & Sample Students are Loaded")
    void testSampleStudentsLoaded() {
        List<StudentResponseDto> students = studentService.getAllStudents();
        assertNotNull(students);
        assertFalse(students.isEmpty(), "Database should have seeded records");
    }

    @Test
    @DisplayName("Requirement 4: Search by ID, Email, and Course")
    void testSearchRequirements() {
        StudentResponseDto byId = studentService.getStudentById("S101");
        assertNotNull(byId);
        assertEquals("S101", byId.getStudentId());

        StudentResponseDto byEmail = studentService.searchByEmail("diya@example.com");
        assertNotNull(byEmail);
        assertEquals("S102", byEmail.getStudentId());

        List<StudentResponseDto> byCourse = studentService.searchByCourse("MCA");
        assertFalse(byCourse.isEmpty());
    }

    @Test
    @DisplayName("Requirement 5: Verify Merit List Sorting in Descending Order")
    void testMeritListDescending() {
        List<MeritStudentDto> meritList = studentService.getMeritList();
        assertFalse(meritList.isEmpty());

        for (int i = 0; i < meritList.size() - 1; i++) {
            assertTrue(
                    meritList.get(i).getAverageScore() >= meritList.get(i + 1).getAverageScore(),
                    "Merit list must be sorted in descending order of average score"
            );
        }
    }

    @Test
    @DisplayName("Requirement 6: Verify Identical Technical Skill Groups")
    void testIdenticalSkillSets() {
        List<IdenticalSkillGroupDto> groups = studentService.getStudentsWithIdenticalSkillSets();
        assertNotNull(groups);
        for (IdenticalSkillGroupDto group : groups) {
            assertTrue(group.getCount() >= 2, "Identical skill groups must contain 2 or more students");
        }
    }

    @Test
    @DisplayName("Requirement 7: Verify Students Needing Prog Improvement (<50) but Math >= 75")
    void testProgNeedsImprovementMathStrong() {
        List<StudentResponseDto> students = studentService.getStudentsNeedingProgImprovementWithStrongMath();
        for (StudentResponseDto s : students) {
            assertTrue(s.getProgrammingScore() < 50.0, "Programming score must be < 50");
            assertTrue(s.getMathematicsScore() >= 75.0, "Mathematics score must be >= 75");
        }
    }

    @Test
    @DisplayName("Requirement 8: Prevent Duplicate Student IDs")
    void testDuplicateStudentIdPrevention() {
        StudentRequestDto dup = new StudentRequestDto(
                "S101", "Duplicate Test", "dup@example.com", "CSE", 80.0, 80.0, List.of("Java")
        );
        assertThrows(DuplicateStudentIdException.class, () -> studentService.createStudent(dup));
    }

    @Test
    @DisplayName("Requirement 8: Reject Invalid Scores < 0 and > 100")
    void testScoreValidation() {
        StudentRequestDto negative = new StudentRequestDto(
                "S999", "Neg Score", "neg@example.com", "CSE", -10.0, 80.0, List.of("Java")
        );
        assertThrows(InvalidScoreException.class, () -> studentService.createStudent(negative));

        StudentRequestDto overflow = new StudentRequestDto(
                "S998", "Over Score", "over@example.com", "CSE", 80.0, 105.0, List.of("Java")
        );
        assertThrows(InvalidScoreException.class, () -> studentService.createStudent(overflow));
    }
}
