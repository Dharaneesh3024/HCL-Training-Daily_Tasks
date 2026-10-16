package lab;

import java.util.ArrayList;
import java.util.List;

public class GcDemo {

    static volatile int[] sink;

    public static void main(String[] args) {
        List<int[]> survivors = new ArrayList<>();
        long checksum = 0;

        for (int i = 0; i < 3_000_000; i++) {
            int[] temp = new int[64];
            temp[0] = i;
            sink = temp;
            checksum += temp[0];

            if (i % 20_000 == 0) {
                survivors.add(new int[2_000]);
            }
        }

        System.out.println("survivors kept: " + survivors.size() + ", checksum " + checksum);
    }
}
