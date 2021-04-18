package dev.dariogarcia.contactapp;

import dev.dariogarcia.contactapp.datamodel.ContactData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void init() throws Exception {
        try {
            ContactData.getInstance().loadContacts();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getStackTrace());
        } catch (XMLStreamException ex) {
            System.out.println(ex.getStackTrace());
        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("resources/MainWindow.fxml"));
        primaryStage.setTitle("Contacts App");
        primaryStage.setScene(new Scene(root, 900, 300));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
