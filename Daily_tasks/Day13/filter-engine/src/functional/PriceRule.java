package functional;

@FunctionalInterface
public interface PriceRule {

    double apply(double unitPrice, int quantity);

    default PriceRule thenRound() {
        return (unitPrice, quantity) -> Math.round(apply(unitPrice, quantity));
    }
}
