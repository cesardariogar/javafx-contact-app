package dev.dariogarcia.contactapp;

import dev.dariogarcia.contactapp.datamodel.Contact;
import dev.dariogarcia.contactapp.datamodel.ContactData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

public class MainWindowController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private TableView<Contact> contactsTableView;

    @FXML
    private TableColumn<Contact, String> first_name;

    @FXML
    private TableColumn<Contact, String> last_name;

    @FXML
    private TableColumn<Contact, String> phone_number;

    @FXML
    private TableColumn<Contact, String> notes;

    @FXML
    private ContextMenu listContextMenu;

    public void initialize() {
        first_name.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
        last_name.setCellValueFactory(new PropertyValueFactory<Contact, String>("lastName"));
        phone_number.setCellValueFactory(new PropertyValueFactory<Contact, String>("phoneNumber"));
        notes.setCellValueFactory(new PropertyValueFactory<Contact, String>("notes"));

        // Column Editable: First Name
        first_name.setCellFactory(TextFieldTableCell.forTableColumn());
        first_name.setOnEditCommit(
                new EventHandler<CellEditEvent<Contact, String>>() {
                    @Override
                    public void handle(CellEditEvent<Contact, String> t) {
                        t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setFirstName(t.getNewValue());
                                ContactData.getInstance().saveContacts();
                    }
                }
        );

        // Column Editable: Last Name
        last_name.setCellFactory(TextFieldTableCell.forTableColumn());
        last_name.setOnEditCommit(
                new EventHandler<CellEditEvent<Contact, String>>() {
                    @Override
                    public void handle(CellEditEvent<Contact, String> t) {
                        t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setLastName(t.getNewValue());
                                ContactData.getInstance().saveContacts();
                    }
                }
        );

        // Column Editable: Phone Number
        phone_number.setCellFactory(TextFieldTableCell.forTableColumn());
        phone_number.setOnEditCommit(
                new EventHandler<CellEditEvent<Contact, String>>() {
                    @Override
                    public void handle(CellEditEvent<Contact, String> t) {
                        t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setPhoneNumber(t.getNewValue());
                                ContactData.getInstance().saveContacts();
                    }
                }
        );

        // Column Editable: Notes
        notes.setCellFactory(TextFieldTableCell.forTableColumn());
        notes.setOnEditCommit(
                new EventHandler<CellEditEvent<Contact, String>>() {
                    @Override
                    public void handle(CellEditEvent<Contact, String> t) {
                        t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setNotes(t.getNewValue());
                                ContactData.getInstance().saveContacts();
                    }
                }
        );

        // Create "Delete" Context Menu
        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Contact item = contactsTableView.getSelectionModel().getSelectedItem();
                deleteItem(item);
            }
        });

        contactsTableView.setRowFactory(new Callback<TableView<Contact>, TableRow<Contact>>() {
            @Override
            public TableRow<Contact> call(TableView<Contact> contactTableView) {
                TableRow<Contact> row = new TableRow<>();

                // Add delete context menu to non-empty rows
                row.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty) -> {
                            if (isNowEmpty) {
                                row.setContextMenu(null);
                            } else {
                                row.setContextMenu(listContextMenu);
                            }
                        });
                return row;
            }
        });
        listContextMenu.getItems().addAll(deleteMenuItem);

        contactsTableView.setItems(ContactData.getInstance().getContacts());
    }

    public void deleteItem(Contact contact) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Delete Contact");
        confirmation.setHeaderText("Delete Contact Confirmation");
        confirmation.setContentText("Are you sure?  Press OK to confirm, or cancel to Back out.");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            ContactData.getInstance().deleteContact(contact);
        }
    }

    @FXML
    private void handleContactAdd() {
        Dialog<ButtonType> addContactDialog = new Dialog<>();
        addContactDialog.initOwner(mainBorderPane.getScene().getWindow());
        addContactDialog.setTitle("Add New Contact");
        addContactDialog.setHeaderText("Use this dialog to create a new Contact");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("resources/AddContact.fxml"));

        try {
            addContactDialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Can't load AddContactDialog");
            e.printStackTrace();
        }

        addContactDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        addContactDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = addContactDialog.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                // Get Dialog Controller
                AddDialogController dialogController = fxmlLoader.getController();

                Optional<Contact> optNewContact = dialogController.addContact();

                // Error: Incomplete form
                if (optNewContact.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Incomplete information");
                    alert.setContentText("New Contact could no be added due to missing information");
                    alert.show();
                }

                // Success. New Contact Added
                ContactData.getInstance().saveContacts();
                optNewContact.ifPresent(Item -> contactsTableView.getSelectionModel().select(Item));
            }
        }
    }

}
