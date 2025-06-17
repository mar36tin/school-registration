package db

import model.{Classroom, Enrolment, Student}
import scala.util.Try

class DBImplementation extends UtilityDatabase {
  val students: Map[String, Student] = Map(
    "stud1" -> Student("stud1", "Rob"),
    "stud2" -> Student("stud2", "Amu"),
    "stud3" -> Student("stud3", "Ann"),
    "stud4" -> Student("stud4", "Kay"),
  )
  val classrooms: Map[String, Classroom] = Map(
    "year1" -> Classroom("year1", "Year 1", 10),
    "year2" -> Classroom("year2", "Year 2", 10),
  )
  var enrolments: List[Enrolment] = List(
    Enrolment("stud1", "year1"),
    Enrolment("stud2", "year1"),
    Enrolment("stud3", "year1"),
    Enrolment("stud4", "year1")
  )

  def enrol(studentId: String, classroomId: String) = Try {
    Enrolment(studentId, classroomId) :: enrolments
  }

  def getStudent(id: String): Option[Student] = students.get(id)

  def getClassroom(id: String): Option[Classroom] = classrooms.get(id)
  
  def getEnrolments() = enrolments
}
