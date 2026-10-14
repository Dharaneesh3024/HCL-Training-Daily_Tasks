package app;

import model.BadKey;
import model.Employee;
import model.GoodKey;
import service.EmployeeDirectory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        EmployeeDirectory dir = new EmployeeDirectory();
        dir.add(new Employee(105, "Meena", "CSE", 82000));
        dir.add(new Employee(101, "Arun", "IT", 56000));
        dir.add(new Employee(108, "Divya", "ECE", 61000));
        dir.add(new Employee(103, "Karthik", "CSE", 95000));
        dir.add(new Employee(110, "Ravi", "IT", 72000));
        dir.add(new Employee(102, "Sneha", "HR", 48000));
        dir.add(new Employee(107, "Vikram", "CSE", 95000));
        dir.add(new Employee(104, "Priya", "ECE", 67000));
        dir.add(new Employee(109, "Naveen", "IT", 59000));

        System.out.println("=== 1. HashMap lookup by id ===");
        System.out.println("  id 103 -> " + dir.findById(103));
        System.out.println("  id 999 -> " + dir.findById(999));
        System.out.println("  has 108? " + dir.exists(108));

        System.out.println("\n=== 2. Headcount per department (merge) ===");
        System.out.println("  " + dir.headcountByDept());

        System.out.println("\n=== 3. HashMap vs LinkedHashMap vs TreeMap ===");
        System.out.println("  HashMap:       " + dir.headcountByDept());
        System.out.println("  LinkedHashMap: " + dir.headcountInsertionOrder());
        System.out.println("  TreeMap:       " + dir.headcountSorted());

        System.out.println("\n=== 4. Department report (TreeMap) ===");
        for (Map.Entry<String, List<Employee>> entry : dir.departmentReport().entrySet()) {
            double total = 0;
            System.out.println("  " + entry.getKey());
            for (Employee e : entry.getValue()) {
                System.out.println("    " + e);
                total += e.getSalary();
            }
            System.out.printf("    total salary: %.2f%n", total);
        }

        System.out.println("\n=== 5. Sorted by id (Comparable) ===");
        for (Employee e : dir.sortedById()) {
            System.out.println("  " + e);
        }

        System.out.println("\n=== 6. Top 5 by salary (Comparator chain) ===");
        for (Employee e : dir.topBySalary(5)) {
            System.out.println("  " + e);
        }

        System.out.println("\n  By department, then salary high to low:");
        for (Employee e : dir.sortedByDeptThenSalary()) {
            System.out.println("    " + e);
        }

        System.out.println("\n=== 7. equals/hashCode contract ===");
        Map<BadKey, String> badMap = new HashMap<>();
        badMap.put(new BadKey("EMP-1"), "Arun");
        System.out.println("  BadKey lookup:  " + badMap.get(new BadKey("EMP-1")));

        Map<GoodKey, String> goodMap = new HashMap<>();
        goodMap.put(new GoodKey("EMP-1"), "Arun");
        System.out.println("  GoodKey lookup: " + goodMap.get(new GoodKey("EMP-1")));

        Set<BadKey> badSet = new HashSet<>();
        badSet.add(new BadKey("X"));
        badSet.add(new BadKey("X"));
        Set<GoodKey> goodSet = new HashSet<>();
        goodSet.add(new GoodKey("X"));
        goodSet.add(new GoodKey("X"));
        System.out.println("  HashSet size with BadKey:  " + badSet.size());
        System.out.println("  HashSet size with GoodKey: " + goodSet.size());
    }
}
