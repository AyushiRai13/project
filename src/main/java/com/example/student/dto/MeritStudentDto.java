package com.example.student.dto;

import com.example.student.model.Student;

public class MeritStudentDto {
    private int rank;
    private String studentId;
    private String studentName;
    private String courseName;
    private Double programmingScore;
    private Double mathematicsScore;
    private double averageScore;
    private String performanceCategory;
    private String strongerSubject;

    public MeritStudentDto() {
    }

    public MeritStudentDto(int rank, Student student) {
        this.rank = rank;
        this.studentId = student.getStudentId();
        this.studentName = student.getStudentName();
        this.courseName = student.getCourseName();
        this.programmingScore = student.getProgrammingScore();
        this.mathematicsScore = student.getMathematicsScore();
        this.averageScore = student.calculateAverageScore();
        this.performanceCategory = student.getPerformanceCategory();
        this.strongerSubject = student.getStrongerSubject();
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
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
}
