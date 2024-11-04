package labs.lab5
import scala.io._

// Reads data from lab 1 and prints the total sales (in dollars) for every store.
// Print the result ordered by state. That is, start with stores in the state of AL.
// Do not use Spark.

// Example output:
// AL, 123, 34453.34 // store 123 in AL, total sales 34453.34
// ...

// Use TreeSets, HashSets, TreeMaps, HashMaps and appropriate data structures to
// make your program as efficient as possible.

// lineItem.csv
// ID, saleID, productID, quantity

// product.csv
// productID, name, price

// sales.csv
// saleID, date, time, storeID, customerID

// store.csv
// storeID, name, address, city, state, zip, phone

case class LineItem(id: String, saleID: Int, productID: Int, quantity: Int)
case class Product(id: Int, price: Double)
case class Sales(id: Int, storeID: Int)
case class Store(id: Int, name: String, state: String)

object Lab5 {
    def main(args: Array[String]): Unit = {
        // Read data from files
        val stores = Source.fromFile("../lab1/stores.csv").getLines().map {
            line =>
                val Array(id, name, address, city, state, zip, phone) = line.split(",")
                Store(id.toInt, name, state)
        }.toSeq

        val sales = Source.fromFile("../lab1/sales.csv").getLines().map {
            line =>
                val Array(id, date, time, storeID, customerID) = line.split(",")
                Sales(id.toInt, storeID.toInt)
        }.toSeq

        val products = Source.fromFile("../lab1/products.csv").getLines().map {
            line =>
                val Array(id, name, price) = line.split(",")
                Product(id.toInt, price.toDouble)
        }.toSeq

        val lineItems = Source.fromFile("../lab1/lineItems.csv").getLines().map {
            line =>
                val Array(id, saleID, productID, quantity) = line.split(",")
                LineItem(id, saleID.toInt, productID.toInt, quantity.toInt)
        }.toSeq

        // Join Sales and Store on storeID
        val storeSales = sales.map {
            sale =>
                val store = stores.find(_.id == sale.storeID).get
                (sale, store)
        }
    }
}