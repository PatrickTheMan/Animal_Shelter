package com.example.animal_shelter;
import java.sql.*;

public class SQL {

    private static Connection con;

    /**
     *  establishes the connection to the database of the Animal Shelter
     */
    public static void connect() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Animal_Schelter","Patrick","123456");
            //System.out.println("Connected to "+con.getCatalog());
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * disconnects from the database
     */
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

    /**
     * creates a new animal in the table Animal in the database by reading in the
     * @param animalName is the name of the animal
     * @param animalTyp  is what kind of animal typ it is
     * @param phoneNum is the phone number of the customer/owner of the animal (FK from table Customer)
     */
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

    /**
     * checked the database whether the phone number already exist
     * @param phoneNum is the phone number from the customer the user is checking
     * @return a boolean to se if the already on exist with the same number
     */
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

    /**
     * checked the database whether the E-Mail address already exist
     * @param email is the E-Mail address from the customer the user is checking
     * @return a boolean to find out if the already on exists with the same E-Mail address
     */
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

    /**
     * creates a new booking in the table Booking in the database based on the
     * @param phoneNum is the phone number from the customer
     * @param animalName is the name from the customers animal
     * @param location is location of the animal shelter
     * @param weekStart is the week number when the animal will arrive
     * @param weekAmount is the number of weeks the animal will stay at the shelter
     */
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
