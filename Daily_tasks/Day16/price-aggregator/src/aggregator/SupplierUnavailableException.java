package aggregator;

public class SupplierUnavailableException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SupplierUnavailableException(String message) {
        super(message);
    }
}
