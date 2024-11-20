package example
import scala.io._
import org.apache.spark.{ SparkConf, SparkContext }
import org.apache.log4j.{ Level, Logger }

object App {
    def main(args: Array[String]): Unit = {
        Logger.getLogger("org").setLevel(Level.OFF)
        Logger.getLogger("akka").setLevel(Level.OFF)

        val conf = new SparkConf().setAppName("assignment5")
        val sc = new SparkContext(conf)

        val lines = sc.textFile("/user/isathaye/input/input.txt").persist()
        
        val gpaMap = Map("A" -> 4.0, "B" -> 3.0, "C" -> 2.0, "D" -> 1.0, "F" -> 0.0)

        val res = lines.map { line =>
            val parts = line.split(", ", 3)
            val name = parts(0)
            val id = parts(1)
            val courses = parts(2).split(", ").map(_.trim)

            val (total, numCourses) = courses.aggregate(0.0, 0)(
                { case ((sum, count), course) =>
                    (sum + gpaMap(course.split(" ")(0)), count + 1)}, 
                { case ((sum1, count1), (sum2, count2)) =>
                    (sum1 + sum2, count1 + count2)}) 

            val gpa = total / numCourses

            s"$name, $id, $gpa"
        }

        res.collect().foreach(println)

        sc.stop()
    }
}