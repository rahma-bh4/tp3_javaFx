package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        data = new DataClass();
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        table.setEditable(true);
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());

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

        table.getItems().addAll(data.getImportList());
    }

    @FXML
    public void ajoutbutton() {
        System.out.println("Bouton Ajouter cliqué !"); // Débogage
        if (tfFirstName == null || tfLastName == null || tfEmail == null) {
            System.out.println("Erreur : un ou plusieurs champs texte sont null");
            return;
        }
        String firstName = tfFirstName.getText().trim();
        String lastName = tfLastName.getText().trim();
        String email = tfEmail.getText().trim();

        System.out.println("Valeurs saisies : " + firstName + ", " + lastName + ", " + email); // Débogage

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis !");
            return;
        }
        if (!isEmailAddress(email)) {
            showAlert("Erreur", "Adresse email invalide !");
            return;
        }

        Person newPerson = new Person(firstName, lastName, email);
        System.out.println("Ajout de : " + newPerson); // Débogage
        table.getItems().add(newPerson);
        System.out.println("Éléments dans la table : " + table.getItems()); // Débogage

        clearFields();
    }

    @FXML
    public void exporter() {
        data.setExportList(table.getItems());
    }

    @FXML
    public void importer() {
        table.getItems().clear();
        table.getItems().addAll(data.getImportList());
    }

    @FXML
    public void remove() {
        Person selectedPerson = table.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            table.getItems().remove(selectedPerson);
        } else {
            showAlert("Erreur", "Aucune ligne sélectionnée !");
        }
    }

    @FXML
    public void quitter() {
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        stage.close();
    }

    public static boolean isEmailAddress(String email) {
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
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