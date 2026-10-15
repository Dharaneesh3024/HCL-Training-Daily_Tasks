package toolkit;

import java.util.Collection;
import java.util.List;

public final class Util {

    private Util() {}

    public static <T extends Comparable<T>> T max(List<T> items) {
        if (items.isEmpty()) {
            throw new IllegalArgumentException("List is empty");
        }
        T best = items.get(0);
        for (T item : items) {
            if (item.compareTo(best) > 0) {
                best = item;
            }
        }
        return best;
    }

    public static double sumAll(Collection<? extends Number> numbers) {
        double total = 0;
        for (Number n : numbers) {
            total += n.doubleValue();
        }
        return total;
    }

    public static void fillWithIntegers(List<? super Integer> target, int count) {
        for (int i = 1; i <= count; i++) {
            target.add(i);
        }
    }

    public static void printAll(Collection<?> items) {
        for (Object o : items) {
            System.out.println("  " + o);
        }
    }

    public static <T> void copy(List<? extends T> source, List<? super T> target) {
        for (T item : source) {
            target.add(item);
        }
    }
}
