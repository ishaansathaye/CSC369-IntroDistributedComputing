package labs.lab6
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}
import scala.collection.mutable.Map

// Reads data from lab 1 and prints the total sales (in dollars) for every store.
// Print the result ordered by state. That is, start with stores in the state of AL.
// All operations should be with RDDs.

// Example output:
// AL, 123, 34453.34 // store 123 in AL, total sales 34453.34
// ...

// Do not use HashMaps, HashSets, TreeMaps, or TreeSets.

// lineItem.csv
// ID, saleID, productID, quantity

// product.csv
// productID, name, price

// sales.csv
// saleID, date, time, storeID, customerID

// store.csv
// storeID, name, address, city, state, zip, phone

object Lab6 {
    def main(args: Array[String]): Unit = {
        Logger.getLogger("org").setLevel(Level.OFF)
        Logger.getLogger("akka").setLevel(Level.OFF)

        val conf = new SparkConf().setAppName("lab6")
        val sc = new SparkContext(conf)

        // Read data from files
        val lineText = sc.textFile("/user/isathaye/input/lineItem.csv").persist()
        val productText = sc.textFile("/user/isathaye/input/product.csv").persist()
        val salesText = sc.textFile("/user/isathaye/input/sales.csv").persist()
        val storeText = sc.textFile("/user/isathaye/input/store.csv").persist()

        // Create RDDs
        // products: (productID, price)
        val products = productText.map(line => {
            val cols = line.split(",")
            (cols(0), cols(2).toDouble)
        })
        // sales: (saleID, storeID)
        val sales = salesText.map(line => {
            val cols = line.split(",")
            (cols(0), cols(3))
        })
        // lineItems: (ID, (saleID, productID, quantity))
        val lineItems = lineText.map(line => {
            val cols = line.split(",")
            (cols(0), (cols(1), cols(2), cols(3)))
        })
        // stores: (storeID, name, state)
        val stores = storeText.map(line => {
            val cols = line.split(",")
            (cols(0), cols(1), cols(4))
        })

        // Join sales and stores on storeID
        // salesByStore: (saleID, store.state, store.name)
        val salesByStore = sales.cartesian(stores)
            .filter(x => x._1._2 == x._2._1)
            .map(x => (x._1._1, x._2._3, x._2._2)).distinct()

        // Join lineItems and products on productID and group by productID
        val lineItemsByProduct = lineItems.cartesian(products)
            .filter(x => x._1._2._2 == x._2._1)
            .groupBy(x => x._1._2._2)
        
        // Calculate total price for each product and amount sold
        val items = lineItemsByProduct.mapValues(
            x => x.map(y => y._1._2._3.toDouble * y._2._2))
        .mapValues(x => x.sum).distinct()

        // Calculate total sales for each store
        val totals = salesByStore.cartesian(items)
            .filter(x => x._1._1 == x._2._1)
            .map(x => (x._1._2, x._2._2))
            .collect()
        
        // Print results
        totals.groupBy(x => x._1).foreach(x => {
            val state = x._1
            x._2.foreach(y => {
                val store = y._1
                val total = y._2
                println(s"$state, $store, $total")
            })
        })

        sc.stop()
    }
}