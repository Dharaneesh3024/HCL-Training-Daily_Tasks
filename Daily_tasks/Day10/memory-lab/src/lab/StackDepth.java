package lab;

public class StackDepth {

    private static int depth = 0;

    private static void recurse() {
        depth++;
        recurse();
    }

    public static void main(String[] args) {
        try {
            recurse();
        } catch (StackOverflowError e) {
            System.out.println("StackOverflowError at depth " + depth);
        }
    }
}
