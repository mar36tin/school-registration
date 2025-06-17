import db.DBImplementation
import model.{Classroom, Enrolment, Student}
import org.mockito.Mockito.when
import org.scalatest.Assertion
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatestplus.mockito.MockitoSugar.mock
import service.RegistrationService
import utility.Exceptions.{NoClassroomException, NoStudentException}

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

class RegistrationServiceSpec extends AsyncFlatSpec {
  "Registration service" should "enrol student if student and class exist and return Success" in {
    withMockDb { mockDb =>
      when(mockDb.getStudent("stud-1")).thenReturn(Some(Student("stud-1", "Martin")))
      when(mockDb.getClassroom("year-1")).thenReturn(Some(Classroom("year-1", "Year 1", 10)))
      when(mockDb.getEnrolments()).thenReturn(TestData.enrolments)

      val regService = RegistrationService(mockDb)

      val enrolmentFuture: Future[Try[Enrolment]] = regService.registerStudent("stud-1", "year-1")

      enrolmentFuture map {
        case Success(value) => assert(value.classroomId == "year-1" && value.studentId == "stud-1")
      }
    }
  }

  it should "result in an Failure exception if student does not exist" in {
    withMockDb { mockDb =>
      when(mockDb.getStudent("stud-1")).thenReturn(None)
      when(mockDb.getClassroom("year-1")).thenReturn(Some(Classroom("year-1", "Year 1", 10)))
      when(mockDb.getEnrolments()).thenReturn(TestData.enrolments)

      val regService = RegistrationService(mockDb)

      val enrolmentFuture: Future[Try[Enrolment]] = regService.registerStudent("stud-1", "year-1")

      enrolmentFuture map {
        case Failure(exception) => {
          assert(exception.isInstanceOf[NoStudentException])
          assert(exception.getMessage == "Cannot find student")
        }
      }
    }
  }

  it should "result in an Failure exception if classroom does not exist" in {
    withMockDb { mockDb =>
      when(mockDb.getStudent("stud-1")).thenReturn(Some(Student("stud-1", "Martin")))
      when(mockDb.getClassroom("year-1")).thenReturn(None)
      when(mockDb.getEnrolments()).thenReturn(TestData.enrolments)

      val regService = RegistrationService(mockDb)

      val enrolmentFuture: Future[Try[Enrolment]] = regService.registerStudent("stud-1", "year-1")

      enrolmentFuture map {
        case Failure(exception) => {
          assert(exception.isInstanceOf[NoClassroomException])
          assert(exception.getMessage == "Cannot find classroom")
        }
      }
    }
  }

  it should "result in an Failure exception if both student and classroom does not exist" in {
    withMockDb { mockDb =>
      when(mockDb.getStudent("stud-1")).thenReturn(None)
      when(mockDb.getClassroom("year-1")).thenReturn(None)
      when(mockDb.getEnrolments()).thenReturn(TestData.enrolments)

      val regService = RegistrationService(mockDb)

      val enrolmentFuture: Future[Try[Enrolment]] = regService.registerStudent("stud-1", "year-1")

      enrolmentFuture map {
        case Failure(exception) => {
          assert(exception.isInstanceOf[NoStudentException])
          assert(exception.getMessage == "Cannot find student")
        }
      }
    }
  }

  def withMockDb(testCode: DBImplementation => Future[Assertion]): Future[Assertion] = {
    val mockDb = mock[DBImplementation]
    testCode(mockDb)
  }

  object TestData {
    val enrolments = List(
      Enrolment("stud-1", "year-1"),
      Enrolment("stud-2", "year-1"),
      Enrolment("stud-3", "year-1")
    )
  }
}
