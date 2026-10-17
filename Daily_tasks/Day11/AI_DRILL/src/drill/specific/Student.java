package drill.specific;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Student {

    private final int id;
    private String name;
    private double gpa;
    private final List<String> courses = new ArrayList<>();

    public Student(int id, String name, double gpa) {
        if (id <= 0) {
            throw new IllegalArgumentException("id must be positive");
        }
        this.id = id;
        setName(name);
        setGpa(gpa);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getGpa() { return gpa; }

    public List<String> getCourses() {
        return Collections.unmodifiableList(courses);
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        this.name = name.trim();
    }

    public void setGpa(double gpa) {
        if (gpa < 0 || gpa > 10) {
            throw new IllegalArgumentException("gpa must be between 0 and 10");
        }
        this.gpa = gpa;
    }

    public void addCourse(String course) {
        if (course == null || course.isBlank()) {
            throw new IllegalArgumentException("course must not be blank");
        }
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        return id == ((Student) o).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name=" + name + ", gpa=" + gpa + "}";
    }
}
