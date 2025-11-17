package service;

import model.Student;
import util.Loader;
import util.StudentNotFoundException;

import java.io.*;
import java.util.*;

public class StudentManager implements RecordActions {

    private Map<Integer, Student> studentMap = new HashMap<>();
    private List<Student> studentList = new ArrayList<>();
    private final String FILE = "students.txt";

    public StudentManager() {
        loadFromFile();
    }

    // 🔹 Simulate loading using thread
    private void simulateLoading() {
        Thread t = new Thread(new Loader());
        t.start();
        try {
            t.join();
        } catch (Exception e) {}
    }

    // 🔹 Add student
    @Override
    public void addStudent() {
        Scanner sc = new Scanner(System.in);
        Student s = new Student();
        s.inputDetails();

        if (studentMap.containsKey(s.getRollNo())) {
            System.out.println("❌ Duplicate roll number! Student already exists.");
            return;
        }

        studentMap.put(s.getRollNo(), s);
        studentList.add(s);

        simulateLoading();
        System.out.println("✅ Student added successfully!");
    }

    // 🔹 View all
    @Override
    public void viewAllStudents() {
        if (studentList.isEmpty()) {
            System.out.println("No records to display.");
            return;
        }

        System.out.println("===== Student Records =====");
        for (Student s : studentList) {
            s.displayDetails();
            System.out.println("---------------------------");
        }
    }

    // 🔹 Search student by name
    @Override
    public void searchStudent() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name to search: ");
        String name = sc.nextLine().trim();

        for (Student s : studentList) {
            if (s.getName().equalsIgnoreCase(name)) {
                System.out.println("Student Found:");
                s.displayDetails();
                return;
            }
        }

        System.out.println("❌ Student not found.");
    }

    // 🔹 Delete by name
    @Override
    public void deleteStudent() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name to delete: ");
        String name = sc.nextLine().trim();

        Iterator<Student> it = studentList.iterator();
        while (it.hasNext()) {
            Student s = it.next();
            if (s.getName().equalsIgnoreCase(name)) {
                it.remove();
                studentMap.remove(s.getRollNo());
                System.out.println("✅ Student deleted.");
                return;
            }
        }

        System.out.println("❌ Student not found.");
    }

    // 🔹 Update by roll number
    @Override
    public void updateStudent() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter roll number to update: ");
        int roll = sc.nextInt();

        if (!studentMap.containsKey(roll)) {
            System.out.println("❌ Student not found.");
            return;
        }

        Student s = studentMap.get(roll);

        sc.nextLine();

        System.out.print("Enter new email: ");
        String email = sc.nextLine();

        System.out.print("Enter new course: ");
        String course = sc.nextLine();

        System.out.print("Enter new marks: ");
        double marks = sc.nextDouble();

        s = new Student(roll, s.getName(), email, course, marks);
        studentMap.put(roll, s);

        // Update in list
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getRollNo() == roll) {
                studentList.set(i, s);
            }
        }

        System.out.println("✅ Student updated.");
    }

    // 🔹 Sort by marks
    @Override
    public void sortByMarks() {
        if (studentList.isEmpty()) {
            System.out.println("No records to sort.");
            return;
        }

        studentList.sort((a, b) -> Double.compare(b.getMarks(), a.getMarks()));

        System.out.println("===== Sorted by Marks (DESC) =====");
        for (Student s : studentList) {
            s.displayDetails();
            System.out.println("---------------------");
        }
    }

    // 🔹 Load from file
    private void loadFromFile() {
        File file = new File(FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                int roll = Integer.parseInt(parts[0]);
                String name = parts[1];
                String email = parts[2];
                String course = parts[3];
                double marks = Double.parseDouble(parts[4]);

                Student s = new Student(roll, name, email, course, marks);
                studentMap.put(roll, s);
                studentList.add(s);
            }
        } catch (Exception e) {
            System.out.println("Error loading file.");
        }
    }

    // 🔹 Save to file
    public void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE))) {
            for (Student s : studentList) {
                bw.write(s.toFileFormat());
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error saving data.");
        }

        System.out.println("💾 Data saved to file!");
    }
}
