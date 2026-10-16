package lab;

import java.util.ArrayList;
import java.util.List;

public class HeapOom {

    public static void main(String[] args) {
        List<byte[]> hold = new ArrayList<>();
        try {
            while (true) {
                hold.add(new byte[1024 * 1024]);
            }
        } catch (OutOfMemoryError e) {
            int blocks = hold.size();
            hold.clear();
            System.out.println("OutOfMemoryError after " + blocks + " MB: " + e.getMessage());
        }
    }
}
