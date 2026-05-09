/*
Group Members' Names:
 - Nick Gilreath
 - Miguel Perez
 - Matarr Touray
 - Peter St Pierre Jr.
 - Felipe Benavides
Version #: 5
*/

public class Employee {
    private int empID;
    private String firstName;
    private String lastName;
    private String ssn;
    private String email;
    private String phone;
    private double salary;
    private String divisionName;
    private String jobTitleName;

    // Constructor
    public Employee(int empID, String firstName, String lastName, String ssn, String email, String phone, double salary, String divisionName, String jobTitleName) {
        this.empID = empID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
        this.divisionName = divisionName;
        this.jobTitleName = jobTitleName;
    }

    // A customized toString() method to easily print the employee's details
    @Override
    public String toString() {
        return "\n--- Employee Info ---\n" +
               "ID: " + empID + "\n" +
               "Name: " + firstName + " " + lastName + "\n" +
               "SSN: " + ssn + "\n" +
               "Email: " + email + "\n" +
               "Phone: " + phone + "\n" +
               "Salary: $" + salary + "\n" +
               "Division: " + divisionName + "\n" +
               "Job Title: " + jobTitleName;
    }
}