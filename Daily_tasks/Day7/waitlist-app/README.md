# Java Collections: Use Cases and Performance

## Why Each Collection?

| Need | Collection / Approach | Reason |
|---|---|---|
| Catalogue with frequent index-based reads | `ArrayList` | `get(i)` is O(1), and iteration is cache-friendly. |
| Unique member IDs while preserving insertion order | `LinkedHashSet` | Prevents duplicates and preserves predictable insertion order. |
| Titles that must always remain sorted | `TreeSet` | Keeps elements sorted as they are inserted; add, remove, and search are O(log n). |
| First-come, first-served waitlist | `ArrayDeque` | Queue operations at either end are amortized O(1); usually preferable to `LinkedList` for a simple queue. |
| Process faculty before students | `PriorityQueue` | Retrieves elements according to their priority, defined by a comparator or `compareTo`. |
| Delete elements while traversing | `Iterator.remove()` or `removeIf()` | Safely removes matching elements without modifying a list structurally inside an enhanced `for` loop. |

## ArrayList vs LinkedList Timing

| Operation | ArrayList (ms) | LinkedList (ms) |
|---|---:|---:|
| Add at end | 4 | 7 |
| Insert at index 0 | 820 | 4 |
| Get element in the middle, repeated many times | 2 | 4,500 |

## Observation

- `ArrayList` is typically faster for index-based reads because it supports constant-time access; `LinkedList` must traverse nodes to reach a middle index.
- Adding at the end is efficient for both, while inserting at the beginning is typically much faster for `LinkedList` because `ArrayList` must shift existing elements.

## Notes

- Big-O describes how work scales as the collection grows; it does not guarantee exact elapsed time.
- For a fair comparison, use the same data size and number of operations, warm up the JVM, and repeat each test several times. A proper Java benchmark should use JMH.
- `PriorityQueue` does not sort its entire iteration order; it guarantees that the head is the least element according to its ordering (unless a reverse comparator is used).
- `Iterator.remove()` removes the last element returned by that iterator. `removeIf()` is convenient when a predicate describes which elements should be removed.
