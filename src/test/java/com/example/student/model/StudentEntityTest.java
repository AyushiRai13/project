package com.example.student.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Student Class Encapsulation & Logic Unit Tests")
class StudentEntityTest {

    @Test
    @DisplayName("Test Student Constructor, Getters and Encapsulation")
    void testEncapsulationAndConstructors() {
        Student student = new Student("S101", "Aarav Sharma", "aarav@example.com", "B.Tech CSE", 92.0, 95.0, List.of("Java", "Python"));

        assertEquals("S101", student.getStudentId());
        assertEquals("Aarav Sharma", student.getStudentName());
        assertEquals("aarav@example.com", student.getEmail());
        assertEquals("B.Tech CSE", student.getCourseName());
        assertEquals(92.0, student.getProgrammingScore());
        assertEquals(95.0, student.getMathematicsScore());
        assertEquals(2, student.getTechnicalSkills().size());
        assertTrue(student.getTechnicalSkills().contains("Java"));
    }

    @Test
    @DisplayName("Requirement 1: Calculate Average Score")
    void testAverageScoreCalculation() {
        Student student = new Student("S101", "Aarav Sharma", "aarav@example.com", "B.Tech CSE", 92.0, 95.0);
        // (92 + 95) / 2 = 93.5
        assertEquals(93.5, student.calculateAverageScore());
    }

    @Test
    @DisplayName("Requirement 2: Performance Categories (Excellent, Good, Satisfactory, Needs Improvement)")
    void testPerformanceCategories() {
        // Excellent: 85 or above
        Student s1 = new Student("S1", "Test", "s1@test.com", "CS", 85.0, 85.0);
        assertEquals("Excellent", s1.getPerformanceCategory());

        // Good: 70 - 84.99
        Student s2 = new Student("S2", "Test", "s2@test.com", "CS", 70.0, 84.0);
        assertEquals("Good", s2.getPerformanceCategory());

        // Satisfactory: 50 - 69.99
        Student s3 = new Student("S3", "Test", "s3@test.com", "CS", 50.0, 69.0);
        assertEquals("Satisfactory", s3.getPerformanceCategory());

        // Needs Improvement: Below 50
        Student s4 = new Student("S4", "Test", "s4@test.com", "CS", 40.0, 48.0);
        assertEquals("Needs Improvement", s4.getPerformanceCategory());
    }

    @Test
    @DisplayName("Requirement 3: Stronger Subject Identification")
    void testStrongerSubject() {
        Student sProg = new Student("S1", "Test", "s1@test.com", "CS", 95.0, 80.0);
        assertEquals("Programming", sProg.getStrongerSubject());

        Student sMath = new Student("S2", "Test", "s2@test.com", "CS", 75.0, 90.0);
        assertEquals("Mathematics", sMath.getStrongerSubject());

        Student sEqual = new Student("S3", "Test", "s3@test.com", "CS", 88.0, 88.0);
        assertEquals("Equal in Both", sEqual.getStrongerSubject());
    }

    @Test
    @DisplayName("Requirement 7: Needs Improvement in Programming (<50) but Math >= 75")
    void testProgrammingNeedsImprovementAndMathStrong() {
        // Programming < 50 AND Math >= 75 -> true
        Student sMatch = new Student("S1", "Test", "s1@test.com", "CS", 42.0, 82.0);
        assertTrue(sMatch.needsImprovementInProgrammingWithStrongMath());

        // Programming >= 50 AND Math >= 75 -> false
        Student sHighProg = new Student("S2", "Test", "s2@test.com", "CS", 52.0, 82.0);
        assertFalse(sHighProg.needsImprovementInProgrammingWithStrongMath());

        // Programming < 50 AND Math < 75 -> false
        Student sLowMath = new Student("S3", "Test", "s3@test.com", "CS", 42.0, 74.0);
        assertFalse(sLowMath.needsImprovementInProgrammingWithStrongMath());
    }

    @Test
    @DisplayName("Requirement 8: Score Validation between 0 and 100")
    void testScoreValidation() {
        Student student = new Student("S1", "Test", "s1@test.com", "CS", 50.0, 50.0);

        // Negative programming score
        assertThrows(IllegalArgumentException.class, () -> student.setProgrammingScore(-1.0));

        // Programming score > 100
        assertThrows(IllegalArgumentException.class, () -> student.setProgrammingScore(100.5));

        // Negative mathematics score
        assertThrows(IllegalArgumentException.class, () -> student.setMathematicsScore(-0.1));

        // Mathematics score > 100
        assertThrows(IllegalArgumentException.class, () -> student.setMathematicsScore(105.0));

        // Valid boundaries: 0 and 100
        assertDoesNotThrow(() -> student.setProgrammingScore(0.0));
        assertDoesNotThrow(() -> student.setProgrammingScore(100.0));
        assertDoesNotThrow(() -> student.setMathematicsScore(0.0));
        assertDoesNotThrow(() -> student.setMathematicsScore(100.0));
    }
}
