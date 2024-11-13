import scala.io._

object q3 {
    def main(args: Array[String]) {
        val filename = "q3.txt"
        val lines = Source.fromFile(filename).getLines.toList
        val students = lines.map(line => {
            val parts = line.split(", ")
            (parts(0), parts(1), parts(2), parts(3))
        })

        val studentMap = students.groupBy(_._1).mapValues(_.map(x => (x._2, x._3, x._4)))
        val sortedStudentMap = studentMap.toList.sortBy(_._1).map(x => {
            val student = x._1
            val classes = x._2.sortBy(_._2).map(y => {
                s"(${y._2}, ${y._3})"
            }).mkString(", ")
            val id = x._2(0)._1
            s"$student, $id, $classes"
        })
        sortedStudentMap.foreach(println)
    }
}