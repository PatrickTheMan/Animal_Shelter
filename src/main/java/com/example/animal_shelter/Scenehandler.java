package com.example.animal_shelter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Scenehandler {

    //region [Variables]

    private static Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    private static double screenX = screenBounds.getMaxX();
    private static double screenY = screenBounds.getMaxY();

    public static Stage masterStage = new Stage();

    //endregion

    //region [Booking Scene]

    public static void bookingScene(){
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: LightBlue");

        Scene scene = new Scene(pane,420,420);
        masterStage.setScene(scene);

        Button butC = new Button();
        butC.setLayoutY(350);
        butC.setLayoutX(250);
        butC.setText("Create Customer");
        pane.getChildren().add(butC);
        butC.setOnAction(actionEvent -> {
            customerScene();
        });

        TextField phoneText = new TextField();
        phoneText.setLayoutX(150);
        phoneText.setLayoutY(50);
        phoneText.setPrefWidth(200);
        pane.getChildren().add(phoneText);

        Label phoneErrorText = new Label();
        phoneErrorText.setStyle("-fx-text-fill: RED; -fx-font-size: 10;");
        phoneErrorText.setLayoutX(150);
        phoneErrorText.setLayoutY(75);
        phoneErrorText.setPrefWidth(200);
        phoneErrorText.setVisible(false);
        pane.getChildren().add(phoneErrorText);

        ChoiceBox animalBox = new ChoiceBox();
        animalBox.setLayoutX(150);
        animalBox.setLayoutY(100);
        animalBox.setPrefWidth(200);
        pane.getChildren().add(animalBox);

        Label animalErrorText = new Label();
        animalErrorText.setStyle("-fx-text-fill: RED; -fx-font-size: 10;");
        animalErrorText.setText("Needs to have selected");
        animalErrorText.setLayoutX(150);
        animalErrorText.setLayoutY(125);
        animalErrorText.setPrefWidth(200);
        animalErrorText.setVisible(false);
        pane.getChildren().add(animalErrorText);

        // TEST TEST
        ArrayList<String> arrayList = new ArrayList();
        arrayList.add("Nice");
        ObservableList observableList = FXCollections.observableArrayList(arrayList);
        animalBox.setItems(observableList);
        //

        ChoiceBox weekAmountBox = new ChoiceBox();
        weekAmountBox.setLayoutX(200);
        weekAmountBox.setLayoutY(175);
        weekAmountBox.setPrefWidth(150);
        pane.getChildren().add(weekAmountBox);

        Label weekAmountErrorText = new Label();
        weekAmountErrorText.setStyle("-fx-text-fill: RED; -fx-font-size: 10;");
        weekAmountErrorText.setText("Needs to have selected");
        weekAmountErrorText.setLayoutX(200);
        weekAmountErrorText.setLayoutY(200);
        weekAmountErrorText.setPrefWidth(150);
        weekAmountErrorText.setVisible(false);
        pane.getChildren().add(weekAmountErrorText);

        ChoiceBox weekStartBox = new ChoiceBox();
        weekStartBox.setLayoutX(50);
        weekStartBox.setLayoutY(175);
        weekStartBox.setPrefWidth(150);
        pane.getChildren().add(weekStartBox);

        Label weekStartErrorText = new Label();
        weekStartErrorText.setStyle("-fx-text-fill: RED; -fx-font-size: 10;");
        weekStartErrorText.setText("Needs to have selected");
        weekStartErrorText.setLayoutX(50);
        weekStartErrorText.setLayoutY(200);
        weekStartErrorText.setPrefWidth(150);
        weekStartErrorText.setVisible(false);
        pane.getChildren().add(weekStartErrorText);

        Label phoneLabel = new Label();
        phoneLabel.setText("Phone:");
        phoneLabel.setLayoutX(50);
        phoneLabel.setLayoutY(48);
        phoneLabel.setStyle("-fx-font-size: 20");
        pane.getChildren().add(phoneLabel);

        Label animalLabel = new Label();
        animalLabel.setText("Animal:");
        animalLabel.setLayoutX(50);
        animalLabel.setLayoutY(98);
        animalLabel.setStyle("-fx-font-size: 20");
        pane.getChildren().add(animalLabel);

        Label weekStartLabel = new Label();
        weekStartLabel.setText("Week Amount");
        weekStartLabel.setStyle("-fx-font-size: 18");
        weekStartLabel.setLayoutX(200);
        weekStartLabel.setLayoutY(150);
        pane.getChildren().add(weekStartLabel);

        Label weekAmountLabel = new Label();
        weekAmountLabel.setText("Week Start");
        weekAmountLabel.setStyle("-fx-font-size: 18");
        weekAmountLabel.setLayoutX(50);
        weekAmountLabel.setLayoutY(150);
        pane.getChildren().add(weekAmountLabel);

        Label successText = new Label();
        successText.setStyle("-fx-text-fill: GREEN; -fx-font-size: 15;");
        successText.setText("Success: Booking Made");
        successText.setLayoutX(50);
        successText.setLayoutY(300);
        successText.setVisible(false);
        pane.getChildren().add(successText);

        Button butBook = new Button();
        butBook.setLayoutX(258);
        butBook.setLayoutY(300);
        butBook.setText("Make Booking");
        pane.getChildren().add(butBook);
        butBook.setOnAction(actionEvent -> {

            boolean error;
            error = false;
            successText.setVisible(false);

            if (phoneText.getText().length()==8){
                phoneErrorText.setVisible(false);

                if (!SQL.checkPhoneNum(phoneText.getText())){
                    phoneErrorText.setVisible(true);
                    phoneErrorText.setText("Customer not found");
                    error=true;
                }

                try {
                    Integer.parseInt(phoneText.getText());
                } catch (NumberFormatException e){
                    phoneErrorText.setVisible(true);
                    phoneErrorText.setText("Not a number");
                    error=true;
                }

            } else {
                phoneErrorText.setVisible(true);
                error=true;
                if (phoneText.getText().length()<=0){
                    phoneErrorText.setText("Needs to be filled");
                } else {
                    phoneErrorText.setText("PhoneNum needs to be 8 chars");
                }
            }

            if (animalBox.getValue()!=null){
                animalErrorText.setVisible(false);
            } else {
                animalErrorText.setVisible(true);
                error=true;
            }

            if (weekStartBox.getValue()!=null){
                weekStartErrorText.setVisible(false);
            } else {
                weekStartErrorText.setVisible(true);
                error=true;
            }

            if (weekAmountBox.getValue()!=null){
                weekAmountErrorText.setVisible(false);
            } else {
                weekAmountErrorText.setVisible(true);
                error=true;
            }


            if (!error){
                successText.setVisible(false);
                SQL.addBooking(phoneText.getText(),
                        animalBox.getValue().toString(),
                        ""/*Missing*/,
                        Integer.parseInt(weekStartBox.getValue().toString()),
                        Integer.parseInt(weekAmountBox.getValue().toString())
                );
            }

        });

        Button butAnimal = new Button();
        butAnimal.setLayoutX(50);
        butAnimal.setLayoutY(350);
        butAnimal.setText("Register Animal");
        pane.getChildren().add(butAnimal);
        butAnimal.setOnAction(actionEvent -> {
            animalScene();
        });

        masterStage.setTitle("Animal Shelter");
    }

    //endregion

    //region [Animal Scene]

    public static void animalScene(){

        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: LightBlue");

        Scene scene = new Scene(pane,420,420);
        masterStage.setScene(scene);

        TextField phoneText = new TextField();
        phoneText.setLayoutX(130);
        phoneText.setLayoutY(50);
        phoneText.setPrefWidth(220);
        pane.getChildren().add(phoneText);

        TextField nameText = new TextField();
        nameText.setLayoutX(130);
        nameText.setLayoutY(100);
        nameText.setPrefWidth(220);
        pane.getChildren().add(nameText);

        Label nameErrorText = new Label();
        nameErrorText.setStyle("-fx-text-fill: RED; -fx-font-size: 10;");
        nameErrorText.setText("Needs to be filled");
        nameErrorText.setLayoutX(130);
        nameErrorText.setLayoutY(125);
        nameErrorText.setPrefWidth(220);
        nameErrorText.setVisible(false);
        pane.getChildren().add(nameErrorText);

        TextField typeText = new TextField();
        typeText.setLayoutX(130);
        typeText.setLayoutY(150);
        typeText.setPrefWidth(220);
        pane.getChildren().add(typeText);

        Label typeErrorText = new Label();
        typeErrorText.setStyle("-fx-text-fill: RED; -fx-font-size: 10;");
        typeErrorText.setText("Needs to be filled");
        typeErrorText.setLayoutX(130);
        typeErrorText.setLayoutY(175);
        typeErrorText.setPrefWidth(220);
        typeErrorText.setVisible(false);
        pane.getChildren().add(typeErrorText);

        Label phoneErrorText = new Label();
        phoneErrorText.setStyle("-fx-text-fill: RED; -fx-font-size: 10;");
        phoneErrorText.setLayoutX(130);
        phoneErrorText.setLayoutY(75);
        phoneErrorText.setVisible(false);
        pane.getChildren().add(phoneErrorText);

        Label phoneLabel = new Label();
        phoneLabel.setText("Phone:");
        phoneLabel.setLayoutX(50);
        phoneLabel.setLayoutY(48);
        phoneLabel.setStyle("-fx-font-size: 18");
        pane.getChildren().add(phoneLabel);

        Label nameLabel = new Label();
        nameLabel.setText("Name:");
        nameLabel.setLayoutX(50);
        nameLabel.setLayoutY(98);
        nameLabel.setStyle("-fx-font-size: 18");
        pane.getChildren().add(nameLabel);

        Label typeLabel = new Label();
        typeLabel.setText("Type:");
        typeLabel.setLayoutX(50);
        typeLabel.setLayoutY(148);
        typeLabel.setStyle("-fx-font-size: 18");
        pane.getChildren().add(typeLabel);

        Label successText = new Label();
        successText.setStyle("-fx-text-fill: GREEN; -fx-font-size: 15;");
        successText.setText("Success: Animal Added");
        successText.setLayoutX(50);
        successText.setLayoutY(250);
        successText.setVisible(false);
        pane.getChildren().add(successText);

        Button butAdd = new Button();
        butAdd.setText("Add");
        butAdd.setLayoutX(310);
        butAdd.setLayoutY(250);
        pane.getChildren().add(butAdd);
        butAdd.setOnAction(actionEvent -> {

            boolean error;
            error = false;
            if (successText.isVisible()){
                successText.setVisible(false);
            }

            if (phoneText.getText().length()==8){
                phoneErrorText.setVisible(false);

                if (!SQL.checkPhoneNum(phoneText.getText())){
                    phoneErrorText.setVisible(true);
                    phoneErrorText.setText("Customer not found");
                    error=true;
                }

                try {
                    Integer.parseInt(phoneText.getText());
                } catch (NumberFormatException e){
                    phoneErrorText.setVisible(true);
                    phoneErrorText.setText("Not a number");
                    error=true;
                }

            } else {
                phoneErrorText.setVisible(true);
                error=true;
                if (phoneText.getText().length()<=0){
                    phoneErrorText.setText("Needs to be filled");
                } else {
                    phoneErrorText.setText("PhoneNum needs to be 8 chars");
                }
            }

            if (nameText.getText().length()>0){
                nameErrorText.setVisible(false);
            } else {
                nameErrorText.setVisible(true);
                error=true;
            }

            if (typeText.getText().length()>0){
                typeErrorText.setVisible(false);
            } else {
                typeErrorText.setVisible(true);
                error=true;
            }



            if (!error){
                successText.setVisible(true);

                SQL.addAnimal(nameText.getText(),typeText.getText(),phoneText.getText());

                nameText.setText("");
                typeText.setText("");
                phoneText.setText("");
            }


        });

        Button butBooking = new Button();
        butBooking.setLayoutX(50);
        butBooking.setLayoutY(350);
        butBooking.setText("Booking");
        pane.getChildren().add(butBooking);
        butBooking.setOnAction(actionEvent ->{
            bookingScene();
        });
    }

    //endregion

    //region [Customer Scene]

    public static void customerScene(){

        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: LightBlue");

        Scene scene = new Scene(pane,420,420);
        masterStage.setScene(scene);

        TextField nameText = new TextField();
        nameText.setLayoutX(130);
        nameText.setLayoutY(50);
        nameText.setPrefWidth(220);
        pane.getChildren().add(nameText);

        Label nameErrorText = new Label();
        nameErrorText.setStyle("-fx-text-fill: RED; -fx-font-size: 10;");
        nameErrorText.setText("Needs to be filled");
        nameErrorText.setLayoutX(130);
        nameErrorText.setLayoutY(75);
        nameErrorText.setPrefWidth(220);
        nameErrorText.setVisible(false);
        pane.getChildren().add(nameErrorText);

        TextField phoneText = new TextField();
        phoneText.setLayoutX(130);
        phoneText.setLayoutY(100);
        phoneText.setPrefWidth(220);
        pane.getChildren().add(phoneText);

        Label phoneErrorText = new Label();
        phoneErrorText.setStyle("-fx-text-fill: RED; -fx-font-size: 10;");
        phoneErrorText.setLayoutX(130);
        phoneErrorText.setLayoutY(125);
        phoneErrorText.setPrefWidth(220);
        phoneErrorText.setVisible(false);
        pane.getChildren().add(phoneErrorText);

        TextField mailText = new TextField();
        mailText.setLayoutX(130);
        mailText.setLayoutY(150);
        mailText.setPrefWidth(220);
        pane.getChildren().add(mailText);

        Label mailErrorText = new Label();
        mailErrorText.setStyle("-fx-text-fill: RED; -fx-font-size: 10;");
        mailErrorText.setLayoutX(130);
        mailErrorText.setLayoutY(175);
        mailErrorText.setPrefWidth(220);
        mailErrorText.setVisible(false);
        pane.getChildren().add(mailErrorText);

        TextField addressText = new TextField();
        addressText.setLayoutX(130);
        addressText.setLayoutY(200);
        addressText.setPrefWidth(120);
        pane.getChildren().add(addressText);

        Label addressErrorText = new Label();
        addressErrorText.setStyle("-fx-text-fill: RED; -fx-font-size: 10;");
        addressErrorText.setText("Needs to be filled");
        addressErrorText.setLayoutX(130);
        addressErrorText.setLayoutY(225);
        addressErrorText.setPrefWidth(220);
        addressErrorText.setVisible(false);
        pane.getChildren().add(addressErrorText);

        TextField zipText = new TextField();
        zipText.setLayoutX(290);
        zipText.setLayoutY(200);
        zipText.setPrefWidth(60);
        pane.getChildren().add(zipText);

        Label zipErrorText = new Label();
        zipErrorText.setStyle("-fx-text-fill: RED; -fx-font-size: 10;");
        zipErrorText.setText("Needs to be filled");
        zipErrorText.setLayoutX(290);
        zipErrorText.setLayoutY(225);
        zipErrorText.setPrefWidth(60);
        zipErrorText.setVisible(false);
        pane.getChildren().add(zipErrorText);

        Label zipLabel = new Label();
        zipLabel.setText("Zip:");
        zipLabel.setLayoutX(255);
        zipLabel.setLayoutY(200);
        zipLabel.setStyle("-fx-font-size: 18");
        pane.getChildren().add(zipLabel);

        Label mailLabel = new Label();
        mailLabel.setText("Mail:");
        mailLabel.setLayoutX(50);
        mailLabel.setLayoutY(148);
        mailLabel.setStyle("-fx-font-size: 18");
        pane.getChildren().add(mailLabel);

        Label addressLabel = new Label();
        addressLabel.setText("Address:");
        addressLabel.setLayoutX(50);
        addressLabel.setLayoutY(198);
        addressLabel.setStyle("-fx-font-size: 18");
        pane.getChildren().add(addressLabel);

        Label nameLabel = new Label();
        nameLabel.setText("Name:");
        nameLabel.setLayoutX(50);
        nameLabel.setLayoutY(48);
        nameLabel.setStyle("-fx-font-size: 18");
        pane.getChildren().add(nameLabel);

        Label phoneLabel = new Label();
        phoneLabel.setText("Phone:");
        phoneLabel.setLayoutX(50);
        phoneLabel.setLayoutY(98);
        phoneLabel.setStyle("-fx-font-size: 18");
        pane.getChildren().add(phoneLabel);

        Label successText = new Label();
        successText.setStyle("-fx-text-fill: GREEN; -fx-font-size: 15;");
        successText.setText("Success: Customer Added");
        successText.setLayoutX(50);
        successText.setLayoutY(300);
        successText.setVisible(false);
        pane.getChildren().add(successText);

        Button butCreate = new Button();
        butCreate.setLayoutX(300);
        butCreate.setLayoutY(300);
        butCreate.setText("Create");
        pane.getChildren().add(butCreate);
        butCreate.setOnAction(actionEvent ->{

            boolean error;
            error=false;
            if (successText.isVisible()){
                successText.setVisible(false);
            }

            if (phoneText.getText().length()==8){
                phoneErrorText.setVisible(false);

                if (SQL.checkPhoneNum(phoneText.getText())){
                    phoneErrorText.setVisible(true);
                    phoneErrorText.setText("Duplicate found");
                    error=true;
                }

                try {
                    Integer.parseInt(phoneText.getText());
                } catch (NumberFormatException e){
                    phoneErrorText.setVisible(true);
                    phoneErrorText.setText("Not a number");
                    error=true;
                }

            } else {
                phoneErrorText.setVisible(true);
                error=true;
                if (phoneText.getText().length()<=0){
                    phoneErrorText.setText("Needs to be filled");
                } else {
                    phoneErrorText.setText("PhoneNum needs to be 8 chars");
                }
            }

            if (nameText.getText().length()>0){
                nameErrorText.setVisible(false);
            } else {
                nameErrorText.setVisible(true);
                error=true;
            }

            if (addressText.getText().length()>0){
                addressErrorText.setVisible(false);
            } else {
                addressErrorText.setVisible(true);
                error=true;
            }

            if (mailText.getText().length()>0){
                mailErrorText.setVisible(false);

                if (SQL.checkEmail(mailText.getText())){
                    mailErrorText.setVisible(true);
                    mailErrorText.setText("Duplicate found");
                    error=true;
                }

                if (!mailText.getText().contains("@") || !mailText.getText().contains(".")){
                    mailErrorText.setVisible(true);
                    mailErrorText.setText("Not a email");
                    error=true;
                }

            } else {
                mailErrorText.setVisible(true);
                mailErrorText.setText("Needs to be filled");
                error=true;
            }

            if (zipText.getText().length()==4){
                zipErrorText.setVisible(false);
            } else {
                zipErrorText.setVisible(true);
                error=true;
                if (zipText.getText().length()<=0){
                    zipErrorText.setText("Needs to be filled");
                } else {
                    zipErrorText.setText("4 char max.");
                }
            }

            if (!error){

                successText.setVisible(true);

                SQL.addCustomer(phoneText.getText(),nameText.getText(),mailText.getText(),addressText.getText(),Integer.parseInt(zipText.getText()));

                phoneText.setText("");
                nameText.setText("");
                mailText.setText("");
                addressText.setText("");
                zipText.setText("");
            }


        });

        Button butBooking = new Button();
        butBooking.setLayoutX(50);
        butBooking.setLayoutY(350);
        butBooking.setText("Booking");
        pane.getChildren().add(butBooking);
        butBooking.setOnAction(actionEvent ->{
            bookingScene();
        });

    }

    //endregion

    //region [Other methods]

    public static void setStage(Stage stage) {
        masterStage = stage;
        masterStage.setResizable(false);
    }

    /**
     * <Strong>This is used for setting the stages location</Strong>
     */
    public static void setSceneLocation(){

        setStageLocation((screenX/2)-210,(screenY/2)-250);

    }

    /**
     * <Strong>This is for setting the stage's location on the screen</Strong>
     * @param x is the x position given in pixels
     * @param y is the y position given in pixels
     */
    public static void setStageLocation(double x, double y){

        masterStage.setX(x);
        masterStage.setY(y);

    }

    //endregion

}