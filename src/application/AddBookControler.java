package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class AddBookControler implements Initializable {
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfEmail;
    @FXML
    private Button addBtn;
    @FXML
    private Button exportBtn;
    @FXML
    private Button importBtn;
    @FXML
    private Button removeBtn;
    @FXML
    private Button quitBtn;
    
    @FXML
    private TableView<Person> table;
    @FXML
    private TableColumn<Person, String> emailCol;
    @FXML
    private TableColumn<Person, String> firstNameCol;
    @FXML
    private TableColumn<Person, String> lastNameCol;
    
    private DataClass data;
    private ObservableList<Person> personList;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        data = new DataClass();
        
        // Create observable list to hold table data
        personList = FXCollections.observableArrayList();
        
        // Link table columns with Person properties
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        // Set the table's items
        table.setItems(personList);
        
        // Make table editable
        table.setEditable(true);
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());

        // Add commit handlers for each column
        firstNameCol.setOnEditCommit(event -> {
            Person person = event.getRowValue();
            String newValue = event.getNewValue();
            if (!newValue.isEmpty()) {
                person.setFirstName(newValue);
            } else {
                showAlert("Erreur", "Le prénom ne peut pas être vide !");
                table.refresh();
            }
        });
        
        lastNameCol.setOnEditCommit(event -> {
            Person person = event.getRowValue();
            String newValue = event.getNewValue();
            if (!newValue.isEmpty()) {
                person.setLastName(newValue);
            } else {
                showAlert("Erreur", "Le nom ne peut pas être vide !");
                table.refresh();
            }
        });

        emailCol.setOnEditCommit(event -> {
            Person person = event.getRowValue();
            String newEmail = event.getNewValue();
            if (isEmailAddress(newEmail)) {
                person.setEmail(newEmail);
            } else {
                showAlert("Erreur", "Adresse email invalide !");
                table.refresh();
            }
        });

        // Add initial data to the table
        personList.addAll(data.getImportList());
        
        // Debug message
        System.out.println("Initialization complete. Table items: " + personList.size());
    }

    @FXML
    public void Add() {
        System.out.println("Add method called");
        
        if (tfFirstName == null || tfLastName == null || tfEmail == null) {
            System.out.println("Error: One or more text fields are null");
            return;
        }
        
        String firstName = tfFirstName.getText().trim();
        String lastName = tfLastName.getText().trim();
        String email = tfEmail.getText().trim();

        System.out.println("Input values: First Name=" + firstName + ", Last Name=" + lastName + ", Email=" + email);

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis !");
            return;
        }
        
        if (!isEmailAddress(email)) {
            showAlert("Erreur", "Adresse email invalide !");
            return;
        }

        Person newPerson = new Person(firstName, lastName, email);
        System.out.println("Adding new person: " + newPerson);
        
        personList.add(newPerson);
        System.out.println("New person added. Table size: " + personList.size());
        
        clearFields();
    }

    @FXML
    public void Exporter() {
        data.setExportList(personList);
        showAlert("Succès", "Les données ont été exportées avec succès !", Alert.AlertType.INFORMATION);
    }

    @FXML
    public void Importer() {
        personList.clear();
        personList.addAll(data.getImportList());
        showAlert("Succès", "Les données ont été importées avec succès !", Alert.AlertType.INFORMATION);
    }

    @FXML
    public void Remove() {
        Person selectedPerson = table.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            personList.remove(selectedPerson);
        } else {
            showAlert("Erreur", "Aucune ligne sélectionnée !");
        }
    }

    @FXML
    public void Quit() {
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        stage.close();
    }

    public static boolean isEmailAddress(String email) {
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    private void showAlert(String title, String message) {
        showAlert(title, message, Alert.AlertType.ERROR);
    }
    
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        tfFirstName.clear();
        tfLastName.clear();
        tfEmail.clear();
    }
}