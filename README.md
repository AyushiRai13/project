# 🎓 Student Management System

A full-stack Academic Assessment & Student Management System built using **Spring Boot 3 Web**, **Spring Data JPA**, and **MySQL**.

---

## 🌟 Key Features & Requirements Covered

1. **Calculate Average Score**: Automatically computes `(programmingScore + mathematicsScore) / 2.0`.
2. **Performance Categories**:
   - `≥ 85.0`: **Excellent**
   - `70.0 – 84.99`: **Good**
   - `50.0 – 69.99`: **Satisfactory**
   - `< 50.0`: **Needs Improvement**
3. **Stronger Subject Identification**: Compares Programming vs. Mathematics (or Equal in Both).
4. **Search Capabilities**: Omni-search and dedicated endpoints for searching by **ID**, **Email**, or **Course Name**.
5. **Merit List Generation**: Ranked list of students sorted by average score in descending order.
6. **Technical Skill Analysis**:
   - Identifies student groups with the exact same skill sets.
   - Groups students sharing each technical skill.
7. **Special Mentorship Filter**: Displays students who need improvement in programming (`score < 50`) but excel in mathematics (`score >= 75`).
8. **Robust Data Validation**:
   - Rejects duplicate Student IDs (Primary Key enforcement & service validation).
   - Enforces scores between `0` and `100` via entity bean validation, custom setter checks, and database constraints.

---

## 🛠️ Tech Stack

- **Java 21 / OpenJDK 27**
- **Spring Boot 3.3.5** (`spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `spring-boot-starter-validation`)
- **MySQL Database**
- **Frontend**: Bootstrap 5 Responsive Web Dashboard (HTML5, Vanilla JS, Bootstrap Icons)
- **Maven**: Build and dependency management

---

## 🚀 How to Run

### 1. Database Setup
Make sure MySQL is running locally with credentials:
- **Username**: `root`
- **Password**: `12345678`
- **Port**: `3306`

Database schema and tables (`student_management_db`) are configured in `src/main/resources/application.properties` with automatic creation and updates.

### 2. Start Application
```bash
./mvnw spring-boot:run
```
Alternatively, package and run JAR:
```bash
./mvnw clean package
java -jar target/student-management-system-0.0.1-SNAPSHOT.jar
```

### 3. Open Web UI
Visit: **[http://localhost:8080](http://localhost:8080)**

---

## 📡 REST API Reference

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/students` | Get all students with computed metrics |
| `GET` | `/api/students/{id}` | Get student by ID |
| `POST` | `/api/students` | Add a new student (validates ID & scores) |
| `PUT` | `/api/students/{id}` | Update student details |
| `DELETE` | `/api/students/{id}` | Delete a student |
| `GET` | `/api/students/search?query={q}` | Omni-search by ID, Email, Course, or Name |
| `GET` | `/api/students/merit-list` | Merit list ranked by average score descending |
| `GET` | `/api/students/identical-skills` | Groups of students with identical skill sets |
| `GET` | `/api/students/grouped-by-skill` | Students grouped by individual shared skill |
| `GET` | `/api/students/programming-needs-improvement-math-strong` | Students with Prog < 50 & Math >= 75 |

---

## 🧪 Run Tests
```bash
./mvnw test
```
Runs all 13 unit and integration tests verifying all 8 requirements.
