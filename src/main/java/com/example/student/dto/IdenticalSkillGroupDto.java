package com.example.student.dto;

import java.util.List;

public class IdenticalSkillGroupDto {
    private List<String> skills;
    private int count;
    private List<StudentResponseDto> students;

    public IdenticalSkillGroupDto() {
    }

    public IdenticalSkillGroupDto(List<String> skills, List<StudentResponseDto> students) {
        this.skills = skills;
        this.students = students;
        this.count = (students != null) ? students.size() : 0;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
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
