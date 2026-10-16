# Project Review – Memory and Code Pitfalls

**Project:** Car Rental Management System  
**Technology Stack:** Java, Spring Boot, Maven, Spring Data JPA, MySQL  
**Reviewed Component:** `RentalService` – car booking and return operations

## 1\. Findings

The following review is based on the actual Spring Boot project implementation and the issues identified while developing the rental functionality.

|#|Finding|Where|Risk|Fix / Improvement|
|-|-|-|-|-|
|1|Monetary calculations using `double`|Rental amount calculation in `RentalService`|Floating-point precision errors may affect rental charges.|Use `BigDecimal` for monetary calculations and define an appropriate rounding policy.|
|2|Missing date validation|`rentCar()`|Null dates or an end date that is not after the start date can produce invalid bookings or incorrect rental durations.|Validate both dates and require `endDate.isAfter(startDate)`.|
|3|Returning an already-returned rental|`returnCar()`|Repeated return requests may process the same rental more than once.|Check the rental status and allow returns only for active rentals.|
|4|Rental duration calculation|`rentCar()`|The calculation `endDate.toEpochDay() - startDate.toEpochDay()` treats the end date as exclusive. This may differ from the intended billing policy.|Define whether rental days are inclusive or exclusive and calculate charges consistently.|
|5|Concurrent booking attempts|`rentCar()`|Two requests may attempt to book the same car for overlapping periods.|Use the existing transaction and pessimistic-locking approach, together with appropriate availability validation.|
|6|Object and database resource management|Spring Data JPA repositories|Incorrectly retaining unnecessary objects or bypassing managed resource handling can increase resource usage.|Let Spring Data JPA manage database resources and avoid unnecessary long-lived references.|

**Important:** A growing static `bookings` list, an `Integer` cache comparison bug, inefficient string concatenation, and unclosed CSV files were mentioned in the earlier standalone `RentalDemo` review. They are not confirmed findings in the current Spring Boot project and should not be reported as existing defects without checking the corresponding source code.

## 2\. Booking Flow: Stack and Heap

The actual booking operation follows the Spring Boot architecture:

```text
Client / Postman
       |
       v
RentalController
       |
       v
RentalService
       |
       +------> CarRepository
       |
       +------> CustomerRepository
       |
       +------> RentalRepository
       |
       +------> MaintenanceRepository
       |
       v
     MySQL
```

The controller receives the HTTP request, the service executes the booking logic, repositories access persisted data, and MySQL stores the resulting rental information.

### Conceptual Stack and Heap During `rentCar()`

```text
STACK (Executing Thread)              HEAP
+---------------------------+         +------------------------+
| Controller method frame   |         | RentalService bean      |
|   Request parameters      |         +------------------------+
+---------------------------+         | Repository beans       |
| RentalService method      |         +------------------------+
|   customerId              |         | Car entity              |
|   carId                   |         | Customer entity         |
|   startDate               |         | Rental entity           |
|   endDate                 |         +------------------------+
|   days                    |                   |
|   total                   |                   v
|   Entity references ------+--------> Objects and references
+---------------------------+         managed during execution
                                     
                                      DATABASE (MySQL)
                                      +------------------------+
                                      | Cars                   |
                                      | Customers              |
                                      | Rentals                |
                                      | Maintenance records    |
                                      +------------------------+
```

*Note: This is a conceptual illustration, not a literal memory dump. Actual stack-frame contents depend on JVM implementation and optimization. Database records are persisted separately from Java heap objects.*

## 3\. What Happens During and After Booking?

1. The controller receives the booking request and passes the relevant parameters to `RentalService`.
2. `RentalService` retrieves the car and customer through their repositories.
3. The service checks car availability, existing rental overlaps, and maintenance overlaps.
4. The rental duration and amount are calculated.
5. A new rental is created with an `ACTIVE` status and saved through the repository.
6. When the method returns, its local variables are no longer available as local variables in that method.
7. Java objects may remain reachable through other objects, Spring-managed components, or references held by the application.
8. Persisted rental data remains in MySQL independently of whether the corresponding Java entity object is still reachable in memory.

The garbage collector can reclaim eligible Java objects when they are no longer reachable from garbage-collection roots. It does not delete persisted database records.

## 4\. Memory Management and Spring Boot

### Stack Memory

* Stores method execution state and local variables in the conceptual JVM model.
* Each executing method uses a stack frame.
* Excessive recursion can cause `StackOverflowError`.
* Returning from a method removes its active stack frame.

### Heap Memory

* Holds Java objects such as `Car`, `Customer`, and `Rental` entities.
* Holds objects created or used by Spring-managed services and repositories.
* Objects may remain reachable through other objects or application components.
* Objects that are no longer reachable become eligible for garbage collection.

### Static References

A static collection that continuously retains booking objects can prevent those objects and their referenced vehicles from becoming eligible for garbage collection. However, this particular issue should only be attributed to the project if such a collection is actually present.

### Database Persistence

Spring Data JPA and MySQL serve different purposes from JVM memory:

* **JVM heap:** Holds Java objects while they are in use.
* **Repositories:** Provide the persistence operations used by the application.
* **MySQL:** Stores records beyond the lifetime of an individual Java method or object.

## 5\. Key Learnings

* **Money calculations:** Prefer `BigDecimal` when precise decimal arithmetic is required for rental charges.
* **Input validation:** Validate booking dates before calculating duration or creating a rental.
* **Rental lifecycle:** Prevent invalid state transitions, such as returning an already-returned rental.
* **Concurrency:** Transaction management and pessimistic locking help protect booking operations against concurrent requests.
* **Object lifecycle:** Method completion does not necessarily make every object created or accessed by that method eligible for garbage collection.
* **Persistence:** Java object lifetime and database record lifetime are independent.
* **Layered architecture:** Controllers handle HTTP requests, services implement business logic, repositories handle persistence, and MySQL stores the data.

## Conclusion

This review focuses on the actual Car Rental Management System and its booking and return operations. It highlights monetary precision, date validation, rental-state management, concurrency, and Java object lifecycle considerations.

These improvements help make the application more reliable, maintainable, and safer when multiple booking requests are processed.

