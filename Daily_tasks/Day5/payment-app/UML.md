# UML

```
              <<interface>>
               Refundable
          + refund(amount): boolean
          + refundPolicy(): String
              ^            ^
              . (implements) .
              .            .
      +-----------------------------+
      |  Payment  (abstract)        |
      |  - id, payer                |
      |  # paidTotal                |
      |  + pay(amount)              |
      |  + pay(amount, note)        |
      |  + getMode()   {abstract}   |
      |  # getCharge() {abstract}   |
      +-----------------------------+
          ^           ^           ^
          |           |           |
    CardPayment   UpiPayment   CashPayment
    (Refundable)  (Refundable)
```
Solid arrow = extends, dotted arrow = implements.
