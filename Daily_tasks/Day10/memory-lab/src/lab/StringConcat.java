package lab;

public class StringConcat {

    public static void main(String[] args) {
        int n = 50_000;

        long start = System.nanoTime();
        String s = "";
        for (int i = 0; i < n; i++) {
            s += "x";
        }
        long concatMs = (System.nanoTime() - start) / 1_000_000;

        start = System.nanoTime();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append("x");
        }
        String built = sb.toString();
        long builderMs = (System.nanoTime() - start) / 1_000_000;

        System.out.println("n = " + n + ", lengths " + s.length() + " / " + built.length());
        System.out.println("String +=     : " + concatMs + " ms");
        System.out.println("StringBuilder : " + builderMs + " ms");
    }
}
