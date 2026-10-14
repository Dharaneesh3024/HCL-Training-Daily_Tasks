package model;

public class Employee implements Comparable<Employee> {

    private final int id;
    private final String name;
    private final String dept;
    private final double salary;

    public Employee(int id, String name, String dept, double salary) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.salary = salary;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDept() { return dept; }
    public double getSalary() { return salary; }

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
