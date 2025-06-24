import db.DBImplementation
import service.RegistrationService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object App extends App {

  val registerService = new RegistrationService(new DBImplementation())

  registerService.registerStudent("stud1", "year1") onComplete {
    case Success(Success(value)) => println(value)
    case Success(Failure(exception)) => println(s"$exception")
    case Failure(exception) => println("future failed")
  }

  for {
    futureEnrolment <- registerService.registerStudent("stud1", "year1")
  } yield {
    futureEnrolment match
      case Failure(exception) => println(s"$exception")
      case Success(value) => println(value)
  }

}
