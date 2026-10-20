# Product Filter Engine

**Day 13 – Functional Programming**



A small Java project exploring functional programming concepts, including lambda expressions, built-in functional interfaces (`Predicate`, `Function`, `Consumer`, and `Supplier`), method references, and a custom `@FunctionalInterface` used to implement the Strategy pattern.



## 📁 Project Structure



```text

filter-engine/

└── src/

&#x20;   ├── app/

&#x20;   │   └── Main.java

&#x20;   ├── engine/

&#x20;   │   └── FilterEngine.java

&#x20;   ├── functional/

&#x20;   │   └── PriceRule.java

&#x20;   └── model/

&#x20;       └── Product.java

```



## 📚 Topics Covered



| Topic | Implementation |

|---|---|

| Composed predicates (`and`, `or`, `negate`) | `FilterEngine`, `Main` — Section 1 |

| Function chains (`andThen`, `compose`) | `Main` — Section 2 |

| Consumer chaining (`andThen`) | `Main` — Section 3 |

| Supplier for default values | `FilterEngine.orDefault`, `Main` — Section 4 |

| Four kinds of method references | `Main` — Section 5 |

| Custom `@FunctionalInterface` with a default method | `PriceRule` |

| Strategy pattern using lambdas | `Main` — Section 6 |

| Lambda capturing an effectively final variable | `Main` — Sections 2 and 6 |



## 🔗 Method Reference Types



| Type | Example |

|---|---|

| Static method reference | `Integer::parseInt` |

| Bound instance method reference | `System.out::println` |

| Unbound instance method reference | `Product::getName` |

| Constructor reference | `ArrayList::new` |



## ⚙️ Requirements



**JDK 8 or newer**



## 🚀 How to Run



### Option 1: Run from an IDE



Import the project into your preferred Java IDE and run `app.Main`.



### Option 2: Compile and Run from the Terminal



Execute the following commands from the project root:



**1. Compile the source files**



```bash

javac -d out src/model/\*.java src/functional/\*.java src/engine/\*.java src/app/\*.java

```



**2. Run the application**



```bash

java -cp out app.Main

```



## 📝 Notes



- Compiles with `-Xlint:all` without warnings.

- No anonymous inner classes are used.

- Lambdas that simply invoke a single method are replaced with method references where appropriate.



