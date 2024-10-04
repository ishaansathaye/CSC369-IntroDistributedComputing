# Custom Partitioners, Sorters, and Grouping Comparators

## Midterm Question
- Sorter Comparator
    - overrides behavior of the default sort
    - if key is primitive type then it uses default compareTo
        - so override by creating a sorter comparator

## Natural and Composite Keys
- natural key is what the final result is grouped by
- composite key is what will be output key of mapper
    - also will define the sorting order on the composite key
- create when we want to sort on something more than just the natural key

