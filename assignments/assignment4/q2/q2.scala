import scala.io._

object q2 {
    def main(args: Array[String]) {
        val filename = "q2.txt"
        val lines = Source.fromFile(filename).getLines.toList
        val temperatures = lines.map(p.split(" ")).map(x => (x(0), x(1).toInt))
        val maxTemperatures = temperatures.groupBy(_._1).mapValues(_.map(_._2).max)
        maxTemperatures.foreach(println)
    }
}