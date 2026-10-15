# Generic Toolkit



Day 9 – Generics. A small Java project covering generic classes and methods, bounded types, wildcards, try-with-resources, and a reusable in-memory repository loaded from CSV files.



## Structure

```

generic-toolkit/

├── data/

│   ├── products.csv

│   └── customers.csv

├── src/

│   ├── app/          Main.java

│   ├── model/        Product.java, Customer.java

│   ├── repository/   CrudRepository.java, InMemoryRepository.java

│   └── toolkit/      Pair.java, Box.java, Util.java,

│                     TrackedResource.java, CsvLoader.java

├── run.bat

└── run.sh

```



## What it covers



| Topic | Where |

|---|---|

| Generic class with two type parameters | `Pair<K, V>` |

| Bounded type parameter | `Box<T extends Number>` |

| Bounded generic method | `Util.max()` with `<T extends Comparable<T>>` |

| Wildcards (`? extends`, `? super`, `?`) | `Util.sumAll`, `Util.fillWithIntegers`, `Util.copy`, `Util.printAll` |

| try-with-resources and `AutoCloseable` | `TrackedResource`, `CsvLoader` |

| Generic repository reused across entities | `CrudRepository<T, ID>`, `InMemoryRepository<T, ID>` |

| Seed data from CSV | `CsvLoader.load()` |



## Requirements

- JDK 11 or newer


Manual:

```

javac -d out src/toolkit/\*.java src/model/\*.java src/repository/\*.java src/app/\*.java

java -cp out app.Main

```


## Using the repository for another entity



```java

InMemoryRepository<Customer, Integer> customers =

&#x20;       new InMemoryRepository<>(Customer::getId);

customers.saveAll(CsvLoader.load(Paths.get("data", "customers.csv"), Customer::fromCsv));

```



## Notes
- Compiles with `-Xlint:all` without warnings (no raw types, no unchecked operations).
- try-with-resources closes resources in reverse order of opening, including when an exception is thrown.
\- try-with-resources closes resources in reverse order of opening, including when an exception is thrown.

