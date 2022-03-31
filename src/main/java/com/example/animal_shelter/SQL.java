package com.example.animal_shelter;
import java.sql.*;

public class SQL {

    private static Connection con;

    public static void connect() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Animal_Schelter","Patrick","123456");
            //System.out.println("Connected to "+con.getCatalog());
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void disconnect() {
        try {
            //System.out.println("Disconnected from "+con.getCatalog());
            con.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void availableCages(int week, String address){
        connect();
        try {
            PreparedStatement ps = con.prepareStatement("exec weekAvailable "+week+", '"+address+"';");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                System.out.println("Available: "+rs.getString(1));
            }

            ps.close();
            rs.close();

            disconnect();
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            disconnect();
        }
    }

    public static void addCustomer(String phoneNum, String customerName, String email, String address, int zipCode){
        connect();
        try {
            PreparedStatement ps = con.prepareStatement("exec insertCustomer '"+phoneNum+"', '"+customerName+"', '"+email+"', '"+address+"', "+zipCode+";");
            ps.executeUpdate();

            System.out.println("Customer ("+customerName+") has been added");

            ps.close();

            disconnect();
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            disconnect();
        }
    }

    public static void addAnimal(String animalName, String animalTyp, String phoneNum){
        connect();
        try{
            PreparedStatement ps = con.prepareStatement("exec insertAnimal '"+animalName+"','"+animalTyp+"','"+phoneNum+"';");
            ps.executeUpdate();

            System.out.println("Animal ("+animalName+") has been added");

            ps.close();

            disconnect();
        }catch(SQLException e){
            System.err.println(e.getMessage());
            disconnect();
        }
    }

    public static boolean checkPhoneNum(String phoneNum){
        connect();

        try{
            PreparedStatement ps = con.prepareStatement("exec checkPhoneNum '"+phoneNum+"';");
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                rs.getString(1);

                ps.close();
                rs.close();

                disconnect();

                return true;
            } else {

                ps.close();
                rs.close();

                disconnect();

                return false;
            }
        }catch(SQLException e){
            System.err.println(e.getMessage());
            disconnect();
            return false;
        }
    }

    public static boolean checkEmail(String email){
        connect();

        try{
            PreparedStatement ps = con.prepareStatement("exec checkEmail '"+email+"';");
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                rs.getString(1);

                ps.close();
                rs.close();

                disconnect();

                return true;
            } else {

                ps.close();
                rs.close();

                disconnect();

                return false;
            }
        }catch(SQLException e){
            System.err.println(e.getMessage());
            disconnect();
            return false;
        }
    }

    public static void addBooking(String phoneNum, String animalName,String location, int weekStart, int weekAmount){

        connect();

        try {
            PreparedStatement ps = con.prepareStatement("exec createPeriods "+weekStart+","+weekAmount+";");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e){
            System.out.println("Period Error: "+e.getMessage());
            disconnect();
            return;
        }

        try {
            PreparedStatement ps = con.prepareStatement("exec insertBooking '"+phoneNum+"','"+animalName+"','"+location+"',"+weekStart+","+weekAmount+";");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e){
            System.out.println("Booking Error: "+e.getMessage());
            disconnect();
            return;
        }

        disconnect();

    }

    public static void getCustomer(){

    }
}
