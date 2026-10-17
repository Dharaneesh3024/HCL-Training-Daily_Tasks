package drill.draft;

import drill.vague.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentServiceDraft {

    private List<Student> students = new ArrayList<>();

    public void add(Student s) {
        students.add(s);
    }

    public List<Student> getAll() {
        return students;
    }

    public Student findById(int id) {
        for (Student s : students) {
            if (s.id == id) {
                return s;
            }
        }
        return null;
    }

    public Student findByName(String name) {
        for (Student s : students) {
            if (s.name == name) {
                return s;
            }
        }
        return null;
    }

    public double averageGpa() {
        double total = 0;
        for (Student s : students) {
            total += s.gpa;
        }
        return total / students.size();
    }

    public void addFromLine(String line) {
        try {
            String[] p = line.split(",");
            add(new Student(Integer.parseInt(p[0]), p[1], Double.parseDouble(p[2])));
        } catch (Exception e) {
        }
    }

    public void remove(Student s) {
        students.remove(s);
    }
}
