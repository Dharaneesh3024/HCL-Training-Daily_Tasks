package exception;

// checked wrapper thrown by the service layer, always keeps the original cause
public class OrderProcessingException extends Exception {

    public OrderProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
