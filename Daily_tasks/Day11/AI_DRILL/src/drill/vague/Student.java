package drill.vague;

import java.util.ArrayList;
import java.util.List;

public class Student {

    public int id;
    public String name;
    public double gpa;
    public List<String> courses = new ArrayList<>();

    public Student(int id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }
}
