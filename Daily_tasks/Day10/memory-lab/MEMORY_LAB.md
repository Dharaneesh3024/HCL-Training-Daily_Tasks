# Memory Lab – Day 10

## Environment

* **JDK:** OpenJDK 21
* **Garbage Collector:** G1 (default)
* **Focus:** JVM Stack, Heap Memory, String Performance, Garbage Collection, and Integer Caching

## Experiment Results

|#|Experiment|Configuration|Result|
|-|-|-|-|
|1|Stack Overflow Depth|`-Xss256k`|3,644 calls|
|1|Stack Overflow Depth|`-Xss512k`|10,198 calls|
|1|Stack Overflow Depth|`-Xss1m`|23,306 calls|
|1|Stack Overflow Depth|`-Xss2m`|49,520 calls|
|2|OutOfMemoryError|`-Xmx64m`, 1 MB blocks|Failed after 31 MB|
|3|String Concatenation|50,000 appends using `+=`|268 ms|
|3|StringBuilder|50,000 appends|4 ms|
|4|Garbage Collection|`-Xmx64m -Xlog:gc`|22 young GCs; 0.4–0.8 ms each|
|5|Integer Cache|`127 == 127`|`true`|
|5|Integer Cache|`128 == 128`|`false`|
|5|Integer Cache|`-128 == -128` / `-129 == -129`|`true` / `false`|

\---

## 1\. StackOverflowError and `-Xss`

Each method invocation uses a stack frame to store information such as local variables, operands, and method execution state. When a recursive method has no terminating condition, frames continue accumulating until the thread's stack limit is reached, resulting in a `StackOverflowError`.

The `-Xss` JVM option controls the stack size allocated to each thread. Increasing the stack size allows more recursive calls before the error occurs.

### Results

|Stack Size|Recursive Calls|
|-|-:|
|`-Xss256k`|3,644|
|`-Xss512k`|10,198|
|`-Xss1m`|23,306|
|`-Xss2m`|49,520|

The number of calls does not increase in exact proportion to the stack size because stack-frame requirements and JVM execution optimizations can affect the result.

**Conclusion:** Increasing `-Xss` can delay a stack overflow, but the correct solution is to implement a proper base case or use an iterative approach when appropriate.

## 2\. OutOfMemoryError with `-Xmx64m`

The experiment repeatedly allocates 1 MB arrays and stores references to them in a list.

Because the list retains references to these arrays, they remain reachable and cannot be reclaimed by the garbage collector.

The JVM is configured with a maximum heap size of 64 MB using `-Xmx64m`. However, the program fails after allocating approximately 31 MB of array data with the error:

`java.lang.OutOfMemoryError: Java heap space`

The maximum heap size is not entirely available for application arrays. Object headers, array references, other live objects, and JVM memory-management requirements also contribute to memory usage.

After the list is cleared, the arrays may become eligible for garbage collection, provided no other references to them exist.

**Conclusion:** Retaining unnecessary object references can cause heap exhaustion even when garbage collection is enabled.

## 3\. String Concatenation: `+=` vs `StringBuilder`

Java `String` objects are immutable, meaning their contents cannot be changed after creation.

When repeatedly concatenating strings using `+=`, new string objects are created, and previous characters may need to be copied into the new result. Repeated concatenation in a loop can therefore lead to quadratic time complexity.

`StringBuilder`, on the other hand, maintains a mutable character buffer and supports efficient repeated appends.

### Benchmark Results

|Method|Number of Appends|Execution Time|
|-|-:|-:|
|String `+=`|50,000|268 ms|
|`StringBuilder`|50,000|4 ms|

In this experiment, `StringBuilder` completed the operation significantly faster than repeated string concatenation.

**Conclusion:** Use `StringBuilder` for repeated string modifications, especially inside loops. The exact performance difference depends on the JVM, workload, and execution environment.

## 4\. Garbage Collection with `-Xlog:gc`

The experiment was executed with the following JVM options:

```bash
-Xmx64m -Xlog:gc
```

The `-Xmx64m` option limits the maximum Java heap size to 64 MB, while `-Xlog:gc` enables garbage-collection logging.

### Observations

* The JVM performed 22 young garbage collections.
* Each recorded pause lasted approximately 0.4–0.8 milliseconds.
* The logged heap usage decreased from approximately 38 MB to 1 MB after each young collection.
* Most temporary objects became unreachable shortly after allocation.
* Only the objects retained by the program, including the 150 small arrays mentioned in the experiment, remained reachable.

With the G1 garbage collector, young collections reclaim unused objects in the young generation and retain or evacuate surviving objects as needed.

The observed heap reduction indicates that most allocated objects were short-lived.

**Conclusion:** Garbage collection efficiently reclaims unreachable objects. Short-lived objects are commonly collected during young-generation collections, although actual collection behavior depends on allocation patterns and JVM decisions.

## 5\. Integer Cache Pitfall

Java caches `Integer` objects for values from `-128` to `127` by default. Autoboxing uses `Integer.valueOf()`, which can return a cached object for values in this range.

When comparing boxed integers with `==`, Java compares object references rather than their numerical values.

### Results

|Comparison|Result|Explanation|
|-|-|-|
|`127 == 127`|`true`|Both references point to the cached object|
|`128 == 128`|`false`|The two boxed values are distinct objects in this experiment|
|`-128 == -128`|`true`|Both references point to the cached object|
|`-129 == -129`|`false`|The two boxed values are distinct objects in this experiment|

### Recommended Approach

Use `.equals()` when comparing the values of wrapper objects.

```java
Integer a = 128;
Integer b = 128;

System.out.println(a == b);      // false in this experiment
System.out.println(a.equals(b)); // true
```

The cache can be configured beyond the default range in some JVM implementations, so reference-comparison results outside the guaranteed cache range should not be relied upon.

**Conclusion:** Use `==` for reference identity when that is what you intend to test, and use `.equals()` for comparing wrapper-object values.

\---

## Overall Learnings

* **Stack memory:** Recursive calls consume stack space, and excessive recursion can cause `StackOverflowError`.
* **Heap memory:** Keeping unnecessary references prevents objects from becoming eligible for garbage collection.
* **String performance:** `StringBuilder` is generally more efficient than repeated `String` concatenation in loops.
* **Garbage collection:** Young-generation collections reclaim unreachable objects and help manage heap usage.
* **Integer caching:** Autoboxing and wrapper-object caching can produce unexpected results when using `==`.

**Day 10 completed:** Explored JVM memory limits, object allocation, garbage collection, string performance, and wrapper-object caching using OpenJDK 21.

