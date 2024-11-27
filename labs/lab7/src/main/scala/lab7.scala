package example
import scala.io._
import org.apache.spark.{ SparkConf, SparkContext }
import org.apache.log4j.{ Level, Logger }


// Write a Spark/Scala program that finds the top 10 stores with the most revenues
// from sales (dollar amount of total sales) per month. The result should be ordered
// by month (ascending) and then by total proceeds from sales (descending).

// Example output:
// 2016-12, (Best Buy, SLO, $1444355), (Costco, LA, $1400344), ... 
// storeName = Best Buy, store city = SLO, total sales = $1,444,355
// 2017-01 // and so on

// lineItem.csv
// ID, saleID, productID, quantity

// product.csv
// productID, name, price

// sales.csv
// saleID, date, time, storeID, customerID

// store.csv
// storeID, name, address, city, state, zip, phone

object App {
    def main(args: Array[String]): Unit = {
        Logger.getLogger("org").setLevel(Level.OFF)
        Logger.getLogger("akka").setLevel(Level.OFF)

        val conf = new SparkConf().setAppName("lab7")
        val sc = new SparkContext(conf)

        // Read data from files
        val lineText = sc.textFile("/user/isathaye/input/lineItems.csv").persist()
        val productText = sc.textFile("/user/isathaye/input/products.csv").persist()
        val salesText = sc.textFile("/user/isathaye/input/sales.csv").persist()
        val storeText = sc.textFile("/user/isathaye/input/store.csv").persist()

        // Create RDDs
        // products: (productID, price)
        val products = productText.map(line => {
            val cols = line.split(",")
            (cols(0), cols(2).toDouble)
        }).persist()
        // lineItems: (ID, (saleID, productID, quantity))
        val lineItems = lineText.map(line => {
            val cols = line.split(",")
            (cols(0), (cols(1), cols(2), cols(3)))
        }).persist()
        // sales: (saleID, date, storeID)
        val sales = salesText.map(line => {
            val cols = line.split(",")
            (cols(0), (cols(1), cols(3)))
        }).persist()
        // stores: (storeID, name, city)
        val stores = storeText.map(line => {
            val cols = line.split(",")
            (cols(0), (cols(1), cols(3)))
        }).persist()

        // Get dates from sales
        // dates: (storeID, date (yyyy-MM), saleID)
        val dates = sales.map {
            case (saleID, (date, storeID)) => 
            (storeID, (date.replace("/", "-").substring(0, 7), saleID))
        }

        // Join dates (sales) with stores
        // storeDates: (saleID, (storeID, date, name, city))
        val storeDates = dates.join(stores).map {
            case (storeID, ((date, saleID), (name, city))) => 
            (saleID, (storeID, date, name, city))
        }

        // Join lineItems with products
        // lineItems: (saleID, total)
        val lineItemsWithPrice = lineItems.join(products).map {
            case (id, ((saleID, productID, quantity), price)) => 
            (saleID, price.toDouble * quantity.toDouble)
        }

        // Join storeDates with lineItems to get revenue per store per month
        // storeDatesWithRevenue: ((date, name, city), total)
        val storeDatesWithRevenue = storeDates.join(lineItemsWithPrice).map {
            case (saleID, ((storeID, date, name, city), total)) => 
            ((date, name, city), total)
        }.groupByKey().sortByKey().mapValues(_.sum)

        // Get top 10 stores with most revenue per month
        // topStores: (date, (name, city, total))
        val topStores = storeDatesWithRevenue.map {
            case ((date, name, city), total) => 
            (date, (name, city, total))
        }.groupByKey().sortByKey().mapValues(_.toList.sortBy(-_._3).take(10))

        // Print output with sales for each month on the same line
        topStores.collect().foreach {
            case (date, stores) => 
            println(date + ", " + stores.map {
                case (name, city, total) => 
                "(" + name + ", " + city + ", $" + total + ")"
            }.mkString(", "))
        }
        
        sc.stop()
    }
}