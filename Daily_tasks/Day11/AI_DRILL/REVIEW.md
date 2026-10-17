# Day 11 – Prompt and Review Drill

## 1. Vague Prompt vs Specific Prompt

| Aspect | Vague Prompt | Specific Prompt |
|---|---|---|
| Prompt | "Create a Student class" | "Create an immutable-ID Student POJO with private fields `id`, `name`, `gpa` (0 to 10), and a list of courses. Validate in the constructor and setters, return courses as read-only, implement `equals()` and `hashCode()` on `id`, and override `toString()`. No public fields." |
| Fields | Public | Private, with `id` declared `final` |
| Validation | None (`gpa = -5`, `name = null`, and `courses = null` are accepted) | `id > 0`, name must not be blank, and GPA must be between 0 and 10 |
| Courses List | Public and replaceable | Read-only view, with duplicates ignored |
| `equals()` / `hashCode()` | Missing | Implemented using `id` |
| Usable in a `HashSet` or `Map` | Not reliable | Yes |

**Result:** The vague prompt produced a class that compiles but allows invalid data. The specific prompt produced a class that enforces its own rules.

## 2. StudentService Review Log

| # | Issue Found in the AI Draft | Checklist Item | How It Showed Up | Fix |
|---|---|---|---|---|
| 1 | `getAll()` returns the internal list | Returned internal lists | Calling `getAll().clear()` emptied the service | Return `Collections.unmodifiableList(new ArrayList<>(...))` |
| 2 | Student fields are public | Encapsulation | `s.gpa = -5` was accepted | Use private fields and validated setters |
| 3 | Missing `equals()` and `hashCode()` in `Student` | Equality and hashing | `remove(new Student(1, ...))` did nothing; size remained 3 | Implement `equals()` and `hashCode()` using `id`; remove by ID |
| 4 | `add(null)` is accepted | Null handling | The next `findById()` call caused a `NullPointerException` | Reject null in `add()` |
| 5 | `ArrayList` with linear search and duplicate IDs allowed | Collection choice | Two students with ID 1 were stored | Use `LinkedHashMap<Integer, Student>` and reject duplicate IDs |
| 6 | `s.name == name` used for String comparison | Null handling / equality | `findByName(new String("Arun"))` returned null | Use `equalsIgnoreCase()` and return a list of matches |
| 7 | `addFromLine()` has an empty `catch (Exception e)` | Swallowed exceptions | Two invalid lines were accepted silently | Throw `IllegalArgumentException` with the original cause attached |
| 8 | `averageGpa()` divides by zero when the list is empty | Edge cases | Returned `NaN` | Return `OptionalDouble` |

**Result:** 8 issues were found and fixed, exceeding the target of 5 issues.

## 3. Project: MaintenanceBlock Entity, Repository, and FR7/FR8

| Check | Result |
|---|---|
| Entity fields | Private and validated; `to` must be after `from`, and `reason` is required |
| `equals()` / `hashCode()` | Implemented using `id` |
| Repository | Reuses the generic `CrudRepository<T, ID>` and `InMemoryRepository` |
| Lint (`-Xlint:all`) | 2 warnings found and fixed: missing `serialVersionUID` in the custom exception and `this` escape in the `Student` constructor (class made `final`) |
| FR7: Reject overlap with a booking | Verified |
| FR7: Reject overlap with another maintenance block | Verified |
| FR7: Reject an unknown vehicle | Verified |
| FR8: Utilisation calculation | Manually verified for vehicle 1 in November |

### FR8 Utilisation Calculation

- **Total period:** 30 days
- **Booked days:** 5 + 10 = 15 days
- **Blocked days:** 5 days
- **Available days:** 30 − 5 = 25 days


**Result:** The calculated utilisation of **60.0%** matches the program output.