package service;

import model.Employee;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeDirectory {

    private final List<Employee> staff = new ArrayList<>();
    private final Map<Integer, Employee> byId = new HashMap<>();

    public void add(Employee e) {
        staff.add(e);
        byId.put(e.getId(), e);
    }

    public Employee findById(int id) {
        return byId.get(id);
    }

    public boolean exists(int id) {
        return byId.containsKey(id);
    }

    public Map<String, Integer> headcountByDept() {
        Map<String, Integer> counts = new HashMap<>();
        for (Employee e : staff) {
            counts.merge(e.getDept(), 1, Integer::sum);
        }
        return counts;
    }

    public Map<String, Integer> headcountInsertionOrder() {
        Map<String, Integer> counts = new LinkedHashMap<>();
        for (Employee e : staff) {
            counts.merge(e.getDept(), 1, Integer::sum);
        }
        return counts;
    }

    public Map<String, Integer> headcountSorted() {
        Map<String, Integer> counts = new TreeMap<>();
        for (Employee e : staff) {
            counts.merge(e.getDept(), 1, Integer::sum);
        }
        return counts;
    }

    public Map<String, List<Employee>> departmentReport() {
        Map<String, List<Employee>> report = new TreeMap<>();
        for (Employee e : staff) {
            report.computeIfAbsent(e.getDept(), k -> new ArrayList<>()).add(e);
        }
        for (List<Employee> list : report.values()) {
            Collections.sort(list);
        }
        return report;
    }

    public List<Employee> sortedById() {
        List<Employee> copy = new ArrayList<>(staff);
        Collections.sort(copy);
        return copy;
    }

    public List<Employee> topBySalary(int n) {
        Comparator<Employee> bySalary = Comparator
                .comparingDouble(Employee::getSalary).reversed()
                .thenComparing(Employee::getName);
        return staff.stream().sorted(bySalary).limit(n).collect(Collectors.toList());
    }

    public List<Employee> sortedByDeptThenSalary() {
        Comparator<Employee> byDept = Comparator
                .comparing(Employee::getDept)
                .thenComparing(Employee::getSalary, Comparator.reverseOrder());
        return staff.stream().sorted(byDept).collect(Collectors.toList());
    }
}
