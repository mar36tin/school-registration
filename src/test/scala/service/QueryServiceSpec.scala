package service


import db.DBImplementation
import model.{Classroom, Enrolment}
import org.mockito.Mockito.{verify, when}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatestplus.mockito.MockitoSugar.mock
import utility.Exceptions.NoClassroomException

class QueryServiceSpec extends AsyncFlatSpec {

  "QueryService" should "return a list of classrooms where the student is enrolled" in {
    val mockDb = mock[DBImplementation]
    val enrolments: List[Enrolment] = List(
      Enrolment("stud1", "year1"),
      Enrolment("stud2", "year1"),
      Enrolment("stud3", "year1"),
      Enrolment("stud4", "year1")
    )
    when(mockDb.getEnrolments()).thenReturn(enrolments)
    when(mockDb.getClassroom("year1")).thenReturn(Some(Classroom("year1", "Year 1", 10)))

    val queryService = QueryService(mockDb)
    val result = queryService.findStudentEnrollments("stud1")

    result map {x =>
      assert(x.contains(Classroom("year1", "Year 1", 10)) )
    }
  }

  it should "throw a NoClassroomFound exception if classroom doesn't exist" in {
    val mockDb = mock[DBImplementation]
    val enrolments: List[Enrolment] = List(
      Enrolment("stud1", "year1"),
      Enrolment("stud2", "year1"),
      Enrolment("stud3", "year1"),
      Enrolment("stud4", "year1")
    )
    when(mockDb.getEnrolments()).thenReturn(enrolments)
    when(mockDb.getClassroom("year1")).thenReturn(None)

    val queryService = QueryService(mockDb)

    recoverToExceptionIf[NoClassroomException] {
      queryService.findStudentEnrollments("stud1")
    } map { ex =>
      verify(mockDb).getClassroom("year1")
      succeed
    }
  }
}