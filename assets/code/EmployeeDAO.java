/*
Group Members' Names:
 - Nick Gilreath
 - Miguel Perez
 - Matarr Touray
 - Peter St Pierre Jr.
 - Felipe Benavides
Version #: 6
*/

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public List<Employee> searchEmployee(String searchTerm) {

        List<Employee> foundEmployees = new ArrayList<>();

        try {
            Connection conn = DatabaseConnection.getConnection();
            // The query checks if the search term matches the ID, SSN, or part of the first/last name
            String query = "SELECT e.*, d.divisionName, j.jobTitleName " +
                "FROM employee e " +
                "JOIN division d ON e.divisionID = d.divisionID " +
                "JOIN jobTitle j ON e.jobTitleID = j.jobTitleID " +
                "WHERE e.empID = ? OR e.SSN = ? OR CONCAT(e.firstName, ' ', e.lastName) = ?";

            PreparedStatement ps = conn.prepareStatement(query);
        
        // Try to parse as ID, if it fails, set to -1 so it doesn't crash
        try {
            ps.setInt(1, Integer.parseInt(searchTerm));
        } catch (NumberFormatException e) {
            ps.setInt(1, -1); 
        }
        
        // Parameter 2 is for SSN
        ps.setString(2, searchTerm);
        
        // Parameter 3 is for the exact Full Name match
        ps.setString(3, searchTerm); 

        ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Instantiate a new Employee object using the data from the ResultSet
                Employee emp = new Employee(
                    rs.getInt("empID"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("SSN"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getDouble("salary"),
                    rs.getString("divisionName"),
                    rs.getString("jobTitleName")
                );
            // Add the object to your dynamic list
            foundEmployees.add(emp);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return the list of objects back to Main.java
        return foundEmployees;
    }
    
    public void addEmployee(String firstName, String lastName, String ssn,
                            String email, String phone, String hireDate,
                            double salary, int jobTitleID, int divisionID) {
        try {
            Connection conn = DatabaseConnection.getConnection();

            String query = "INSERT INTO employee " +
                    "(firstName, lastName, SSN, email, phone, hireDate, salary, jobTitleID, divisionID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, ssn);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setDate(6, Date.valueOf(hireDate));
            ps.setDouble(7, salary);
            ps.setInt(8, jobTitleID);
            ps.setInt(9, divisionID);

            ps.executeUpdate();
            System.out.println("Employee added successfully.");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(int empID, String newEmail, String newPhone) {
        try {
            Connection conn = DatabaseConnection.getConnection();

            String query = "UPDATE employee SET email = ?, phone = ? WHERE empID = ?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, newEmail);
            ps.setString(2, newPhone);
            ps.setInt(3, empID);

            ps.executeUpdate();
            System.out.println("Employee updated.");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateEmployeeFull(int empID, double salary, int jobTitleID, int divisionID) {
        try {
            Connection conn = DatabaseConnection.getConnection();

            String query = "UPDATE employee SET salary=?, jobTitleID=?, divisionID=? WHERE empID=?";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setDouble(1, salary);
            ps.setInt(2, jobTitleID);
            ps.setInt(3, divisionID);
            ps.setInt(4, empID);

            ps.executeUpdate();
            System.out.println("Employee updated (full).");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void increaseSalary(double min, double max, double percent) {
        try {
            Connection conn = DatabaseConnection.getConnection();

            String query = "UPDATE employee SET salary = salary * ? WHERE salary >= ? AND salary < ?";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setDouble(1, 1 + percent);
            ps.setDouble(2, min);
            ps.setDouble(3, max);

            int rows = ps.executeUpdate();
            System.out.println(rows + " employees updated.");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void totalPayByDivision() {
        try {
            Connection conn = DatabaseConnection.getConnection();

            String query = "SELECT d.divisionName, SUM(p.netPay) totalPay " +
                    "FROM employee e " +
                    "JOIN division d ON e.divisionID = d.divisionID " +
                    "JOIN payStatement p ON e.empID = p.empID " +
                    "GROUP BY d.divisionName";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            System.out.println("\n--- Pay by Division ---");

            while (rs.next()) {
                System.out.println(rs.getString("divisionName") + ": $" + rs.getDouble("totalPay"));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void totalPayByJobTitle() {
        try {
            Connection conn = DatabaseConnection.getConnection();

            String query = "SELECT j.jobTitleName, SUM(p.netPay) totalPay " +
                    "FROM employee e " +
                    "JOIN jobTitle j ON e.jobTitleID = j.jobTitleID " +
                    "JOIN payStatement p ON e.empID = p.empID " +
                    "GROUP BY j.jobTitleName";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            System.out.println("\n--- Pay by Job Title ---");

            while (rs.next()) {
                System.out.println(rs.getString("jobTitleName") + ": $" + rs.getDouble("totalPay"));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPayHistory(int empID) {
        try {
            Connection conn = DatabaseConnection.getConnection();

            String query = "SELECT * FROM payStatement WHERE empID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, empID);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Pay History ---");

            while (rs.next()) {
                System.out.println("Date: " + rs.getDate("payDate") +
                        " | Gross: $" + rs.getDouble("grossPay") +
                        " | Net: $" + rs.getDouble("netPay"));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}