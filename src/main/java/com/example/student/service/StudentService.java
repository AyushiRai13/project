package com.example.student.service;

import com.example.student.dto.*;
import com.example.student.exception.DuplicateStudentIdException;
import com.example.student.exception.InvalidScoreException;
import com.example.student.exception.StudentNotFoundException;
import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Requirement 8: Prevent duplicate student IDs and validate scores between 0 and 100.
     */
    public StudentResponseDto createStudent(StudentRequestDto request) {
        // Validate score boundaries (Requirement 8)
        validateScores(request.getProgrammingScore(), request.getMathematicsScore());

        // Validate unique student ID (Requirement 8)
        if (studentRepository.existsById(request.getStudentId().trim())) {
            throw new DuplicateStudentIdException("Student ID '" + request.getStudentId() + "' already exists! Duplicate student IDs are not allowed.");
        }

        // Validate unique email
        if (studentRepository.existsByEmailIgnoreCase(request.getEmail().trim())) {
            throw new IllegalArgumentException("Email '" + request.getEmail() + "' is already registered with another student.");
        }

        Student student = new Student(
                request.getStudentId().trim(),
                request.getStudentName().trim(),
                request.getEmail().trim(),
                request.getCourseName().trim(),
                request.getProgrammingScore(),
                request.getMathematicsScore(),
                request.getTechnicalSkills()
        );

        Student saved = studentRepository.save(student);
        return StudentResponseDto.fromEntity(saved);
    }

    public StudentResponseDto updateStudent(String studentId, StudentRequestDto request) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student with ID '" + studentId + "' not found."));

        validateScores(request.getProgrammingScore(), request.getMathematicsScore());

        // Check if new email is used by another student
        Optional<Student> existingEmail = studentRepository.findByEmailIgnoreCase(request.getEmail().trim());
        if (existingEmail.isPresent() && !existingEmail.get().getStudentId().equalsIgnoreCase(studentId)) {
            throw new IllegalArgumentException("Email '" + request.getEmail() + "' is already in use by another student.");
        }

        student.setStudentName(request.getStudentName().trim());
        student.setEmail(request.getEmail().trim());
        student.setCourseName(request.getCourseName().trim());
        student.setProgrammingScore(request.getProgrammingScore());
        student.setMathematicsScore(request.getMathematicsScore());
        student.setTechnicalSkills(request.getTechnicalSkills());

        Student updated = studentRepository.save(student);
        return StudentResponseDto.fromEntity(updated);
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(StudentResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StudentResponseDto getStudentById(String studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student with ID '" + studentId + "' not found."));
        return StudentResponseDto.fromEntity(student);
    }

    public void deleteStudent(String studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException("Student with ID '" + studentId + "' not found.");
        }
        studentRepository.deleteById(studentId);
    }

    /**
     * Requirement 4: Search for students by ID, email or course
     */
    @Transactional(readOnly = true)
    public List<StudentResponseDto> searchStudents(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllStudents();
        }
        return studentRepository.searchByIdOrEmailOrCourse(query.trim()).stream()
                .map(StudentResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDto> searchByCourse(String course) {
        return studentRepository.findByCourseNameIgnoreCase(course.trim()).stream()
                .map(StudentResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StudentResponseDto searchByEmail(String email) {
        Student student = studentRepository.findByEmailIgnoreCase(email.trim())
                .orElseThrow(() -> new StudentNotFoundException("Student with email '" + email + "' not found."));
        return StudentResponseDto.fromEntity(student);
    }

    /**
     * Requirement 5: Generate a merit list sorted by average score in descending order
     */
    @Transactional(readOnly = true)
    public List<MeritStudentDto> getMeritList() {
        List<Student> students = studentRepository.findAllOrderByAverageScoreDesc();
        List<MeritStudentDto> meritList = new ArrayList<>();
        int rank = 1;
        for (Student s : students) {
            meritList.add(new MeritStudentDto(rank++, s));
        }
        return meritList;
    }

    /**
     * Requirement 6: Identify students who have the same technical skills.
     * Part A: Group students who possess identical skill sets.
     */
    @Transactional(readOnly = true)
    public List<IdenticalSkillGroupDto> getStudentsWithIdenticalSkillSets() {
        List<Student> students = studentRepository.findAll();

        // Map normalized sorted skills signature -> List of Students
        Map<String, List<Student>> signatureMap = new LinkedHashMap<>();
        Map<String, List<String>> normalizedSkillsMap = new LinkedHashMap<>();

        for (Student s : students) {
            List<String> sortedSkills = s.getTechnicalSkills().stream()
                    .map(String::trim)
                    .filter(sk -> !sk.isEmpty())
                    .sorted(String.CASE_INSENSITIVE_ORDER)
                    .collect(Collectors.toList());

            String signature = sortedSkills.stream()
                    .map(String::toLowerCase)
                    .collect(Collectors.joining("|"));

            signatureMap.computeIfAbsent(signature, k -> new ArrayList<>()).add(s);
            normalizedSkillsMap.putIfAbsent(signature, sortedSkills);
        }

        // Filter groups having 2 or more students (sharing the exact same skill set)
        List<IdenticalSkillGroupDto> identicalGroups = new ArrayList<>();
        for (Map.Entry<String, List<Student>> entry : signatureMap.entrySet()) {
            if (entry.getValue().size() > 1 && !entry.getKey().isEmpty()) {
                List<StudentResponseDto> studentDtos = entry.getValue().stream()
                        .map(StudentResponseDto::fromEntity)
                        .collect(Collectors.toList());
                identicalGroups.add(new IdenticalSkillGroupDto(normalizedSkillsMap.get(entry.getKey()), studentDtos));
            }
        }

        return identicalGroups;
    }

    /**
     * Requirement 6: Part B: Group students sharing each technical skill
     */
    @Transactional(readOnly = true)
    public List<SkillStudentGroupDto> getStudentsGroupedBySharedSkills() {
        List<Student> students = studentRepository.findAll();
        Map<String, List<Student>> skillMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for (Student s : students) {
            for (String skill : s.getTechnicalSkills()) {
                String cleanSkill = skill.trim();
                if (!cleanSkill.isEmpty()) {
                    skillMap.computeIfAbsent(cleanSkill, k -> new ArrayList<>()).add(s);
                }
            }
        }

        List<SkillStudentGroupDto> groups = new ArrayList<>();
        for (Map.Entry<String, List<Student>> entry : skillMap.entrySet()) {
            List<StudentResponseDto> studentDtos = entry.getValue().stream()
                    .map(StudentResponseDto::fromEntity)
                    .collect(Collectors.toList());
            groups.add(new SkillStudentGroupDto(entry.getKey(), studentDtos));
        }

        return groups;
    }

    /**
     * Requirement 6: Part C: Find students sharing a specific skill
     */
    @Transactional(readOnly = true)
    public List<StudentResponseDto> getStudentsBySkill(String skill) {
        return studentRepository.findBySkill(skill.trim()).stream()
                .map(StudentResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Requirement 7: Display students who need improvement in programming (< 50)
     * but have a mathematics score of at least 75 (>= 75).
     */
    @Transactional(readOnly = true)
    public List<StudentResponseDto> getStudentsNeedingProgImprovementWithStrongMath() {
        return studentRepository.findStudentsNeedingProgImprovementWithStrongMath().stream()
                .map(StudentResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Requirement 8: Score validation helper (0 to 100)
     */
    public void validateScores(Double programmingScore, Double mathematicsScore) {
        if (programmingScore == null) {
            throw new InvalidScoreException("Programming score cannot be null.");
        }
        if (programmingScore < 0.0 || programmingScore > 100.0) {
            throw new InvalidScoreException("Programming score must be between 0 and 100. Provided: " + programmingScore);
        }
        if (mathematicsScore == null) {
            throw new InvalidScoreException("Mathematics score cannot be null.");
        }
        if (mathematicsScore < 0.0 || mathematicsScore > 100.0) {
            throw new InvalidScoreException("Mathematics score must be between 0 and 100. Provided: " + mathematicsScore);
        }
    }
}
