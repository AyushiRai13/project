package com.example.student.dto;

import java.util.List;

public class SkillStudentGroupDto {
    private String skill;
    private int count;
    private List<StudentResponseDto> students;

    public SkillStudentGroupDto() {
    }

    public SkillStudentGroupDto(String skill, List<StudentResponseDto> students) {
        this.skill = skill;
        this.students = students;
        this.count = (students != null) ? students.size() : 0;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<StudentResponseDto> getStudents() {
        return students;
    }

    public void setStudents(List<StudentResponseDto> students) {
        this.students = students;
        this.count = (students != null) ? students.size() : 0;
    }
}
