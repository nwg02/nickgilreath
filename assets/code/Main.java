/*
Group Members' Names:
 - Nick Gilreath
 - Miguel Perez
 - Matarr Touray
 - Peter St Pierre Jr.
 - Felipe Benavides
Version #: 6
*/

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        EmployeeDAO dao = new EmployeeDAO();

        int choice = 0;

        while (choice != 9) {

            System.out.println("\n=== Employee Management System ===");
            System.out.println("1. Search Employee");
            System.out.println("2. Add Employee");
            System.out.println("3. Update Email/Phone");
            System.out.println("4. Increase Salary");
            System.out.println("5. Report by Division");
            System.out.println("6. Pay History");
            System.out.println("7. Full Update (Salary/Job/Division)");
            System.out.println("8. Report by Job Title");
            System.out.println("9. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Employee ID, SSN, or Full Name: ");
                    String searchTerm = sc.nextLine();
                    
                    // Call the method and store the returned list
                    java.util.List<Employee> results = dao.searchEmployee(searchTerm);
                    
                    if (results.isEmpty()) {
                        System.out.println("Employee not found.");
                    } else {
                        // Loop through the dynamic data structure and print each employee
                        for (Employee e : results) {
                            System.out.println(e.toString());
                        }
                    }
                    break;
                    

                case 2:
                    System.out.print("First Name: ");
                    String f = sc.nextLine();
                    System.out.print("Last Name: ");
                    String l = sc.nextLine();
                    System.out.print("SSN: ");
                    String ssn = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Phone: ");
                    String phone = sc.nextLine();
                    System.out.print("Hire Date (YYYY-MM-DD): ");
                    String date = sc.nextLine();
                    System.out.print("Salary: ");
                    double sal = sc.nextDouble();
                    System.out.print("JobTitleID: ");
                    int job = sc.nextInt();
                    System.out.print("DivisionID: ");
                    int div = sc.nextInt();
                    dao.addEmployee(f, l, ssn, email, phone, date, sal, job, div);
                    break;

                case 3:
                    System.out.print("Employee ID: ");
                    int eid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("New Email: ");
                    String newEmail = sc.nextLine();
                    System.out.print("New Phone: ");
                    String newPhone = sc.nextLine();
                    dao.updateEmployee(eid, newEmail, newPhone);
                    break;

                case 4:
                    System.out.print("Min Salary: ");
                    double min = sc.nextDouble();
                    System.out.print("Max Salary: ");
                    double max = sc.nextDouble();
                    System.out.print("Percent (0.032 = 3.2%): ");
                    double p = sc.nextDouble();
                    dao.increaseSalary(min, max, p);
                    break;

                case 5:
                    dao.totalPayByDivision();
                    break;

                case 6:
                    System.out.print("Enter Employee ID: ");
                    int payEmpId = sc.nextInt();
                    dao.getPayHistory(payEmpId);
                    break;

                case 7:
                    System.out.print("Employee ID: ");
                    int fullEid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("New Salary: ");
                    double newSal = sc.nextDouble();
                    System.out.print("New JobTitleID: ");
                    int newJob = sc.nextInt();
                    System.out.print("New DivisionID: ");
                    int newDiv = sc.nextInt();
                    dao.updateEmployeeFull(fullEid, newSal, newJob, newDiv);
                    break;

                case 8:
                    dao.totalPayByJobTitle();
                    break;

                case 9:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }

        sc.close();
    }
}