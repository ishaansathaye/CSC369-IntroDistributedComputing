# Secondary Sort Example

## Problem:
Consider the following input: (stock, date, price)
- appl, 1/1/2001, 11.0
- bam, 1/1/2001, 22.0
- goog, 1/1/2001, 10.0
- appl, 2/2/2012, 22.0
- bam, 2/2/2012, 23.0

We want to group the data by the stock symbol and sort the data in ascending order relative to stock symbol and in descending order by date as it comes to the reducer. Price if the date is the same.
The reducer will just output the stock symobl and combinations of date/price pairs.

For example:
- appl, ((2/2/2012,22.0), (1/1/2001,11.0))
- bam, ((2/2/2012, 23.0), (1/1/2001, 22.0))
- goog, ((1/1/2001,10.0))

## Solution:
- Mapper:
    - Input: (1, "bam, 2001/01/01, 22.0") (date should be in format yyyy/mm/dd wrong in example)
    - Custom Object ((bam, 2001/01/01, 22.0), ("2001/01/01", 22.0))
        - Value is a string of date and price because we want to just concatenate them in the reducer
- Sort Comparator:
    - Sorting order on stock and date*-1 (for descending order)
    - If both same then sort by price
    - Composite key us the the stock, date, and price
        - **Because we want to sort by stock, date, and price**
    - Natural key is just the stock symbol
        - **Because we want to group by stock symbol**
- Partitioner:
    - Partition by stock symbol
    - Applies hash function to stock symbol and returns the partition number
    - Exam question: what is the connection between the partitioner and the grouping comparator?
        - **Makes sure all the same stock symbols go to the same reduce node**
- Global Grouping
    - ((bam, "2001/01/01", 22.0), ("2001/01/01", 22.0))
    - ((bam, "2000/01/01", 10.0), ("2000/01/01", 10.0))
        - should go to reduce and look like this:
            - (bam, (s1, s2, ...))
    - 0 if part of the same group and another number if not
- Reducer
    - Concatenate the date and price pairs
    - Output the stock symbol and the date/price pairs
    - Output: (bam, ((2001/01/01, 22.0), (2000/01/01, 10.0)))