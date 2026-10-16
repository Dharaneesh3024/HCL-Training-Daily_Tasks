package library;

import java.time.LocalDate;

public class Loan {

    private final Book book;
    private final String member;
    private final LocalDate dueDate;

    public Loan(Book book, String member, LocalDate dueDate) {
        this.book = book;
        this.member = member;
        this.dueDate = dueDate;
    }

    public boolean isOverdue(LocalDate today) {
        return dueDate.isBefore(today);
    }

    @Override
    public String toString() {
        return book.getTitle() + " -> " + member + " (due " + dueDate + ")";
    }
}
