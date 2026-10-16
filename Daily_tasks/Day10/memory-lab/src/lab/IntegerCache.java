package lab;

public class IntegerCache {

    public static void main(String[] args) {
        Integer a = 127;
        Integer b = 127;
        Integer c = 128;
        Integer d = 128;

        System.out.println("127 == 127      : " + (a == b));
        System.out.println("128 == 128      : " + (c == d));
        System.out.println("128 equals 128  : " + c.equals(d));

        Integer low1 = -128;
        Integer low2 = -128;
        Integer low3 = -129;
        Integer low4 = -129;
        System.out.println("-128 == -128    : " + (low1 == low2));
        System.out.println("-129 == -129    : " + (low3 == low4));
    }
}
