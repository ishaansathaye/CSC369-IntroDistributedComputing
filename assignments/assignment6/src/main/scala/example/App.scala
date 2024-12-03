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
    def convertGPA(grade: String): Double = 
        return grade match {
            case "A" => 4.0
            case "B" => 3.0
            case "C" => 2.0
            case "D" => 1.0
            case _ => 0.0
        }

    def main(args: Array[String]): Unit = {
        Logger.getLogger("org").setLevel(Level.OFF)
        Logger.getLogger("akka").setLevel(Level.OFF)

        val conf = new SparkConf().setAppName("assignment6").setMaster("local[*]")
        val sc = new SparkContext(conf)

        val coursesText = sc.textFile("/user/isathaye/input/courses.txt").persist()
        val studentsText = sc.textFile("/user/isathaye/input/students.txt").persist()
        val gradesText = sc.textFile("/user/isathaye/input/grades.txt").persist()
        
        val courses = coursesText.map(line => {
            val cols = line.split(",")
            (cols(0).trim(), cols(1).trim().toInt)
        }).persist()
        val students = studentsText.map(line => {
            val cols = line.split(",")
            (cols(0).trim(), (cols(1), cols(2), cols(3)))
        }).persist()
        val grades = gradesText.map(line => {
            val cols = line.split(",")
            (cols(0).trim(), (cols(1).trim(), cols(2).trim()))
        }).persist()
        
        // Convert courses to key value pair and replace grade with GPA num
        val gpaGrades = grades.map(x => (x._1, (x._2._1, convertGPA(x._2._2))))

        // Join studentGPA with students and group by student ID
        val studentGPA = students.leftOuterJoin(gpaGrades).map({
            case (id, ((name, address, phone), Some((course, gpa)))) => (id, (name, address, phone, gpa))
            case (id, ((name, address, phone), None)) => (id, (name, address, phone, 0.0))
        })

        // Group then aggregate by student ID and calculate average GPA
        val studentGrouped = studentGPA.groupByKey().mapValues(values => {
            val gpaList = values.map(_._4)
            val gpaSum = gpaList.sum
            val gpaCount = gpaList.size
            val gpaAvg = gpaSum / gpaCount
            gpaAvg
        })

        // Join studentGrouped with students and sort by GPA in descending order
        val studentGPAOrdered = studentGrouped.join(students).sortBy(_._2._1, false)

        // Print student name, gpa
        studentGPAOrdered.collect().foreach(x => println(x._2._2._1 + ", " + x._2._1))

        sc.stop()
    }
}