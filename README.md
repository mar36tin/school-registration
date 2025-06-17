# School Registration System — Scala Mini Project

This is a small functional Scala app simulating a simple **school registration system**. The app models students, classrooms, and enrollments, and lets students register for classes and query their registrations.

It’s designed as a **2-hour exercise** to practice:

✅ Functional data modelling  
✅ Futures for async operations  
✅ Try and Option for safe computation  
✅ PartialFunctions for error handling  
✅ Higher-order functions and functional composition

---

## Features

- **Register a student to a class** (with capacity checks)
- **Query a student’s enrolled classes**
- **Handle errors gracefully using Try and PartialFunction**
- **Simulate async DB calls using Future**

---

## Getting Started

### Requirements

- Scala 2.13 or 3.x
- SBT (or any Scala build tool)

### How to run

1️⃣ Clone or copy this project.  
2️⃣ Open in your favorite IDE (e.g. IntelliJ IDEA with Scala plugin, VSCode + Metals).  
3️⃣ Run the `Main` object or test the service methods in a Scala REPL.

---

## Example

```scala
val service = new RegistrationService()

// Attempt to register Alice to Math
service.registerStudent("s1", "c1").onComplete {
  case Success(Success(enrollment)) => println(s"Enrolled: $enrollment")
  case Success(Failure(ex)) => println(service.errorHandler(ex))
  case Failure(ex) => println(s"Unexpected failure: ${ex.getMessage}")
}

// Query Alice's enrollments
service.findStudentEnrollments("s1").onComplete {
  case Success(classes) => println(s"Alice is enrolled in: ${classes.map(_.name).mkString(", ")}")
  case Failure(ex) => println(s"Query failed: ${ex.getMessage}")
}
```

---

## Architecture
### Data Models:

Student(id, name)

Classroom(id, name, capacity)

Enrollment(studentId, classroomId)

### Service Methods:

registerStudent(studentId, classroomId): Future[Try[Enrollment]]

findStudentEnrollments(studentId): Future[List[Classroom]]

### Error Handling:

Custom PartialFunction[Throwable, String] to map exceptions to messages

---

## Functional Concepts Covered
- Option to safely model missing data

- Try to model success/failure of operations

- Future for async DB simulation

- PartialFunction for clean error mapping

- Immutability in data models

- Higher-order and partially applied functions

---

## Extension Ideas
- Add persistence (e.g., in-memory storage that updates immutably)

- Add class capacity enforcement (count enrollments per class)

- Add removal of students from classes

- Add JSON I/O using a library like circe or play-json