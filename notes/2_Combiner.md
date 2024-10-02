# Combiner Functions and Custom Classes

## Previously
- Sending aggregate data to the reducer
    - Send an object of the sum and count for each key

## Combiner
- For optimization purposes
- Adding it does not change the output
- Purpose is to reduce the amount of data sent to the reducer
    - Aggregate the data with the same key at each of the map nodes
    before sending it to the reducer
- Can be a sum like (2024/01/01, (30, 1)) instead of (2024/01/01, 30)
    - 30 being the sum and 1 being the count
    - Sends the sum and count to the reducer
- This will decrease traffic and make the process faster
    - Function is unnecessary if mapper and reducer on same node
- Writing combiner same as writing Reducer
    - Just need to implement the reduce method
    - ```context.write(key, new SumCountPair(sum,count));```
        - SumCountPair is a custom class
        - sending reduced data to the reducer

## Driver
- Add the combiner to the driver
    - ```job.setCombinerClass(AverageTemperatureCombiner.class);```
    - This will run the combiner after the map phase and before the reduce phase
    - Inputs and Outputs of same type for the combiner

## Example: Word Counting
- Reads a series of text files
- Takes as input the number N
- Finds all words of size >= N characters and their frequency
- We will use combiner again
- Map
    - (1, "I really like Fish and tacos")
    - Output of map: (really, 1), (like, 1), (fish, 1), (tacos, 1) for the first line
    - (2, "Fish likes Fish")
    - Output of map: (Fish, 1), (likes, 1), (Fish, 1) for the second line
- Sorting
    - (Fish, 1), (Fish, 1), (Fish, 1), (likes, 1), (like, 1), (tacos, 1)
- Local Grouping:
    - (Fish, 1), (Fish, 1), (Fish, 1)
    - (likes, 1)
    - (really, 1), (like, 1), (tacos, 1)
- Combiner
    - (Fish, 3)
    - (likes, 1)
    - (really, 1), (like, 1), (tacos, 1)
- Reducer
    - (Fish, 14) -> gets all the Fishes from different map nodes
    - (likes, 1)
    - (really, 1), (like, 1), (tacos, 1)