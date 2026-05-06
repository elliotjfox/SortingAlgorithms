# Selection Sort

Selection sort is one of the simplest and most intuitive sorting algorithms. It starts by searching linearly through the unsorted array for the smallest number, then swaps it to the front. Then it repeats for the next smallest, and the next, and so on. 

The simplicity of selection sort makes it easy to understand and program, but it takes a relatively long time for sorting algorithms.

https://github.com/user-attachments/assets/3aa82dd9-7409-4eab-834d-ec9a14c0e531

## Pseudocode

```
ALGORITHM SelectionSort(A)
    FOR i = 0 TO A.size - 1 DO
        minIndex = i;
        FOR j = i + 1 TO A.size - 1 DO
            IF list[j] < list[minIndex] THEN
                minIndex = j;
            END IF
        END FOR
        swap(minIndex, i);
    END FOR
END ALGORITHM
```

The inner loop searches the unsorted section for the smallest element and the outer loop swaps it to the correct position. From the pseudocode, we can easily see that the runtime complexity of selection sort is O(n<sup>2</sup>).
