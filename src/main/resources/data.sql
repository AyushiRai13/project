-- Sample Seed Data for testing all requirements

-- Clean previous test data (optional or idempotent)
DELETE FROM student_technical_skills;
DELETE FROM students;

-- Insert Students
-- S101: Average = 93.5 (Excellent), Math > Programming
INSERT INTO students (student_id, student_name, email, course_name, programming_score, mathematics_score)
VALUES ('S101', 'Aarav Sharma', 'aarav@example.com', 'B.Tech CSE', 92.00, 95.00);

-- S102: Average = 81.0 (Good), Math > Programming
INSERT INTO students (student_id, student_name, email, course_name, programming_score, mathematics_score)
VALUES ('S102', 'Diya Patel', 'diya@example.com', 'B.Tech IT', 78.00, 84.00);

-- S103: Average = 78.0 (Good), Programming > Math (Same skills as S101: Java, Python, Spring Boot)
INSERT INTO students (student_id, student_name, email, course_name, programming_score, mathematics_score)
VALUES ('S103', 'Rohan Verma', 'rohan@example.com', 'B.Tech CSE', 88.00, 68.00);

-- S104: Average = 62.0 (Satisfactory), Programming < 50 (42.00) AND Math >= 75 (82.00) -> Requirement 7 match!
INSERT INTO students (student_id, student_name, email, course_name, programming_score, mathematics_score)
VALUES ('S104', 'Ananya Singh', 'ananya@example.com', 'MCA', 42.00, 82.00);

-- S105: Average = 46.5 (Needs Improvement), Programming < 50 (45.00) and Math < 75 (48.00)
INSERT INTO students (student_id, student_name, email, course_name, programming_score, mathematics_score)
VALUES ('S105', 'Kabir Mehta', 'kabir@example.com', 'B.Tech IT', 45.00, 48.00);

-- S106: Average = 59.0 (Satisfactory), Programming < 50 (40.00) AND Math >= 75 (78.00) -> Requirement 7 match! (Same skills as S104: Python, Machine Learning)
INSERT INTO students (student_id, student_name, email, course_name, programming_score, mathematics_score)
VALUES ('S106', 'Sneha Rao', 'sneha@example.com', 'MCA', 40.00, 78.00);

-- S107: Average = 90.0 (Excellent), Equal scores (90.00 & 90.00)
INSERT INTO students (student_id, student_name, email, course_name, programming_score, mathematics_score)
VALUES ('S107', 'Vikram Malhotra', 'vikram@example.com', 'B.Sc CS', 90.00, 90.00);

-- Insert Technical Skills
INSERT INTO student_technical_skills (student_id, skill) VALUES
('S101', 'Java'), ('S101', 'Python'), ('S101', 'Spring Boot'),
('S102', 'Java'), ('S102', 'SQL'), ('S102', 'React'),
('S103', 'Java'), ('S103', 'Python'), ('S103', 'Spring Boot'),
('S104', 'Python'), ('S104', 'Machine Learning'),
('S105', 'HTML'), ('S105', 'CSS'),
('S106', 'Python'), ('S106', 'Machine Learning'),
('S107', 'C++'), ('S107', 'Java'), ('S107', 'Algorithms');
