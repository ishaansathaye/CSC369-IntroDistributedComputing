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

        // Write a program that prints the top 5 most difficult classes.
        val top5 = courses.map(x => (x._2, x._1)).sortByKey(false).take(5)
        println("Top 5 most difficult classes:")
        top5.map(x => println(x._2))

        sc.stop()
    }
}