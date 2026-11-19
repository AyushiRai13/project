-- Database Schema for Student Management System
-- Database: student_management_db

CREATE DATABASE IF NOT EXISTS student_management_db;
USE student_management_db;

-- 1. Students Table
-- Prevents duplicate student IDs via PRIMARY KEY constraint
-- Enforces score validation between 0 and 100 via CHECK constraints
CREATE TABLE IF NOT EXISTS students (
    student_id VARCHAR(50) NOT NULL,
    student_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    course_name VARCHAR(100) NOT NULL,
    programming_score DECIMAL(5,2) NOT NULL CHECK (programming_score >= 0 AND programming_score <= 100),
    mathematics_score DECIMAL(5,2) NOT NULL CHECK (mathematics_score >= 0 AND mathematics_score <= 100),
    PRIMARY KEY (student_id)
);

-- 2. Technical Skills Table (Normalized one-to-many relationship)
CREATE TABLE IF NOT EXISTS student_technical_skills (
    student_id VARCHAR(50) NOT NULL,
    skill VARCHAR(100) NOT NULL,
    PRIMARY KEY (student_id, skill),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);
