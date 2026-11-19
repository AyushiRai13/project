package com.example.student.dto;

import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRequestDto {

    @NotBlank(message = "Student ID cannot be blank")
    private String studentId;

    @NotBlank(message = "Student name cannot be blank")
    private String studentName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Course name cannot be blank")
    private String courseName;

    @NotNull(message = "Programming score cannot be null")
    @Min(value = 0, message = "Programming score must be at least 0")
    @Max(value = 100, message = "Programming score cannot exceed 100")
    private Double programmingScore;

    @NotNull(message = "Mathematics score cannot be null")
    @Min(value = 0, message = "Mathematics score must be at least 0")
    @Max(value = 100, message = "Mathematics score cannot exceed 100")
    private Double mathematicsScore;

    private List<String> technicalSkills = new ArrayList<>();

    public StudentRequestDto() {
    }

    public StudentRequestDto(String studentId, String studentName, String email, String courseName,
                             Double programmingScore, Double mathematicsScore, List<String> technicalSkills) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
        this.courseName = courseName;
        this.programmingScore = programmingScore;
        this.mathematicsScore = mathematicsScore;
        this.technicalSkills = technicalSkills;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Double getProgrammingScore() {
        return programmingScore;
    }

    public void setProgrammingScore(Double programmingScore) {
        this.programmingScore = programmingScore;
    }

    public Double getMathematicsScore() {
        return mathematicsScore;
    }

    public void setMathematicsScore(Double mathematicsScore) {
        this.mathematicsScore = mathematicsScore;
    }

    public List<String> getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(List<String> technicalSkills) {
        this.technicalSkills = technicalSkills;
    }
}
