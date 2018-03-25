package yelpReviewHashing;

import java.io.*;
import java.util.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private HashTable ht = new HashTable();
    private Button search = new Button();
    private ListView businessMenu;
    private ListView similarBusinesses;
    private ArrayList<String> allBusinesses = new ArrayList<String>();

    public static void main(String[] args) {
        launch(args);
    }

    //get similar businesses once selected
    private void buttonPress() {
        ObservableList<String> list = FXCollections.observableArrayList(ht.getSimilar(businessMenu.getSelectionModel().getSelectedItem().toString()));
        similarBusinesses.setItems(list);

    }


    @Override
    public void start(Stage stage) throws Exception {


        stage.setTitle("YelpDataComparing");

        //Parse json
        try {
            Parser.parse("/home/jsantos4/Documents/csc365/YelpDataBinaryTree/src/main/resources/business.json", ht); //path = directory path of json file
        } catch (IOException e) {
            e.printStackTrace();
        }

        //put all businesses into array list
        ht.getAll(allBusinesses);

        //put array list of businesses into menu list
        businessMenu = new javafx.scene.control.ListView<String>();
        ObservableList<String> list = FXCollections.observableArrayList(allBusinesses);
        businessMenu.setItems(list);
        search.setText("Search");

        //create new list view for similar businesses
        similarBusinesses = new javafx.scene.control.ListView<String>();

        //call method to select business in menu on button press
        search.setOnAction(event -> buttonPress());



        //display gui
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(businessMenu, similarBusinesses, search);
        Scene scene = new Scene(layout, 800, 450);
        stage.setScene(scene);
        stage.show();

    }

}
