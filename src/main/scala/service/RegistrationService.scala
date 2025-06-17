package service

import db.DBImplementation
import model.{Classroom, Enrolment, Student}
import utility.Exceptions.{ClassroomFullException, NoClassroomException, NoStudentException}

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global

class RegistrationService(db: DBImplementation) {

  def registerStudent(studentId: String, classroomId: String): Future[Try[Enrolment]] = Future {
    Try{
      val student = getStudent(studentId).getOrElse(throw new NoStudentException)
      val classroom = getClass(classroomId).getOrElse(throw new NoClassroomException)

      isClassFull(classroomId) match
        case Failure(exception) => throw exception
        case Success(fullClassroom) if fullClassroom => throw new ClassroomFullException
        case Success(fullClassroom) => Enrolment(studentId, classroomId)
    }
  }

  def getClass(classId: String): Option[Classroom] = db.getClassroom(classId)

  def isClassFull(classId: String): Try[Boolean] = Try {
    val classroom = getClass(classId).getOrElse(throw new NoClassroomException)
    val enrolled = db.getEnrolments().count(_.classroomId == classId)
    enrolled >= classroom.capacity
  }

  def getStudent(studentId: String): Option[Student] = db.getStudent(studentId)

}