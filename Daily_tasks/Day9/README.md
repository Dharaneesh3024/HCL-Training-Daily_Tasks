# Generic Toolkit

A small Java project demonstrating **Generics, Bounded Types, Wildcards, Try-with-Resources, and a Reusable In-Memory Repository**. The project also supports loading seed data from CSV files.

## Requirements

- JDK 11 or newer

## Project Structure

```text
generic-toolkit/
├── data/
│   ├── products.csv
│   └── customers.csv
├── src/
│   ├── app/
│   │   └── Main.java
│   ├── model/
│   │   ├── Product.java
│   │   └── Customer.java
│   ├── repository/
│   │   ├── CrudRepository.java
│   │   └── InMemoryRepository.java
│   └── toolkit/
│       ├── Pair.java
│       ├── Box.java
│       ├── Util.java
│       ├── TrackedResource.java
│       └── CsvLoader.java
```

## Concepts Covered

| Topic | Implementation |
|---|---|
| Generic class with two type parameters | `Pair<K, V>` |
| Bounded type parameter | `Box<T extends Number>` |
| Bounded generic method | `Util.max()` using `<T extends Comparable<T>>` |
| Upper-bounded wildcard | `? extends` |
| Lower-bounded wildcard | `? super` |
| Unbounded wildcard | `?` |
| Generic utility methods | `sumAll`, `fillWithIntegers`, `copy`, `printAll` |
| Try-with-resources | `TrackedResource` and `CsvLoader` |
| Automatic resource management | `AutoCloseable` |
| Generic repository | `CrudRepository<T, ID>` and `InMemoryRepository<T, ID>` |
| CSV data loading | `CsvLoader.load()` |

## Compilation and Execution

Run the following commands from the project root directory.

### Compile

```bash
javac -Xlint:all -d out src/toolkit/*.java src/model/*.java src/repository/*.java src/app/*.java
```

### Run

```bash
java -cp out app.Main
```

## Reusing the Repository for Another Entity

The generic repository can be reused for different entity types without duplicating CRUD logic.

For example, to create a repository for customers and load customer data from a CSV file:

```java
InMemoryRepository<Customer, Integer> customers =
        new InMemoryRepository<>(Customer::getId);

customers.saveAll(
    CsvLoader.load(
        Paths.get("data", "customers.csv"),
        Customer::fromCsv
    )
);
```

Here, `Customer::getId` supplies the customer's ID, while `Customer::fromCsv` converts CSV records into `Customer` objects.

## Key Features

- Reusable generic classes and methods.
- Type-safe operations using bounded types and wildcards.
- In-memory CRUD operations for different entity types.
- CSV-based initialization of sample data.
- Automatic resource cleanup using try-with-resources.
- Designed to compile with `-Xlint:all` without warnings, avoiding raw types and unchecked operations.

## Learning Objective

The goal of this project is to understand how Java Generics improve code reuse and type safety while applying them to practical utilities, resource management, CSV processing, and repository design.
