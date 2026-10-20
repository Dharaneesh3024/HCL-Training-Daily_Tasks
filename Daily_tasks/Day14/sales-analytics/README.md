# Sales Analytics

Day 14 – Streams API. A Java project that analyzes 1,000 generated orders using streams and collectors, comparing sequential and parallel stream performance.

## Project Structure

```text
sales-analytics/
└── src/
    ├── analytics/
    │   └── SalesAnalytics.java
    ├── app/
    │   └── Main.java
    ├── data/
    │   └── OrderGenerator.java
    └── model/
        ├── Order.java
        ├── OrderLine.java
        └── OrderStatus.java
```

## Reports

All reports in `SalesAnalytics` use the Collectors API.

| Report | Collector or Operation |
|---|---|
| Revenue by category | `groupingBy`, `summingDouble` |
| Top 3 customers | `toMap`, `sorted`, `limit` |
| Delivered vs. not delivered | `partitioningBy` |
| Orders per status | `groupingBy`, `counting` |
| Average basket size | `averagingInt` |
| Comma-joined product list | `flatMap`, `distinct`, `sorted`, `joining` |
| Order value statistics | `summarizingDouble` |
| Total revenue | `reduce` |
| Cancelled order check | `anyMatch` |

## Streams Concepts Demonstrated

- **Stream Creation:** `Stream.of`, `Arrays.stream`, `IntStream.rangeClosed`, `Stream.iterate`
- **Intermediate Operations:** `filter`, `map`, `flatMap`, `sorted`, `distinct`, `limit`, `peek`
- **Terminal Operations:** `collect`, `reduce`, `count`, `anyMatch`, `findFirst`
- **Laziness:** Processing begins only when a terminal operation executes. `findFirst` stops when a matching element is found.
- **Sequential vs. Parallel Streams:** Parallel processing can improve performance for CPU-intensive tasks, while lightweight tasks may not benefit.
- **No External Mutation:** Stream pipelines produce results without modifying external state.

## Output Sections

1. Creating Streams
2. Laziness
3. Sales Reports
4. Sequential vs. Parallel Timing

## Notes

- The project generates 1,000 orders using a fixed random seed to ensure consistent report values across runs.
- Execution timings vary depending on the machine and runtime environment.
