import java.util.ArrayList;
import java.util.Scanner;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }
}

class Student {
    private int studentID;
    private String name;
    private ArrayList<Course> registeredCourses;

    public Student(int studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public int getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(Course course) {
        registeredCourses.add(course);
    }

    public void removeCourse(Course course) {
        registeredCourses.remove(course);
    }
}

class CourseRegistrationSystem {
    private ArrayList<Course> courseDatabase;
    private ArrayList<Student> studentDatabase;
    private Scanner scanner;

    public CourseRegistrationSystem() {
        this.courseDatabase = new ArrayList<>();
        this.studentDatabase = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void displayCourseListing() {
        System.out.println("Course Listing:");
        for (Course course : courseDatabase) {
            System.out.println("Course Code: " + course.getCourseCode());
            System.out.println("Title: " + course.getTitle());
            System.out.println("Description: " + course.getDescription());
            System.out.println("Capacity: " + course.getCapacity());
            System.out.println("Schedule: " + course.getSchedule());
            System.out.println("Available Slots: " + (course.getCapacity() - getRegisteredStudentsCount(course)));
            System.out.println();
        }
    }

    public void displayStudentRegistration(int studentID) {
        System.out.println("Student Registration for ID: " + studentID);
        Student student = findStudent(studentID);
        if (student != null) {
            System.out.println("Student Name: " + student.getName());
            System.out.println("Registered Courses:");
            for (Course course : student.getRegisteredCourses()) {
                System.out.println(course.getCourseCode() + " - " + course.getTitle());
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    public void registerStudent() {
        System.out.print("Enter student ID: ");
        int studentID = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter student name: ");
        String studentName = scanner.nextLine();

        Student newStudent = new Student(studentID, studentName);
        studentDatabase.add(newStudent);

        System.out.println("Student registration successful!");
    }

    public void registerCourse(int studentID) {
        displayCourseListing();
        System.out.print("Enter the course code to register: ");
        String courseCode = scanner.next();

        Course selectedCourse = findCourse(courseCode);
        Student student = findStudent(studentID);

        if (selectedCourse != null && student != null) {
            if (getRegisteredStudentsCount(selectedCourse) < selectedCourse.getCapacity()) {
                student.registerCourse(selectedCourse);
                System.out.println("Course registration successful!");
            } else {
                System.out.println("Course registration failed. The course is full.");
            }
        } else {
            System.out.println("Invalid course or student ID.");
        }
    }

    public void removeCourse(int studentID) {
        displayStudentRegistration(studentID);
        System.out.print("Enter the course code to remove: ");
        String courseCode = scanner.next();

        Course selectedCourse = findCourse(courseCode);
        Student student = findStudent(studentID);

        if (selectedCourse != null && student != null) {
            student.removeCourse(selectedCourse);
            System.out.println("Course removal successful!");
        } else {
            System.out.println("Invalid course or student ID.");
        }
    }

    private int getRegisteredStudentsCount(Course course) {
        int count = 0;
        for (Student student : studentDatabase) {
            if (student.getRegisteredCourses().contains(course)) {
                count++;
            }
        }
        return count;
    }

    private Course findCourse(String courseCode) {
        for (Course course : courseDatabase) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        return null;
    }

    private Student findStudent(int studentID) {
        for (Student student : studentDatabase) {
            if (student.getStudentID() == studentID) {
                return student;
            }
        }
        return null;
    }
}

public class CourseRegistrationSystemApp {
    public static void main(String[] args) {
        CourseRegistrationSystem registrationSystem = new CourseRegistrationSystem();
        
       
        registrationSystem.courseDatabase.add(new Course("CSCI101", "Introduction to Programming", "Fundamentals of programming", 50, "Mon/Wed 10:00 AM"));
        registrationSystem.courseDatabase.add(new Course("MATH201", "Calculus I", "Basic calculus concepts", 40, "Tue/Thu 2:00 PM"));
        registrationSystem.courseDatabase.add(new Course("ENG102", "English Composition", "Developing writing skills", 30, "Mon/Wed/Fri 1:00 PM"));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Course Registration System");
            System.out.println("1. Display Course Listing");
            System.out.println("2. Register Student");
            System.out.println("3. Register Course");
            System.out.println("4. Remove Course");
            System.out.println("5. Display Student Registration");
            System.out.println("6. Exit");
            System.out.print("Enter your choice (1-6): ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    registrationSystem.displayCourseListing();
                    break;
                case 2:
                    registrationSystem.registerStudent();
                    break;
                case 3:
                    System.out.print("Enter student ID: ");
                    int studentID = scanner.nextInt();
                    registrationSystem.registerCourse(studentID);
                    break;
                case 4:
                    System.out.print("Enter student ID: ");
                    int studentIDToRemove = scanner.nextInt();
                    registrationSystem.removeCourse(studentIDToRemove);
                    break;
                case 5:
                    System.out.print("Enter student ID: ");
                    int studentIDToDisplay = scanner.nextInt();
                    registrationSystem.displayStudentRegistration(studentIDToDisplay);
                    break;
                case 6:
                    System.out.println("Exiting the Course Registration System. Thank you!");
                    System.exit(0);
                    break;
                default
