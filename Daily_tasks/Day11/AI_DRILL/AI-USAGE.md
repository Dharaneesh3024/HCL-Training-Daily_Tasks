# AI Usage Log

## Entry 1 – Student POJO: Vague vs Specific Prompt

- **Date:** 2026-10-17
- **Tool:** AI assistant (Copilot)
- **Prompt A:** "Create a Student class"
- **Prompt B:** "Create an immutable-id Student POJO with private fields `id`, `name`, `gpa` (0 to 10), and a list of courses. Validate in constructor and setters, return courses as read-only, implement `equals()` and `hashCode()` on `id` and `toString()`. No public fields."
- **Output A:** Public fields, no validation, and no `equals()` or `hashCode()`.
- **Output B:** Private fields, validation, read-only courses, and `equals()` and `hashCode()` based on `id`.
- **Review:** Compared both implementations in section 1 of `DrillMain`. Version A accepted `gpa = -5`, a null name, and a null courses list. Version B rejected all three.
- **Decision:** Accepted version B. More specific prompts resulted in fewer review fixes.

## Entry 2 – StudentService Draft

- **Date:** 2026-10-17
- **Tool:** AI assistant (Copilot)
- **Prompt:** "Generate a StudentService with add, getAll, findById, findByName, averageGpa, addFromLine and remove."
- **Output:** Working code that compiled but contained 8 problems (see section 2 of `REVIEW.md`).
- **Worst Findings:**
  - `getAll()` returned the internal list.
  - `==` was used to compare Strings.
  - An empty `catch` block swallowed invalid input.
  - Null students and duplicate IDs were accepted.
- **Changes Made:**
  - Replaced the list with a `LinkedHashMap` keyed by ID.
  - Returned read-only copies.
  - Used `Optional` and `OptionalDouble` for results.
  - Used `equalsIgnoreCase()` for String comparison.
  - Threw exceptions while preserving the original cause.
- **Verification:** Ran the draft and fixed versions side by side in `DrillMain`.
- **Takeaway:** AI-generated code can compile and look tidy while still containing problems. Testing edge cases such as null values, duplicates, empty collections, and invalid input helps uncover these issues.

## Entry 3 – MaintenanceBlock Entity, Repository Use, and FR7/FR8

- **Date:** 2026-10-17
- **Tool:** AI assistant (Copilot)
- **Prompt:** "Create a MaintenanceBlock entity and reuse the generic `CrudRepository<T, ID>` and `InMemoryRepository`. Add FR7 (admin blocks a vehicle for maintenance, no overlap with bookings or other blocks) and FR8 (utilisation percentage for a period, per vehicle and per branch)."
- **Output:** `MaintenanceBlock` entity and `FleetService` methods: `blockForMaintenance()`, `utilisationPercent()`, and `utilisationByBranch()`.
- **Review:** Ran the checklist. Fields were private, validation was present, no raw types were used, and no exceptions were swallowed. Running `-Xlint:all` revealed two warnings: missing `serialVersionUID` and `this` escaping from a constructor. Both were fixed.
- **Verification:** FR7 conflict cases and a manual FR8 calculation (60.0% utilisation for vehicle 1) matched the program output.
- **Decision:** Accepted after fixes. Utilisation is calculated as booked days divided by available days, where available days equal the total period minus maintenance days.