package drill.app;

import drill.draft.StudentServiceDraft;
import drill.service.StudentService;

public class DrillMain {

    public static void main(String[] args) {
        vagueVersusSpecific();
        draftService();
        fixedService();
    }

    static void vagueVersusSpecific() {
        System.out.println("=== 1. Vague vs specific Student ===");
        drill.vague.Student v = new drill.vague.Student(1, "Arun", 8.2);
        v.gpa = -5;
        v.name = null;
        v.courses = null;
        System.out.println("  vague POJO accepted gpa=" + v.gpa + ", name=" + v.name + ", courses=" + v.courses);

        try {
            new drill.specific.Student(1, " ", 8.2);
        } catch (IllegalArgumentException e) {
            System.out.println("  specific POJO rejected blank name: " + e.getMessage());
        }
        try {
            new drill.specific.Student(2, "Divya", 14);
        } catch (IllegalArgumentException e) {
            System.out.println("  specific POJO rejected gpa 14: " + e.getMessage());
        }
        drill.specific.Student s = new drill.specific.Student(3, "Karthik", 7.5);
        s.addCourse("Java");
        try {
            s.getCourses().add("Hack");
        } catch (UnsupportedOperationException e) {
            System.out.println("  specific POJO courses list is read-only");
        }
        System.out.println("  equal ids equal? " + new drill.specific.Student(3, "X", 5).equals(s));
    }

    static void draftService() {
        System.out.println("\n=== 2. AI draft StudentService ===");
        StudentServiceDraft d = new StudentServiceDraft();
        d.add(new drill.vague.Student(1, "Arun", 8.0));
        d.add(new drill.vague.Student(1, "Arun again", 9.0));
        System.out.println("  size after duplicate id: " + d.getAll().size());

        System.out.println("  findByName(new String(\"Arun\")): " + d.findByName(new String("Arun")));

        d.add(null);
        try {
            d.findById(5);
        } catch (NullPointerException e) {
            System.out.println("  null was accepted by add(), then findById crashed with NullPointerException");
        }

        d.addFromLine("2,Divya,abc");
        d.addFromLine("garbage");
        System.out.println("  after two bad lines, size still " + d.getAll().size() + " and no error shown");

        d.remove(new drill.vague.Student(1, "Arun", 8.0));
        System.out.println("  after remove of equal student, size " + d.getAll().size());

        d.getAll().clear();
        System.out.println("  caller cleared internal list, size now " + d.getAll().size());
        System.out.println("  average of empty list: " + d.averageGpa());
    }

    static void fixedService() {
        System.out.println("\n=== 3. Reviewed StudentService ===");
        StudentService svc = new StudentService();
        svc.add(new drill.specific.Student(1, "Arun", 8.0));

        try {
            svc.add(new drill.specific.Student(1, "Arun again", 9.0));
        } catch (IllegalArgumentException e) {
            System.out.println("  duplicate rejected: " + e.getMessage());
        }
        try {
            svc.add(null);
        } catch (IllegalArgumentException e) {
            System.out.println("  null rejected: " + e.getMessage());
        }

        System.out.println("  findByName(new String(\"arun\")): " + svc.findByName(new String("arun")));
        System.out.println("  findById(99): " + svc.findById(99));

        try {
            svc.addFromLine("2,Divya,abc");
        } catch (IllegalArgumentException e) {
            System.out.println("  bad line reported: " + e.getMessage() + " (cause: " + e.getCause().getClass().getSimpleName() + ")");
        }

        try {
            svc.getAll().clear();
        } catch (UnsupportedOperationException e) {
            System.out.println("  getAll() is read-only, internal list safe");
        }
        System.out.println("  remove id 1: " + svc.remove(1) + ", size " + svc.getAll().size());
        System.out.println("  average of empty service: " + svc.averageGpa());
    }
}
