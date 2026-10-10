package exception;

// checked: the caller is forced to deal with it
public class InsufficientStockException extends Exception {

    private final String item;
    private final int requested;
    private final int available;

    public InsufficientStockException(String item, int requested, int available) {
        super("Not enough stock for '" + item + "': wanted " + requested + ", have " + available);
        this.item = item;
        this.requested = requested;
        this.available = available;
    }

    public String getItem() { return item; }
    public int getRequested() { return requested; }
    public int getAvailable() { return available; }
}
