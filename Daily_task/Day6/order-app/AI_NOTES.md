# Day 6: AI Stack Trace Notes

**Tool used:** GitHub Copilot Chat (VS Code)

## How to read a trace
- First line shows the exception type and message.
- Stack frames show where the error occurred.
- `Caused by:` shows the underlying exception.

## Trace 1: Checked exception with cause (order "bag:5")

**Full trace:**
```text
Exception in thread "main" java.lang.RuntimeException: Failed to process order
    at OrderService.processOrder(OrderService.java:15)
    at Main.main(Main.java:5)
Caused by: InsufficientStockException: Insufficient stock for item: bag
    at InventoryService.checkStock(InventoryService.java:20)
    at OrderService.processOrder(OrderService.java:12)
    ... 1 more
```

**Prompt:**
> Explain this stack trace and identify the cause, wrapper exception, and possible fix.

**Copilot's explanation:**
> The order failed because the bag has insufficient stock. `InsufficientStockException` is the cause, wrapped inside a `RuntimeException`.

| Claim by Copilot | Right / Wrong | How I checked |
|---|---|---|
| `RuntimeException` is the wrapper. | Right | First line |
| `InsufficientStockException` is the cause. | Right | `Caused by:` section |
| `InsufficientStockException` is checked. | Right, if it extends `Exception`. | Class declaration |

**What I'd say myself:** The bag has insufficient stock, causing an exception that is wrapped in a `RuntimeException`.

## Trace 2: Unchecked exception (order "pen:abc")

**Full trace:**
```text
Exception in thread "main" InvalidQuantityException: Quantity must be a positive integer
    at OrderService.validateQuantity(OrderService.java:24)
    at OrderService.processOrder(OrderService.java:12)
    at Main.main(Main.java:5)
```

**Prompt:**
> Explain this exception, where it occurred, and whether it is checked or unchecked.

**Copilot's explanation:**
> `InvalidQuantityException` occurs because the quantity is invalid. If it extends `RuntimeException`, it is unchecked.

| Claim by Copilot | Right / Wrong | How I checked |
|---|---|---|
| The exception is `InvalidQuantityException`. | Right | First line |
| It is unchecked. | Right, if it extends `RuntimeException`. | Class declaration |
| The `finally` block executes. | Right, under normal execution. | Tested with a Java program |

**What I'd say myself:** The quantity is invalid, so `InvalidQuantityException` is thrown. Since it is unchecked, it does not need to be declared using `throws`.

## Things to verify
- Correct exception class and line number.
- Cause and wrapper identified correctly.
- Checked and unchecked classifications.
- Whether `finally` executed.
- Whether the suggested fix worked.