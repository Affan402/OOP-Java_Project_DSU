import java.util.*;
// Base abstract class demonstrating abstraction and encapsulation
abstract class Person {
    private String name;
    private String email;
    private int id;

    public Person(String name, String email, int id) {
        setName(name);
        setEmail(email);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public final void setName(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Name cannot be empty");
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public final void setEmail(String email) {
        if (!email.contains("@"))
            throw new IllegalArgumentException("Invalid email format");
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public abstract void displayDetails();
}


class Student extends Person {
    private List<Course> enrolledCourses = new ArrayList<>();

    public Student(String name, String email, int id) {
        super(name, email, id);
    }

    public void enrollInCourse(Course course) {
        if (enrolledCourses.contains(course)) {
            System.out.println("Already enrolled in: " + course.getCourseCode());
        } else if (course.registerStudent(this)) {
            enrolledCourses.add(course);
            System.out.println("Enrolled in course: " + course.getCourseCode());
        }
    }

    public void dropCourse(Course course) {
        if (enrolledCourses.remove(course)) {
            course.removeStudent(this);
            System.out.println("Dropped course: " + course.getCourseCode());
        }
    }

    @Override
    public void displayDetails() {
        System.out.println("Student: " + getName() + " | Email: " + getEmail() + " | ID: " + getId());
        System.out.println("Enrolled Courses:");
        for (Course c : enrolledCourses) {
            System.out.println("- " + c.getCourseCode());
        }
    }
}

class Instructor extends Person {
    private List<Course> assignedCourses = new ArrayList<>();

    public Instructor(String name, String email, int id) {
        super(name, email, id);
    }

    public void assignCourse(Course course) {
        if (!assignedCourses.contains(course)) {
            assignedCourses.add(course);
            course.setInstructor(this);
            System.out.println("Assigned to course: " + course.getCourseCode());
        }
    }

    @Override
    public void displayDetails() {
        System.out.println("Instructor: " + getName() + " | Email: " + getEmail() + " | ID: " + getId());
        System.out.println("Assigned Courses:");
        for (Course c : assignedCourses) {
            System.out.println("- " + c.getCourseCode());
        }
    }
}

class Course {
    private String courseCode;
    private String courseName;
    private int maxStudents;
    private List<Student> registeredStudents = new ArrayList<>();
    private Instructor instructor;

    public Course(String courseCode, String courseName, int maxStudents) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.maxStudents = maxStudents;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public boolean registerStudent(Student student) {
        if (registeredStudents.size() >= maxStudents) {
            System.out.println("Course full: " + courseCode);
            return false;
        }
        registeredStudents.add(student);
        return true;
    }

    public void removeStudent(Student student) {
        registeredStudents.remove(student);
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public void displayCourseInfo() {
        String instructorName = "TBA";
        if (instructor != null) {
            instructorName = instructor.getName();
        }
        System.out.println("Course: " + courseCode + " - " + courseName);
        System.out.println("Instructor: " + instructorName);
        System.out.println("Enrolled Students:");
        for (Student s : registeredStudents) {
            System.out.println("- " + s.getName());
        }
    }
}

class RegistrationSystem {
    private List<Student> students = new ArrayList<>();
    private List<Instructor> instructors = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addInstructor(Instructor instructor) {
        instructors.add(instructor);
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void enrollStudentInCourse(int studentId, String courseCode) {
        Student foundStudent = null;
        Course foundCourse = null;

        for (Student s : students) {
            if (s.getId() == studentId) {
                foundStudent = s;
                break;
            }
        }

        for (Course c : courses) {
            if (c.getCourseCode().equals(courseCode)) {
                foundCourse = c;
                break;
            }
        }

        if (foundStudent != null && foundCourse != null) {
            foundStudent.enrollInCourse(foundCourse);
        }
    }

    public void displayAll() {
        System.out.println("\n--- Students ---");
        for (Student s : students) {
            s.displayDetails();
        }
        System.out.println("\n--- Instructors ---");
        for (Instructor i : instructors) {
            i.displayDetails();
        }
        System.out.println("\n--- Courses ---");
        for (Course c : courses) {
            c.displayCourseInfo();
        }
    }

    public static void main(String[] args) {
        RegistrationSystem system = new RegistrationSystem();

        Student s1 = new Student("Ali", "ali@email.com", 1);
        Student s2 = new Student("Zara", "zara@email.com", 2);
        Instructor i1 = new Instructor("Dr. Khan", "khan@email.com", 101);

        Course c1 = new Course("CS101", "Intro to Programming", 2);
        Course c2 = new Course("CS102", "OOP in Java", 2);

        system.addStudent(s1);
        system.addStudent(s2);
        system.addInstructor(i1);
        system.addCourse(c1);
        system.addCourse(c2);

        i1.assignCourse(c1);
        i1.assignCourse(c2);

        system.enrollStudentInCourse(1, "CS101");
        system.enrollStudentInCourse(2, "CS101");
        system.enrollStudentInCourse(1, "CS102");

        system.displayAll();
    }
}
