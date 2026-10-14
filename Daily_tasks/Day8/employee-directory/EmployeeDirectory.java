import java.util.*;
import java.util.stream.Collectors;

public class EmployeeDirectory {

    static class Employee implements Comparable<Employee> {
        private final int id;
        private final String name;
        private final String dept;
        private final double salary;

        Employee(int id, String name, String dept, double salary) {
            this.id = id;
            this.name = name;
            this.dept = dept;
            this.salary = salary;
        }

        int getId() { return id; }
        String getName() { return name; }
        String getDept() { return dept; }
        double getSalary() { return salary; }

        @Override
        public int compareTo(Employee other) {
            return Integer.compare(this.id, other.id);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Employee)) return false;
            return id == ((Employee) o).id;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(id);
        }

        @Override
        public String toString() {
            return String.format("%d %-8s %-6s %9.2f", id, name, dept, salary);
        }
    }

    static class BadKey {
        final String code;
        BadKey(String code) { this.code = code; }

        @Override
        public boolean equals(Object o) {
            return o instanceof BadKey && code.equals(((BadKey) o).code);
        }
        // hashCode() not overridden, so Object's identity hash is used
    }

    static class GoodKey {
        final String code;
        GoodKey(String code) { this.code = code; }

        @Override
        public boolean equals(Object o) {
            return o instanceof GoodKey && code.equals(((GoodKey) o).code);
        }

        @Override
        public int hashCode() {
            return code.hashCode();
        }
    }

    public static void main(String[] args) {
        List<Employee> staff = new ArrayList<>(List.of(
            new Employee(105, "Meena",   "CSE",   82000),
            new Employee(101, "Arun",    "IT",    56000),
            new Employee(108, "Divya",   "ECE",   61000),
            new Employee(103, "Karthik", "CSE",   95000),
            new Employee(110, "Ravi",    "IT",    72000),
            new Employee(102, "Sneha",   "HR",    48000),
            new Employee(107, "Vikram",  "CSE",   95000),
            new Employee(104, "Priya",   "ECE",   67000),
            new Employee(109, "Naveen",  "IT",    59000)
        ));

        System.out.println("=== 1. HashMap lookup by id ===");
        Map<Integer, Employee> byId = new HashMap<>();
        for (Employee e : staff) {
            byId.put(e.getId(), e);
        }
        System.out.println("  id 103 -> " + byId.get(103));
        System.out.println("  id 999 -> " + byId.get(999));
        System.out.println("  has 108? " + byId.containsKey(108));

        System.out.println("\n=== 2. Headcount per department (merge) ===");
        Map<String, Integer> headcount = new HashMap<>();
        for (Employee e : staff) {
            headcount.merge(e.getDept(), 1, Integer::sum);
        }
        System.out.println("  " + headcount);

        System.out.println("\n=== 3. Iteration order of the three maps ===");
        Map<String, Integer> hm = new HashMap<>();
        Map<String, Integer> lhm = new LinkedHashMap<>();
        Map<String, Integer> tm = new TreeMap<>();
        for (Employee e : staff) {
            hm.merge(e.getDept(), 1, Integer::sum);
            lhm.merge(e.getDept(), 1, Integer::sum);
            tm.merge(e.getDept(), 1, Integer::sum);
        }
        System.out.println("  HashMap       (no promised order):  " + hm);
        System.out.println("  LinkedHashMap (insertion order):    " + lhm);
        System.out.println("  TreeMap       (sorted by key):      " + tm);

        System.out.println("\n=== 4. Department report (TreeMap) ===");
        Map<String, List<Employee>> report = new TreeMap<>();
        for (Employee e : staff) {
            report.computeIfAbsent(e.getDept(), k -> new ArrayList<>()).add(e);
        }
        for (Map.Entry<String, List<Employee>> entry : report.entrySet()) {
            Collections.sort(entry.getValue());   // uses compareTo (id)
            double total = 0;
            System.out.println("  " + entry.getKey());
            for (Employee e : entry.getValue()) {
                System.out.println("    " + e);
                total += e.getSalary();
            }
            System.out.printf("    total salary: %.2f%n", total);
        }

        System.out.println("\n=== 5. Sorted by id (Comparable) ===");
        List<Employee> byIdOrder = new ArrayList<>(staff);
        Collections.sort(byIdOrder);
        byIdOrder.forEach(e -> System.out.println("  " + e));

        System.out.println("\n=== 6. Top 5 by salary (Comparator chain) ===");
        Comparator<Employee> topSalary = Comparator
                .comparingDouble(Employee::getSalary).reversed()
                .thenComparing(Employee::getName);
        List<Employee> top5 = staff.stream().sorted(topSalary).limit(5).collect(Collectors.toList());
        top5.forEach(e -> System.out.println("  " + e));
        System.out.println("  (Karthik and Vikram tie at 95000, name breaks the tie)");

        System.out.println("\n  By dept, then salary high to low:");
        Comparator<Employee> deptThenSalary = Comparator
                .comparing(Employee::getDept)
                .thenComparing(Employee::getSalary, Comparator.reverseOrder());
        staff.stream().sorted(deptThenSalary).forEach(e -> System.out.println("    " + e));

        System.out.println("\n=== 7. equals/hashCode contract ===");
        Map<BadKey, String> badMap = new HashMap<>();
        badMap.put(new BadKey("EMP-1"), "Arun");
        BadKey lookupBad = new BadKey("EMP-1");
        System.out.println("  BadKey equals?  " + lookupBad.equals(new BadKey("EMP-1")));
        System.out.println("  BadKey lookup:  " + badMap.get(lookupBad) + "   <-- bug, expected Arun");

        Map<GoodKey, String> goodMap = new HashMap<>();
        goodMap.put(new GoodKey("EMP-1"), "Arun");
        System.out.println("  GoodKey lookup: " + goodMap.get(new GoodKey("EMP-1")) + "   <-- fixed with hashCode()");

        Set<BadKey> badSet = new HashSet<>();
        badSet.add(new BadKey("X"));
        badSet.add(new BadKey("X"));
        Set<GoodKey> goodSet = new HashSet<>();
        goodSet.add(new GoodKey("X"));
        goodSet.add(new GoodKey("X"));
        System.out.println("  HashSet size with BadKey:  " + badSet.size() + " (should be 1)");
        System.out.println("  HashSet size with GoodKey: " + goodSet.size());
    }
}
