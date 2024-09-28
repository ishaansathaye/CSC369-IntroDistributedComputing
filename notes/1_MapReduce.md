# Map/Reduce Introduction

## Map Function Example
- Input to map method: (key, value) -> outputs (d1 (string), t1 (int))
    - key is the line number
    - value is the line of text
    - (1, "d1, t1")
- Map method executed at the source

## Steps
1. Map
    - Input: (key, value)
    - Output: (d1, t1)
2. Local Sort
    - Sorts by key (could be ascending or descending)

(3 and 4 are optional)

3. Local Group
    - Groups by key
4. Combiner
    - Combines values with the same key
    - Can be a sum like (2024/01/01, (120, 3))
        - 120 being the sum and 3 being the count

Reduce phase starts:

5. Partition or Shuffle
    - Map nodes and reduce nodes are different
        - both can be used for map and reduce in reality
    - send data to the correct reduce node based on a hash of the key
6. Global Sort (merge)
    - Chunks of data is merged and sorted by the key and then sent to the reduce node
    - Chunks of data is coming from different map nodes
7. Global Group
    - for each key we are creating groups
8. Reduce method
    - gets everything with the same key and compress everything into a single value
    - (k1, avg(v1), sum(v2), count(v3))
    