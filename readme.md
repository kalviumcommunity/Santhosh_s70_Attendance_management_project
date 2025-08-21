# Java Attendance Management System

## Overview
A simple, extensible Attendance Management System written in Java. This repository contains the code and documentation needed to record, view, and manage student attendance. The project is intended as a starting point for classroom or small-organization attendance workflows and can be adapted to use a local database, a file store, or integrated into a web or desktop UI.

## Key features
- Record daily attendance (present/absent/late)
- Maintain student roster and class lists
- View attendance history by student, date, or class
- Generate simple reports (daily totals, individual summaries)
- Lightweight persistence: configurable to use SQLite, MySQL, or flat files
- Extensible: modular design to plug UI (CLI / Swing / JavaFX / Spring Boot)

## Tech stack
- Java 8+ (recommended Java 11+)
- JDBC for database connectivity
- Optional: Maven or Gradle build
- Optional: SQLite (file-based) or MySQL/Postgres for production

## Prerequisites
- JDK installed (javac/java in PATH)
- Maven or Gradle if using the provided build scripts (optional)
- If using a DB: SQLite (file), or a running MySQL/Postgres instance
- Git (for cloning repo)

## Quick start (local, SQLite)
1. Clone the project:
    - git clone <repo-url>
2. Configure SQLite (default):
    - By default the app looks for `data/attendance.db`. Create the folder `data` in the project root.
    - The app will create tables if they do not exist, or run the provided SQL below once.

3. Build and run (Maven example):
    - mvn clean package
    - java -jar target/attendance-system.jar

4. Or run from source (simple javac/java):
    - javac -d out $(find src -name "*.java")
    - java -cp out com.example.attendance.Main

(Replace package and main class names with those used in your project.)

## Configuration
- application.properties or config.properties (example keys)
  - db.type=sqlite | mysql | memory
  - db.url=jdbc:sqlite:./data/attendance.db
  - db.username=
  - db.password=
  - app.port=8080 (if web)
- Environment variables may be supported for production secrets.

## Database schema (example)
Run these statements for SQLite / MySQL to create baseline tables:

CREATE TABLE students (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  student_id VARCHAR(50) UNIQUE NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  email VARCHAR(150)
);

CREATE TABLE classes (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  code VARCHAR(50) NOT NULL,
  name VARCHAR(150) NOT NULL,
  description TEXT
);

CREATE TABLE attendance (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  student_id INTEGER NOT NULL,
  class_id INTEGER NOT NULL,
  date DATE NOT NULL,
  status VARCHAR(20) NOT NULL, -- PRESENT / ABSENT / LATE / EXCUSED
  notes TEXT,
  FOREIGN KEY(student_id) REFERENCES students(id),
  FOREIGN KEY(class_id) REFERENCES classes(id)
);

Adjust types and auto-increment syntax for MySQL/Postgres if needed.

## Usage examples
- Add student: StudentService.addStudent(student)
- Mark attendance: AttendanceService.mark(studentId, classId, date, status)
- Query history: AttendanceService.getByStudent(studentId)

If the project includes a UI:
- CLI: follow menu prompts
- Swing/JavaFX: launch GUI with `java -jar` and interact
- Spring Boot web app: open http://localhost:8080

## Build options
- Maven:
  - mvn clean package
  - mvn test
- Gradle:
  - ./gradlew build
- Plain javac:
  - javac -d out $(find src -name "*.java")
  - java -cp out com.example.attendance.Main

## Tests
- Unit tests are implemented with JUnit (if present).
- Run `mvn test` or `./gradlew test`.

## Project structure (suggested)
- src/main/java/...        — application source
- src/main/resources/...   — configuration and SQL migrations
- src/test/java/...        — unit tests
- data/                    — local DB file (gitignored)
- docs/                    — design docs, ER diagrams

## Logging and debugging
- Use a logging framework (SLF4J + Logback) for production installs.
- Enable debug logging by updating config: logging.level=DEBUG

## Extending the project
- Add authentication/authorization for multi-user access
- Export reports as CSV or PDF
- Add REST API endpoints for integration with other systems
- Create a mobile or web frontend

## Contributing
- Fork the repo, create a feature branch, submit pull requests with clear descriptions.
- Follow code style and include unit tests where applicable.
- Open issues for bugs or feature requests.

## Troubleshooting
- DB connection errors: verify db.url, user, password and JDBC driver on classpath.
- Port conflicts (web): change app.port in config.
- Missing classes at runtime: ensure build includes all dependencies and correct packaging.

## License
Specify an open-source license (MIT, Apache-2.0, etc.). Include a LICENSE file in the repo.

## Contact
For questions or issues, open an issue in the repository or contact the maintainer listed in package metadata.

Tips:
- Keep sensitive credentials out of source control; use environment variables.
- Use migrations (Flyway/Liquibase) for schema evolution in production.
