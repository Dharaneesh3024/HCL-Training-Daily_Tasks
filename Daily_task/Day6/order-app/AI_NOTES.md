# Day 6: AI stack trace notes

Tool used: GitHub Copilot Chat (VS Code)

## How to read a trace (my own summary)
- First line = exception type + message.
- Lines below = call stack, top is where it was thrown, bottom is where it started (main).
- "Caused by:" = the original exception that got wrapped. The real problem is usually the last "Caused by".

---
## Trace 1: checked exception with cause (order "bag:5")
Paste the full trace here:
```
(paste)
```
Prompt I gave Copilot:
> (paste)

Copilot's explanation (copied):
> (paste)

| Claim by Copilot | Right / Wrong | How I checked |
|---|---|---|
| | | |

What I'd say myself: ...

---
## Trace 2: unchecked exception (order "pen:abc")
```
(paste)
```
Prompt:
> (paste)

Copilot's explanation:
> (paste)

| Claim by Copilot | Right / Wrong | How I checked |
|---|---|---|
| | | |

What I'd say myself: ...

---
## Things to verify in Copilot's answers
- Did it name the correct line and class where the exception was thrown?
- Did it say which exception is the cause and which is the wrapper?
- Did it say InsufficientStockException is checked and InvalidQuantityException is unchecked?
- Did it mention the finally block still ran?
- If it suggested a fix, did I actually try it?
