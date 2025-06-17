package utility

object Exceptions {
  class NoStudentException extends Exception("Cannot find student")
  class NoClassroomException extends Exception("Cannot find classroom")
  class ClassroomFullException extends Exception("Classroom is full")
}
