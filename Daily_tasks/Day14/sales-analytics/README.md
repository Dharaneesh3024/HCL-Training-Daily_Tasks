\# Sales Analytics



Day 14 - Streams API. A Java project that analyzes 1,000 generated orders using streams and collectors, and compares sequential and parallel streams.



\## Project Structure



```text

sales-analytics/

└── src/

&#x20;   ├── analytics/

&#x20;   │   └── SalesAnalytics.java

&#x20;   ├── app/

&#x20;   │   └── Main.java

&#x20;   ├── data/

&#x20;   │   └── OrderGenerator.java

&#x20;   └── model/

&#x20;       ├── Order.java

&#x20;       ├── OrderLine.java

&#x20;       └── OrderStatus.java

```



\## Reports



All reports in `SalesAnalytics` are built using the Collectors API.



| Report | Collector or Operation |

|---|---|

| Revenue by category | `groupingBy` with `summingDouble` |

| Top 3 customers | `toMap` with merge function, `sorted`, `limit` |

| Delivered vs. not delivered | `partitioningBy` |

| Orders per status | `groupingBy` with `counting` |

| Average basket size | `averagingInt` |

| Comma-joined product list | `flatMap`, `distinct`, `sorted`, `joining` |

| Order value statistics | `summarizingDouble` |

| Total revenue | `reduce` |

| Cancelled order check | `anyMatch` |



\## Streams Concepts Demonstrated



\- \*\*Stream Creation:\*\* `Stream.of`, `Arrays.stream`, `IntStream.rangeClosed`, `Stream.iterate`

\- \*\*Intermediate Operations:\*\* `filter`, `map`, `flatMap`, `sorted`, `distinct`, `limit`, `peek`

\- \*\*Terminal Operations:\*\* `collect`, `reduce`, `count`, `anyMatch`, `findFirst`

\- \*\*Laziness:\*\* No processing occurs until a terminal operation runs. `findFirst` stops processing once the first matching result is found.

\- \*\*Sequential vs. Parallel Streams:\*\* CPU-intensive operations can benefit from parallel processing, while lightweight operations may not.

\- \*\*No External Mutation:\*\* Pipelines produce results without modifying external state.



\## Output Sections



1\. Creating Streams

2\. Laziness

3\. Sales Reports

4\. Sequential vs. Parallel Timing



\## Notes



\- The project generates 1,000 orders using a fixed random seed, ensuring consistent report values across runs.

\- Execution timings vary depending on the machine and runtime environment.



