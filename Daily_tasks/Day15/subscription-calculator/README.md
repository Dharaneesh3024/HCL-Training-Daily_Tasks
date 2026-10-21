# Subscription Calculator

Day 15 - Optional and java.time. A Java project that calculates subscription expiry dates, remaining time, grace periods and time zone conversions with the java.time API, and uses Optional for all lookups.

## Project Structure

```
subscription-calculator/
`-- src/
    |-- app/          Main.java
    |-- calculator/   SubscriptionCalculator.java
    |-- exception/    SubscriptionNotFoundException.java
    |-- model/        Plan.java, Subscription.java, SubscriptionStatus.java
    `-- service/      SubscriptionService.java
```

## Optional

| Feature | Where |
|---|---|
| Optional.of, ofNullable, empty | Main, section 1 |
| map, filter, flatMap | SubscriptionService, Main |
| orElse vs orElseGet | Main, section 1 |
| ifPresentOrElse | Main, section 1 |
| orElseThrow with a custom exception | SubscriptionService.getById |

All find methods return Optional and no method returns null. Optional is used only for return values, not for fields or parameters.

## java.time

| Feature | Where |
|---|---|
| LocalDate and Period for expiry dates | SubscriptionCalculator.expiryDate |
| Days remaining and grace period status | SubscriptionCalculator |
| ZonedDateTime, IST to UTC and New York | SubscriptionCalculator.inZone |
| Duration until expiry | SubscriptionCalculator.timeUntil |
| LocalDateTime for reminders | SubscriptionCalculator.reminderTime |
| DateTimeFormatter | SubscriptionCalculator |
| Month-end and leap-year handling | Main, section 2 |

No java.util.Date is used.

## Requirements

- JDK 11 or newer

## Run

Run `app.Main` from your IDE, or from the project root:

```
javac -d out src/model/*.java src/exception/*.java src/calculator/*.java src/service/*.java src/app/*.java
java -cp out app.Main
```

## Output Sections

1. Optional
2. Dates, expiry and grace period
3. Time zones, Duration and reminder
