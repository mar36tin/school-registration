package db

import model.{Classroom, Enrolment, Student}

trait UtilityDatabase {
    val students: Map[String, Student]
    val classrooms: Map[String, Classroom]
    var enrolments: List[Enrolment]

    def getStudent(id: String): Option[Student]
    def getClassroom(id: String): Option[Classroom]
}