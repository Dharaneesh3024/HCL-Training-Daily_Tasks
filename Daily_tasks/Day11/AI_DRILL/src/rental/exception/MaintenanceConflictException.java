package rental.exception;

public class MaintenanceConflictException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MaintenanceConflictException(String message) {
        super(message);
    }
}
