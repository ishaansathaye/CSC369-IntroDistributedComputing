package example
import scala.io._
import org.apache.spark.{ SparkConf, SparkContext }
import org.apache.log4j.{ Level, Logger }

// Consider three files. One file has information about students (ID, name, address, phone number). 
// 1, John, 123 Main, 233 223 5566 

// Consider a second files that has information about courses and their difficulty. 
// CSC365, 1
// CSC369, 1
// CSC469, 2

// Consider a third file that contains the student ID, course, and grade. 
// 1, CSC365, A
// 1, CSC369, A
// 1, CSC469, B
// It contains information about student taking a class and earning a grade. 


object App {
    def main(args: Array[String]): Unit = {
        Logger.getLogger("org").setLevel(Level.OFF)
        Logger.getLogger("akka").setLevel(Level.OFF)

        val conf = new SparkConf().setAppName("assignment5")
        val sc = new SparkContext(conf)

        val coursesText = sc.textFile("/user/isathaye/input/courses.txt").persist()
        val studentsText = sc.textFile("/user/isathaye/input/students.txt").persist()
        val gradesText = sc.textFile("/user/isathaye/input/grades.txt").persist()
        
        val courses = coursesText.map(line => {
            val cols = line.split(",")
            (cols(0), cols(1).toInt)
        }).persist()
        val students = studentsText.map(line => {
            val cols = line.split(",")
            (cols(0), (cols(1), cols(2), cols(3)))
        }).persist()
        val grades = gradesText.map(line => {
            val cols = line.split(",")
            (cols(0), (cols(1), cols(2)))
        }).persist()

        // Find name of students that have taken at least one of the courses
        // with the greatest difficulty level

        // Find the courses with the greatest difficulty level
        // Only keep courses with the greatest difficulty level
        // course, difficulty
        val maxCourse = courses.maxBy(_._2)
        maxCourse.foreach(println)

        // Join students with grades
        // val studentGrades = students.join(grades).map {
        //     case (studentID, ((name, address, phone), (course, grade))) => 
        //     (studentID, (name, course, grade))
        // }

        // Filter students that have taken at least one of the courses
        // with the greatest difficulty level


        sc.stop()
    }
}