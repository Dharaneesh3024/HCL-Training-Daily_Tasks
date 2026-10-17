package drill.service;

import drill.specific.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;

public class StudentService {

    private final Map<Integer, Student> students = new LinkedHashMap<>();

    public void add(Student s) {
        if (s == null) {
            throw new IllegalArgumentException("student must not be null");
        }
        if (students.containsKey(s.getId())) {
            throw new IllegalArgumentException("duplicate student id " + s.getId());
        }
        students.put(s.getId(), s);
    }

    public List<Student> getAll() {
        return Collections.unmodifiableList(new ArrayList<>(students.values()));
    }

    public Optional<Student> findById(int id) {
        return Optional.ofNullable(students.get(id));
    }

    public List<Student> findByName(String name) {
        List<Student> matches = new ArrayList<>();
        if (name == null) {
            return matches;
        }
        for (Student s : students.values()) {
            if (s.getName().equalsIgnoreCase(name.trim())) {
                matches.add(s);
            }
        }
        return matches;
    }

    public OptionalDouble averageGpa() {
        return students.values().stream().mapToDouble(Student::getGpa).average();
    }

    public void addFromLine(String line) {
        if (line == null) {
            throw new IllegalArgumentException("line must not be null");
        }
        String[] p = line.split(",");
        if (p.length != 3) {
            throw new IllegalArgumentException("expected id,name,gpa but got: " + line);
        }
        try {
            add(new Student(Integer.parseInt(p[0].trim()), p[1], Double.parseDouble(p[2].trim())));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("bad number in line: " + line, e);
        }
    }

    public boolean remove(int id) {
        return students.remove(id) != null;
    }
}
