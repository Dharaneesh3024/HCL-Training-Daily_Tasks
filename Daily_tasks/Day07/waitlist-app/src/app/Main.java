package app;

import library.Book;
import library.Loan;
import library.WaitRequest;

import java.time.LocalDate;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        catalogue();
        dedup();
        waitlist();
        facultyFirst();
        overdueRemoval();
        cmeDemo();
        timing();
    }

    // 1. ArrayList: catalogue is read by index and by looping far more than it is inserted into
    static void catalogue() {
        System.out.println("=== 1. Catalogue (ArrayList) ===");
        List<Book> catalogue = new ArrayList<>();
        catalogue.add(new Book("111", "Effective Java"));
        catalogue.add(new Book("222", "Clean Code"));
        catalogue.add(new Book("333", "Head First Java"));
        catalogue.add(new Book("444", "Algorithms"));

        for (Book b : catalogue) {
            System.out.println("  " + b);
        }
        System.out.println("  book at index 2: " + catalogue.get(2));

        // TreeSet when we want titles always sorted
        Set<String> sortedTitles = new TreeSet<>();
        for (Book b : catalogue) {
            sortedTitles.add(b.getTitle());
        }
        System.out.println("  sorted titles (TreeSet): " + sortedTitles);
    }

    // 2. LinkedHashSet: no duplicates, but keeps the order people registered in
    static void dedup() {
        System.out.println("\n=== 2. De-dup member IDs (LinkedHashSet) ===");
        String[] scans = {"M01", "M02", "M01", "M03", "M02", "M04"};
        Set<String> members = new LinkedHashSet<>();
        for (String id : scans) {
            if (!members.add(id)) {
                System.out.println("  duplicate ignored: " + id);
            }
        }
        System.out.println("  unique, in scan order: " + members);
        // HashSet would also drop duplicates, but print in no guaranteed order
        System.out.println("  same ids in a HashSet:  " + new HashSet<>(members));
    }

    // 3. ArrayDeque: plain first-come-first-served queue
    static void waitlist() {
        System.out.println("\n=== 3. Waitlist (ArrayDeque, FIFO) ===");
        Deque<String> waitlist = new ArrayDeque<>();
        waitlist.offer("Arun");
        waitlist.offer("Divya");
        waitlist.offer("Karthik");
        System.out.println("  waiting: " + waitlist);
        System.out.println("  next up (peek): " + waitlist.peek());
        System.out.println("  book returned, given to: " + waitlist.poll());
        System.out.println("  still waiting: " + waitlist);
    }

    // 4. PriorityQueue: faculty jump the line
    static void facultyFirst() {
        System.out.println("\n=== 4. Faculty-first requests (PriorityQueue) ===");
        PriorityQueue<WaitRequest> pq = new PriorityQueue<>();
        pq.add(new WaitRequest("Arun", false, 1));
        pq.add(new WaitRequest("Dr. Meena", true, 2));
        pq.add(new WaitRequest("Divya", false, 3));
        pq.add(new WaitRequest("Prof. Ravi", true, 4));

        System.out.println("  served in this order:");
        while (!pq.isEmpty()) {
            System.out.println("    " + pq.poll());
        }
    }

    // 5. Iterator.remove: the safe way to delete while looping
    static void overdueRemoval() {
        System.out.println("\n=== 5. Remove overdue loans (Iterator.remove) ===");
        LocalDate today = LocalDate.now();
        List<Loan> loans = new ArrayList<>();
        loans.add(new Loan(new Book("111", "Effective Java"), "Arun", today.minusDays(10)));
        loans.add(new Loan(new Book("222", "Clean Code"), "Divya", today.plusDays(5)));
        loans.add(new Loan(new Book("333", "Head First Java"), "Karthik", today.minusDays(2)));

        Iterator<Loan> it = loans.iterator();
        while (it.hasNext()) {
            Loan l = it.next();
            if (l.isOverdue(today)) {
                System.out.println("  removing overdue: " + l);
                it.remove();
            }
        }
        System.out.println("  remaining: " + loans);

        // ListIterator can also go backwards and set/add while walking
        ListIterator<Loan> li = loans.listIterator(loans.size());
        while (li.hasPrevious()) {
            System.out.println("  (backwards) " + li.previous());
        }
    }

    // 6. What goes wrong with for-each + remove
    static void cmeDemo() {
        System.out.println("\n=== 6. ConcurrentModificationException ===");
        List<String> names = new ArrayList<>(List.of("a", "b", "c", "d"));
        try {
            for (String n : names) {
                if (n.equals("b")) {
                    names.remove(n);   // modifies the list behind the iterator's back
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("  caught: " + e);
            System.out.println("  fix: use Iterator.remove() or removeIf()");
        }
        names.removeIf(n -> n.equals("b"));
        System.out.println("  after removeIf: " + names);
    }

    // 7. ArrayList vs LinkedList timing
    static void timing() {
        System.out.println("\n=== 7. Timing: ArrayList vs LinkedList ===");
        final int N = 50_000;
        final int GETS = 20_000;

        // warm-up so the JIT does not skew the first measurement
        run(new ArrayList<>(), 5_000, 2_000);
        run(new LinkedList<>(), 5_000, 2_000);

        long[] a = run(new ArrayList<>(), N, GETS);
        long[] l = run(new LinkedList<>(), N, GETS);

        System.out.println("  N = " + N + " items, " + GETS + " get(i) calls (times in ms)");
        System.out.printf("  %-28s %10s %10s%n", "Operation", "ArrayList", "LinkedList");
        System.out.printf("  %-28s %10d %10d%n", "add at end (N times)", a[0], l[0]);
        System.out.printf("  %-28s %10d %10d%n", "insert at index 0 (N times)", a[1], l[1]);
        System.out.printf("  %-28s %10d %10d%n", "get(i) in the middle", a[2], l[2]);
    }

    static long[] run(List<Integer> list, int n, int gets) {
        long t = System.nanoTime();
        for (int i = 0; i < n; i++) list.add(i);
        long addEnd = (System.nanoTime() - t) / 1_000_000;

        list.clear();
        t = System.nanoTime();
        for (int i = 0; i < n; i++) list.add(0, i);
        long addFront = (System.nanoTime() - t) / 1_000_000;

        Random r = new Random(1);
        long sum = 0;
        t = System.nanoTime();
        for (int i = 0; i < gets; i++) sum += list.get(r.nextInt(list.size()));
        long get = (System.nanoTime() - t) / 1_000_000;
        if (sum == 42) System.out.print("");   // keep the loop from being optimised away

        return new long[]{addEnd, addFront, get};
    }
}
