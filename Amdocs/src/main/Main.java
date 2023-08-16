package main;


import java.awt.*;
import java.sql.*;
import java.util.Scanner;

public class Main {
   String url = "jdbc:mysql://localhost:3306/blood_management";
        String user = "root";
        String password = "aastha2802";

    public static void main(String args[]) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Amdocs Blood Management System");
        System.out.println("===========================================");
        int choice = 3;

        do {
            System.out.println("1: Admin");
            System.out.println("2: Exit");
            System.out.println("===========================================");
            System.out.print("Enter Your Choice : ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                // Admin
                case 1: {
                    if (login(scanner)) {
                        adminDashBoard(scanner);
                    } else {
                        break;
                    }
                    // admin(scanner);
                    break;
                }
                
            }
        } while (choice != 2);

    }

    // Admin Login
    public static boolean login(Scanner scanner) {
        String url = "jdbc:mysql://localhost:3306/blood_management";
        String user = "root";
        String password = "aastha2802";
        System.out.println("");
        System.out.println("Enter your login id and password");
        System.out.println("===========================================");
        System.out.print("Id: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Pass: ");
        String pass = scanner.nextLine();

        try {
            // Establish the database connection
            Connection connection = DriverManager.getConnection(url, user, password);

            // Prepare the SQL insert statement
            String checkQuery = "SELECT * FROM admin WHERE id = ? and password = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(checkQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, pass);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("Please check your id or pass");
                System.out.println("");
                return false;
            } else {
                System.out.println("User Details");
                System.out.println(resultSet.getInt("id") + " " + resultSet.getString("adminName") + " " +
                        resultSet.getString("password"));
                return true;
            }
            // Close the database connection and resources
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // admin dashboard.
    public static void adminDashBoard(Scanner scanner) {
        int choice = 4;
        do {
            System.out.println(" ");
            System.out.println("Welcome to Admin Dashboard");
            System.out.println("1: Donor");
            System.out.println("2: Receiver");
            System.out.println("3: Report");
            System.out.println("4: Exit");
            System.out.println("========================================");
            System.out.print("Enter your Choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    donorDashBoard(scanner);
                    break;
                }
                case 2: {
                    receiverDashBoard(scanner);
                    break;
                }
                case 3: {
                    System.out.println("Welcome to Report Portal");
                    int choice1=3;
                    do{
                        System.out.println("");
                        System.out.println("1: Donor Report ");
                        System.out.println("2: Receiver Report");
                        System.out.println("3: Exit");
                        System.out.println("===================");
                        System.out.print("Enter Your Choice: ");
                        choice1=Integer.parseInt(scanner.nextLine());
                        switch (choice1){
                            case 1:{
                                generateDonorReport(scanner);
                                break;
                            } case 2:{
                                generateReceiverReport(scanner);
                                break;
                            }
                        }
                    }while (choice1!=3);


                    break;
                }
            }
        } while (choice != 4);
    }
    // Donor DashBoard
    public static void donorDashBoard(Scanner scanner) {
    final String url = "jdbc:mysql://localhost:3306/blood_management";
      final  String user = "root";
       final String password = "aastha2802";
        int choice = 4;
        do {
            System.out.println(" ");
            System.out.println("Welcome to Donor Dashboard");
            System.out.println("1: Add New Donor Details");
            System.out.println("2: Update Donor Details");
            System.out.println("3: Delete Donor Details");
            System.out.println("4: Exit");
            System.out.println("");
            System.out.print("Enter Your Choice : ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    System.out.println("Welcome to Donor Dashboard");
                    try {
                        // Establish the database connection

                        System.out.println("Enter id, name,bloodGroup,contactDetails");
                            System.out.print("Enter Donor id: ");
                        int donorid = Integer.parseInt(scanner.nextLine());
                        
                        System.out.print("Enter Donor Name: ");
                        System.out.println("");
                        String donorName = scanner.nextLine();
                        System.out.print("Enter Donor Blood Group: ");
                        String bloodgroup = scanner.nextLine();
                        System.out.print("Enter Donor Contact Details: ");
                        String contactno = scanner.nextLine();
                    

                        Connection connection = DriverManager.getConnection(url, user, password);

                        // Prepare the SQL insert statement
                        String insertQuery = "INSERT INTO Donor (donorid,donorName, bloodgroup, contactno) VALUES ( ?,?,?,?)";
                        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                        preparedStatement.setInt(1,donorid);
                        preparedStatement.setString(2, donorName);
                        preparedStatement.setString(3, bloodgroup);
                        preparedStatement.setString(4, contactno);
                       

                        // Execute the insert statement
                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Donor registered successfully!");
                            System.out.println("==================");
                        } else {
                            System.out.println("Failed to register customer.");
                        }
                        preparedStatement.close();
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 2: {
                    System.out.println("");
                    System.out.println("Welcome to Donor Update Page");
                    // System.out.println("Enter the Donor ID");
                    
                    try  {
                        // Step 2: Get Advocate ID input from user
                        Connection connection = DriverManager.getConnection(url, user, password);
                        
                        System.out.print("Enter Donor ID to modify: ");
                        int donorId=Integer.parseInt(scanner.nextLine());

                        // Step 3: Check if customer ID exists in the database
                        String checkQuery = "SELECT * FROM Donor WHERE donorid = ?";
                        try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                            checkStatement.setInt(1, donorId);
                            ResultSet resultSet = checkStatement.executeQuery();

                            if (!resultSet.next()) {
                                System.out.println("Donor ID does not exist in the database.");
                                break;
                            }
                        }

                        //  Get the field to modify from the user
                        System.out.println("Select the field to modify:");
                        System.out.println("1. Donorname");
                        System.out.println("2. Bloodgroup");
                        System.out.println("3. address");


                        int fieldChoice = Integer.parseInt(scanner.nextLine());
                        // Consume the newline character

                        //  Get the new value for the selected field
                        String newValue = "";
                        switch (fieldChoice) {
                            case 1:
                                System.out.print("Enter new name: ");
                                newValue = scanner.nextLine();
                                break;
                            case 2:
                                System.out.print("Enter Bloodgroup: ");
                                newValue = scanner.nextLine();
                                break;
                            case 3:
                                System.out.print("Enter new Address: ");
                                newValue = scanner.nextLine();
                                break;
                            default:
                                System.out.println("Invalid field choice.");
                                return;
                        }

                        //  Execute the update query
                        String updateQuery = "";
                        switch (fieldChoice) {
                            case 1:
                                updateQuery = "UPDATE Donor SET donorName= ? WHERE donorid = ?";
                                break;
                            case 2:
                                updateQuery = "UPDATE Donor SET bloodgroup = ? WHERE donorid = ?";
                                break;
                            case 3:{
                                updateQuery = "UPDATE Donor SET contactno = ? WHERE donorid = ?";
                            }

                        }
                        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                            statement.setString(1, newValue);
                            statement.setInt(2, donorId);
                            //Execute the query
                            int rowsAffected = statement.executeUpdate();
                            System.out.println(rowsAffected + " row(s) updated.");
                            System.out.println("");
                        }



                    } catch (SQLException e) {
                        e.printStackTrace();
                    }





                    
                    break;
                }
                case 3: {
                    try {
                        System.out.println("");
                        System.out.println("Welcome to Donor Deletion Page");
                        // Establish the database connection
                        System.out.print("Enter the Donor id: ");
                        Connection connection = DriverManager.getConnection(url, user, password);
            
                        int donorId = Integer.parseInt(scanner.nextLine());
                        String checkQuery = "SELECT * FROM Donor WHERE donorid = ?";
                        String delQueryString = "DELETE FROM Donor WHERE donorid = ?";
                        PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
                        checkStatement.setInt(1, donorId);
                        ResultSet resultSet = checkStatement.executeQuery();
                        int flag = 0;
                        if (!resultSet.next()) {
                            System.out.println("Donor ID does not exist in the database.");
                            break;
                        } else {
                            int id1 = resultSet.getInt("donorid");
                            String donor = resultSet.getString("donorname");
                            String bloodGroup = resultSet.getString("bloodgroup");
                            String contactDetails = resultSet.getString("contactno");
                            //
                            System.out.println("ID: " + id1);
                            System.out.println("Name: " + donor);
                            System.out.println("Adress: " + bloodGroup);
                            System.out.println("Number: " + contactDetails);
            
                            System.out.println("======================");
                        }
                        int delete=0;
                        System.out.println("Do You Want to Delete the Donor ");
                        System.out.println("Press 1 for conformation");
                        delete=Integer.parseInt(scanner.nextLine());


                        if(delete!=1) break;
                        System.out.println("Deleting statement");
                        String sql = "DELETE FROM Donor WHERE donorid = ?";
                        PreparedStatement statement = connection.prepareStatement(sql);
            
                        // Set the ID parameter
                        // Replace with your custom ID
                        statement.setInt(1, donorId);
            
                        // Execute the SQL query
                        int rowsAffected = statement.executeUpdate();
            
                        // Check the number of rows affected
                        if (rowsAffected > 0) {
                            System.out.println("Data deleted successfully.");
                        } else {
                            System.out.println("No data found to delete.");
                        }
                        System.out.println("");
                        // Close the statement and connection
                        statement.close();
                    }
            
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        } while (choice != 4);
    }
    // Receiver DashBoard
    public static void receiverDashBoard(Scanner scanner) {
    final String url = "jdbc:mysql://localhost:3306/blood_management";
     final   String user = "root";
      final  String password = "aastha2802";
        int choice = 4;
        do {
            System.out.println("");
            System.out.println("Welcome to Receiver Dashboard");
            System.out.println("1: Add New Receiver Details");
            System.out.println("2: Update Receiver Details");
            System.out.println("3: Delete Receiver Details");
            System.out.println("4: Exit");
            System.out.println("===============================");
            System.out.print("Enter your Choice");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    System.out.println("");
                    System.out.println("Welcome to Receiver Dashboard");
                    try {
                        // Establish the database connection
                        System.out.println(" ");
                        System.out.println("Enter Receiver id,ReceiverName,requiredBloodGroup,contactDetails,address");
                        System.out.print("Enter Receiver id : ");
                        int receiverid = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter Receiver Name : ");
                        String receiverName = scanner.nextLine();
                        System.out.println("Enter Receiver Blood Group");
                        String requiredBloodGroup = scanner.nextLine();
                        System.out.println("Enter Receiver Contact Details");
                        String contactDetails = scanner.nextLine();
                        System.out.println("Enter Receiver Address");
                        String address=scanner.nextLine();

                        Connection connection = DriverManager.getConnection(url, user, password);

                        // Prepare the SQL insert statement
                        String insertQuery = "INSERT INTO receiver (receiverid,receivername, bloodgrp, contactno,address) VALUES (?, ?, ?,?,?);";
                        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                          preparedStatement.setInt(1, receiverid);
                        preparedStatement.setString(2, receiverName);
                        preparedStatement.setString(3, requiredBloodGroup);
                        preparedStatement.setString(4, contactDetails);
                        preparedStatement.setString(5,address);

                        // Execute the insert statement
                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Receiver registered successfully!");
                        } else {
                            System.out.println("Failed to register User.");
                        }
                        System.out.println("");
                        preparedStatement.close();
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 2: {
                    System.out.println(" ");
                    System.out.println("Welcome to Receiver Update Page");
                    // System.out.println("Enter the Donor ID");

                    try  {
                        // Step 2: Get Advocate ID input from user
                        Connection connection = DriverManager.getConnection(url, user, password);

                        System.out.print("Enter Receiver ID to modify: ");
                        int receiverId=Integer.parseInt(scanner.nextLine());

                        // Step 3: Check if customer ID exists in the database
                        String checkQuery = "SELECT * FROM Receiver WHERE receiverid = ?";
                        try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                            checkStatement.setInt(1, receiverId);
                            ResultSet resultSet = checkStatement.executeQuery();

                            if (!resultSet.next()) {
                                System.out.println("Receiver ID does not exist in the database.");
                                break;
                            }
                        }

                        //  Get the field to modify from the user
                        System.out.println(" ");
                        System.out.println("Select the field to modify:");
                        System.out.println("1. ReceiverName");
                        System.out.println("2. RequiredBloodGroup");
                        System.out.println("3. ReceiverAddress");
                        System.out.println("");
//                        System.out.println("4:");
                        System.out.print("Enter your Choice");
                        int fieldChoice = Integer.parseInt(scanner.nextLine());
                        // Consume the newline character

                        //  Get the new value for the selected field
                        String newValue = "";
                        switch (fieldChoice) {
                            case 1:
                                System.out.print("Enter new name: ");
                                newValue = scanner.nextLine();
                                break;
                            case 2:
                                System.out.print("Enter BloodGroup: ");
                                newValue = scanner.nextLine();
                                break;
                            case 3:
                                System.out.print("Enter new Address: ");
                                newValue = scanner.nextLine();
                                break;
                            default:
                                System.out.println("Invalid field choice.");
                                return;
                        }
                        //  Execute the update query
                        String updateQuery = "";
                        switch (fieldChoice) {
                            case 1:
                                updateQuery = "UPDATE receiver SET receivername= ? WHERE receiverid = ?";
                                break;
                            case 2:
                                updateQuery = "UPDATE receiver SET bloodgrp = ? WHERE receiverid = ?";
                                break;
                            case 3:{
                                updateQuery = "UPDATE receiver SET contactno = ? WHERE receiverid = ?";
                            }
                        }
                        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                            statement.setString(1, newValue);
                            statement.setInt(2, receiverId);
                            //Execute the query
                            int rowsAffected = statement.executeUpdate();
                            System.out.println(rowsAffected + " row(s) updated.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 3: {
                    try {
                        System.out.println(" ");
                        System.out.println("Welcome to Receiver Deletion Page");
                        // Establish the database connection
                        System.out.print("Enter the Receiver id: ");
                        Connection connection = DriverManager.getConnection(url, user, password);

                        int receiverId = Integer.parseInt(scanner.nextLine());
                        String checkQuery = "SELECT * FROM receiver WHERE receiverid = ?";
                        String delQueryString = "DELETE FROM receiver WHERE receiverid = ?";
                        PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
                        checkStatement.setInt(1, receiverId);
                        ResultSet resultSet = checkStatement.executeQuery();
                        int flag = 0;
                        if (!resultSet.next()) {
                            System.out.println("Receiver ID does not exist in the database.");
                            System.out.println(" ");
                            break;
                        } else {
                            System.out.println("");
                            int id1 = resultSet.getInt("receiverid");
                            String receiverName = resultSet.getString("receivername");
                            String bloodGroup = resultSet.getString("bloodgrp");
                            String contactDetails = resultSet.getString("contactno");
                            String address= resultSet.getString("address");
                            //
                            System.out.println("ID: " + id1);
                            System.out.println("Name: " + receiverName);
                            System.out.println("Adress: " + bloodGroup);
                            System.out.println("Number: " + contactDetails);
                            System.out.println("BloodGroup: "+address);

                            System.out.println("======================");
                        }
                        int delete=0;
                        System.out.println("Do You Want to Delete the Receiver ");
                        System.out.println("Press 1 for conformation");
                        delete=Integer.parseInt(scanner.nextLine());


                        if(delete!=1) break;
                        System.out.println("Deleting statement");
                        String sql = "DELETE FROM receiver WHERE receiverid = ?";
                        PreparedStatement statement = connection.prepareStatement(sql);

                        // Set the ID parameter
                        // Replace with your custom ID
                        statement.setInt(1, receiverId);

                        // Execute the SQL query
                        int rowsAffected = statement.executeUpdate();

                        // Check the number of rows affected
                        if (rowsAffected > 0) {
                            System.out.println("Data deleted successfully.");
                        } else {
                            System.out.println("No data found to delete.");
                        }

                        // Close the statement and connection
                        statement.close();
                    }

                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        } while (choice != 4);
    }


    public  static  void generateDonorReport(Scanner scanner){
        try{
       final   String url = "jdbc:mysql://localhost:3306/blood_management";
      final  String user = "root";
        final String password = "aastha2802";

            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Donor Details");

            String query="select bloodgroup,count(donorid) from Donor group by(bloodgroup);";
            PreparedStatement checkStatement = connection.prepareStatement(query);
            ResultSet resultSet = checkStatement.executeQuery();
            System.out.println("BloodGroup   Total Number of Units");
            while(resultSet.next()){
                System.out.println("   " +resultSet.getString("bloodgroup")+"        "+resultSet.getString(2));
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public  static  void generateReceiverReport(Scanner scanner){
        try{
      final    String url = "jdbc:mysql://localhost:3306/blood_management";
      final  String user = "root";
      final  String password = "aastha2802";
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Receiver Details");

            String query="select bloodgrp,count(receiverid) from receiver group by(bloodgrp);";
            PreparedStatement checkStatement = connection.prepareStatement(query);
            ResultSet resultSet = checkStatement.executeQuery();
            System.out.println("BloodGroup   Total Number of Units");
            while(resultSet.next()){
                System.out.println("   " +resultSet.getString(1)+"        "+resultSet.getString(2));
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }
    // Admin
    public static void admin() {

    }

}