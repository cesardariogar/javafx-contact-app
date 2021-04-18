package dev.dariogarcia.contactapp;

import dev.dariogarcia.contactapp.datamodel.Contact;
import dev.dariogarcia.contactapp.datamodel.ContactData;
import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Optional;

public class AddDialogController {

    @FXML
    private DialogPane DialogPaneContactAdd;

    @FXML
    private TextField txtFldFirstName;

    @FXML
    private TextField txtFldLastName;

    @FXML
    private TextField txtFldPhoneNumber;

    @FXML
    private TextArea txtAreaNotes;

    public Optional<Contact> addContact() {
        String firstName = txtFldFirstName.getText().trim();
        String lastName = txtFldLastName.getText().trim();
        String phoneNumber = txtFldPhoneNumber.getText().trim();
        String notes = txtAreaNotes.getText();

        if (firstName.equals("") || lastName.equals("") || phoneNumber.equals("")) {
            return Optional.empty();
        }

        Contact newContact = new Contact(firstName, lastName, phoneNumber, notes);

        ContactData.getInstance().addContact(newContact);

        return Optional.of(newContact);

    }


}
