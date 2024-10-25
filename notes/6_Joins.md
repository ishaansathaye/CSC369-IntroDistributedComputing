# Outer Joins and Multiple Jobs

## Full Example for Midterm
- Pictures of example
- Use PairofStrings class on midterm to represent tuple
- Job 1: All states and their amounts
    - salesByState
    - CA 100
    - CA 200
    - FL 100
    - FL 700
- Job 2: Find total revenue per state
    - totalRevenueByState
    - CA 300
    - FL 800
- Job 3: Find states with the most revenue
    - create single reduce node (manually set number of reducers to 1 in job configuration)
    - statesWithMostRevenue
    - CA
    - FL
    - have global variable firstEquals = True in the reducer
        - if reducer has run once then set firstEquals = False
        - can do this since it is sorted in descending order
    - pseudocode:
    ```java
    class Reducer {
        Boolean first = true
        reduce(key, values) {
            if (first) {
                for (value in values) {
                    output(el, null)
                }
            }
            first = false
        }
    }
    ```

## Practice Midterm
- What is HDFS? Explain why it is needed when using map/reduce.
    - HDFS (Hadoop Distributed File System) is a distributed file system designed to run on commodity hardware. It provides high-throughput access to application data and is suitable for applications with large data sets. HDFS is needed in map/reduce because it allows for the storage and processing of large amounts of data across multiple machines, enabling parallel processing and fault tolerance.
    - Data distributed across multiple nodes, allowing for efficient data processing and storage.