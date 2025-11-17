import service.StudentManager;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        StudentManager sm = new StudentManager();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("===== Capstone Student Menu =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search by Name");
            System.out.println("4. Delete by Name");
            System.out.println("5. Update by Roll Number");
            System.out.println("6. Sort by Marks");
            System.out.println("7. Save and Exit");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt();

            switch (ch) {
                case 1 -> sm.addStudent();
                case 2 -> sm.viewAllStudents();
                case 3 -> sm.searchStudent();
                case 4 -> sm.deleteStudent();
                case 5 -> sm.updateStudent();
                case 6 -> sm.sortByMarks();
                case 7 -> {
                    sm.saveToFile();
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
