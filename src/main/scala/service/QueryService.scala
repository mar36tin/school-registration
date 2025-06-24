package service

import db.DBImplementation

import scala.concurrent.Future
import model.Classroom
import utility.Exceptions.NoClassroomException

import scala.concurrent.ExecutionContext.Implicits.global

class QueryService(dBImplementation: DBImplementation) {

    def findStudentEnrollments(studentId: String): Future[List[Classroom]] = Future {

        val enrolments = dBImplementation.getEnrolments().filter(_.studentId == studentId)

        val classrooms = enrolments map { x =>
            dBImplementation.getClassroom(x.classroomId).getOrElse(throw new NoClassroomException())
        }
        classrooms
    }
  
}
