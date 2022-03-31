package com.example.animal_shelter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQL {

    private static Connection con;

    /**
     *  establishes the connection to the database of the Animal Shelter
     */
    public static void connect() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Animal_Schelter","sa","123456");
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

    /**
     * gets the number of available cages at the chosen location over the year
     * @param address is the location/address where the user will search
     * @return all cages that are available
     */
    public static List<String> getAvailableCages(String address){
        List<String> projectList = new ArrayList<>();
        connect();

        try {
            // reads the stored procedure to find all available weeks
            PreparedStatement ps = con.prepareStatement("weekAvailable '"+address+"'");
            // executes the stored procedure until no data has been found
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                // stores the available number of cages
                String no = rs.getString(1);
                // adds the number of available cages in arraylist
                projectList.add(no);

            }
            ps.close();
            rs.close();
            disconnect();

            return projectList;

        }catch (SQLException e) {
            System.err.println(e.getMessage());
            disconnect();
            return null;
        }
    }

    /**
     * creates a new customer in the Customer table in the database by reading in the
     * @param phoneNum is the phone number of the customer (PK in the Table Customer)
     * @param customerName is the customer name
     * @param email is the E-mail address of the customer
     * @param address is the address from the customer
     * @param zipCode is the city where the customer lives (FK from table City)
     */
    public static void addCustomer(String phoneNum, String customerName, String email, String address, int zipCode){
        connect();
        try {
            // reads the stored procedure to create a new customer in the database
            PreparedStatement ps = con.prepareStatement("exec insertCustomer '"+phoneNum+"', '"+customerName+"', '"+email+"', '"+address+"', "+zipCode+";");
            // executes the stored procedure 
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

    /**
     * is looking in the database for all animal of a given customer
     * by searching with the phone number
     * @param phoneNum is the phone number of the costumer (PK Customer table and FK Animal table)
     * @return all name some was creating in the database with this phone number
     */
    public static List<String> getAnimalNames(String phoneNum){
        List<String> projectList = new ArrayList<>();
        connect();
        try {
            PreparedStatement ps = con.prepareStatement("getAnimalNames '"+phoneNum+"';");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                String no = rs.getString(1);

                projectList.add(no);

            }
            ps.close();
            rs.close();
            disconnect();

            return projectList;

        }catch (SQLException e) {
            System.err.println(e.getMessage());
            disconnect();
            return null;
        }
    }

    /**
     * is looking for an animal based on the phone number to se the starting week and how long it will stay
     * @param phoneNum is the phone number of customer to find the right animal
     * @return the name of the animal, starting week and number of weeks
     */
    public static List<String> getStartAndAmount(String phoneNum){
        List<String> projectList = new ArrayList<>();
        connect();
        try {
            PreparedStatement ps = con.prepareStatement("getStartAndAmount '"+phoneNum+"';");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                String no1 = rs.getString(1);
                projectList.add(no1);

                String no2 = rs.getString(2);
                projectList.add(no2);

                String no3 = rs.getString(3);
                projectList.add(no3);

            }
            ps.close();
            rs.close();
            disconnect();

            return projectList;

        }catch (SQLException e) {
            System.err.println(e.getMessage());
            disconnect();
            return null;
        }
    }

    /**
     * deletes a book from the database by reading in the parameter
     * @param phoneNum is the phone number from the customer
     * @param animalName is the animal name
     * @param weekStart is the week when the animal will arrive
     * @param amountWeek is the number of weeks the animal have to stay
     *
     */
    public static void deleteBooking(String phoneNum,String animalName,int weekStart,int amountWeek){
        connect();
        try {
            // reads the stored procedure to delete a booking from the database
            PreparedStatement ps = con.prepareStatement("deleteBooking '"+phoneNum+"','"+animalName+"',"+weekStart+","+amountWeek+";");
            // executes the stored procedures delete the booking
            ps.executeUpdate();
            ps.close();
            disconnect();
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            disconnect();
        }
    }

}
