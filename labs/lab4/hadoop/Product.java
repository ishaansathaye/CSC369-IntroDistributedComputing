import java.io.*;
import org.apache.hadoop.io.*;

public class Product implements Comparable <Product> {
    private int productID;
    private String productName;
    private double price;

    public Product(int productID, String productName, double price) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
    }

    public String toString() {
        return productID + ", " + productName + ", " + price;
    }

    public int compareTo(Product other) {
        if (this.price > other.price) {
            return -1; // return that it is less so it appears first
        }
        if (this.price < other.price) {
            return 1; // return that it is greater so it appears later
        }
        if (this.productID > other.productID) {
            return 1; // return that it is greater so it appears later
        }
        if (this.productID < other.productID) {
            return -1; // return that it is less so it appears first
        }
        return 0; // return that they are equal
    }
}