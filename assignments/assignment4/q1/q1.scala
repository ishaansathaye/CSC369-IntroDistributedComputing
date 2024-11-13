import scala.io._

object q1 {
    def main(args: Array[String]) {
        val filename = "q1.txt"
        val lines = Source.fromFile(filename).getLines.toList
        val numbers = lines.flatMap(_.split(" ").map(_.toInt))
        val divisibleBy3 = numbers.filter(_ % 3 == 0)
        println(divisibleBy3.length)
    }
}