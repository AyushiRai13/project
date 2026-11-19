package com.example.student.dto;

import com.example.student.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentResponseDto {
    private String studentId;
    private String studentName;
    private String email;
    private String courseName;
    private Double programmingScore;
    private Double mathematicsScore;
    private double averageScore;
    private String performanceCategory;
    private String strongerSubject;
    private boolean needsImprovementInProgrammingWithStrongMath;
    private List<String> technicalSkills = new ArrayList<>();

    public StudentResponseDto() {
    }

    public static StudentResponseDto fromEntity(Student student) {
        StudentResponseDto dto = new StudentResponseDto();
        dto.setStudentId(student.getStudentId());
        dto.setStudentName(student.getStudentName());
        dto.setEmail(student.getEmail());
        dto.setCourseName(student.getCourseName());
        dto.setProgrammingScore(student.getProgrammingScore());
        dto.setMathematicsScore(student.getMathematicsScore());
        dto.setAverageScore(student.calculateAverageScore());
        dto.setPerformanceCategory(student.getPerformanceCategory());
        dto.setStrongerSubject(student.getStrongerSubject());
        dto.setNeedsImprovementInProgrammingWithStrongMath(student.needsImprovementInProgrammingWithStrongMath());
        dto.setTechnicalSkills(student.getTechnicalSkills() != null ? new ArrayList<>(student.getTechnicalSkills()) : new ArrayList<>());
        return dto;
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

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public String getPerformanceCategory() {
        return performanceCategory;
    }

    public void setPerformanceCategory(String performanceCategory) {
        this.performanceCategory = performanceCategory;
    }

    public String getStrongerSubject() {
        return strongerSubject;
    }

    public void setStrongerSubject(String strongerSubject) {
        this.strongerSubject = strongerSubject;
    }

    public boolean isNeedsImprovementInProgrammingWithStrongMath() {
        return needsImprovementInProgrammingWithStrongMath;
    }

    public void setNeedsImprovementInProgrammingWithStrongMath(boolean needsImprovementInProgrammingWithStrongMath) {
        this.needsImprovementInProgrammingWithStrongMath = needsImprovementInProgrammingWithStrongMath;
    }

    public List<String> getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(List<String> technicalSkills) {
        this.technicalSkills = technicalSkills;
    }
}
