# CSC 369 Introduction to Distributed Computing

## [Slides](https://drive.google.com/drive/folders/15f8oNQfrhNaNGEnIE2-QLQ7_O8go3B60)
- [0 - Using Hadoop and Java](https://docs.google.com/presentation/d/1MJ10Xl_4CI0m0sRZV7fgejnmyCAH4qWe/edit#slide=id.p3)
- [1 - Map/Reduce](https://docs.google.com/presentation/d/1CFfGHUuzZNVUfKejn_E3AVtR1p7046op/edit#slide=id.p4)
- [2 - Combiner Functions and Custom Classes](https://docs.google.com/presentation/d/1AiSMVQQLVdIh6sGOEFGy96F0YZt6Uzax/edit#slide=id.p1)
- [3 - Custom Partitioners, Sorters, and Grouping Comparators](https://docs.google.com/presentation/d/1r9gLifKq3PrpLUJ2gMmY7KM44d0iOs5d/edit#slide=id.p1)
- [4 - Secondary Sort Example](https://docs.google.com/presentation/d/1CG13YuVfVTuzRJFCazdTRs2kr2yBp03Z/edit#slide=id.p3)
- [5 - Top N Example](https://docs.google.com/presentation/d/1HfZVg7Nh81fa1gThNKIdQ_m1zmPcUFIg/edit#slide=id.p1)
- [6 - Outer Joins and Multiple Jobs](https://docs.google.com/presentation/d/1yorq8VWz3FI8mJmigQXl9FDGNmwfr0vd/edit#slide=id.p1)

## Notes
- [Hadoop and Java](notes/0_HadoopJava.md)

## Labs
- [Lab 1 - Data Generation](labs/lab1/)
    - [Lab 1 Instructions](https://docs.google.com/document/d/1IZJ3BmwIFJFoxMhJ-pdHfGyYU1rzox7w/edit)
- [Lab 2 - Total Sales](labs/lab2/)
    - [Lab 2 Instructions](https://docs.google.com/document/d/1K-T44teE8fGD3-PdRWcSMnv6ewJBGX5b/edit)
- [Lab 3 - Sorting All Sales](https://docs.google.com/document/d/1ILEF63JqMABhDGTELM9VkUjAXiMnC9AN/edit)
- [Lab 4 - Top 10 Expensive Products](https://docs.google.com/document/d/1F3ElibL21zv-aZDmF0MCAoTl-26RO-hI/edit#heading=h.gjdgxs)

## Assignments
- [Assignment 1](assignments/assignment1/assignment1.pdf)
- [Assignment 2](assignments/assignment2/assignment2.pdf)

## Basic Commands
- Write `hadoop fs -ls /user/lubo` to see what is there.
Common operations
- `hadoop fs -ls [directory]` find content
- `hadoop fs -copyFromLocal localDataFile /user/lubo/input` <- copies a file from current local directory to input directory in the HDFS.
- `hadoop fs -get /user/lubo/input/file` <- copies a file from the input directory in the HDFS to the current local directory.
- `hadoop fs -rm -r /user/lubo/output` <- deletes output directory. 
- `hadoop fs -mkdir /user/lubo/input` <- creates input directory
- `hadoop fs -cat /user/lubo/output/part-r-00000` <-prints the content of the output file

## Compiling Program
- Put all your source code in the same folder.
- Run `hadoop com.sun.tools.javac.Main *.java` This will create the .class files.
- Run `jar cvf WordCount.jar *.class`. This will create a single jar.
    - Example job submission:
        - `hadoop jar WordCount.jar WordCountDriver 5 /user/lubo/input /user/lubo/output`
- `WordCount.jar` is the name of the jar file
- `WordCountDriver` is the name of the Java driver file (the file that contains the main method).
- The last three parameters are input to the program: e.g., min size of word to be selected and location of input and output directories.
- Type `hadoop fs -cat /user/lubo/output/part-r-00000` to see result. 
- If multiple files, use 00001, 00002, and so on.