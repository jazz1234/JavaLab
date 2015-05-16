package evgeniy.stolyarov.ist.address.view;

import evgeniy.stolyarov.ist.address.SMainAppE;
import evgeniy.stolyarov.ist.address.util.SDateUtilE;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import evgeniy.stolyarov.ist.address.SMainAppE;
import evgeniy.stolyarov.ist.address.model.SPersonE;
import org.controlsfx.dialog.Dialogs;

public class SPersonOverviewControllerE {
    @FXML
    private TableView<SPersonE> sPersonTablee;
    @FXML
    private TableColumn<SPersonE, String> sFirstNameColumne;
    @FXML
    private TableColumn<SPersonE, String> sLastNameColumne;

    @FXML
    private Label sFirstNameLabele;
    @FXML
    private Label sLastNameLabele;
    @FXML
    private Label sStreetLabele;
    @FXML
    private Label sPostalCodeLabele;
    @FXML
    private Label sCityLabele;
    @FXML
    private Label sBirthdayLabele;

    // Reference to the main application.
    private SMainAppE sMainAppe;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public SPersonOverviewControllerE() {

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        sFirstNameColumne.setCellValueFactory(
                cellData -> cellData.getValue().firstNameProperty());
        sLastNameColumne.setCellValueFactory(
                cellData -> cellData.getValue().lastNameProperty());

        // Clear person details.
        sShowPersonDetailse(null);

        // Listen for selection changes and show the person details when changed.
        sPersonTablee.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> sShowPersonDetailse(newValue));
    }

    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     *
     * @param person the person or null
     */
    private void sShowPersonDetailse(SPersonE person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            sFirstNameLabele.setText(person.getFirstName());
            sLastNameLabele.setText(person.getLastName());
            sStreetLabele.setText(person.getStreet());
            sPostalCodeLabele.setText(Integer.toString(person.getPostalCode()));
            sCityLabele.setText(person.getCity());


            sBirthdayLabele.setText(SDateUtilE.sFormate(person.getBirthday()));
        } else {
            // Person is null, remove all the text.
            sFirstNameLabele.setText("");
            sLastNameLabele.setText("");
            sStreetLabele.setText("");
            sPostalCodeLabele.setText("");
            sCityLabele.setText("");
            sBirthdayLabele.setText("");
        }
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void sHandleDeletePersone() {
        int selectedIndex = sPersonTablee.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            sPersonTablee.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Dialogs.create()
                    .title("No Selection")
                    .masthead("No Person Selected")
                    .message("Please select a person in the table.")
                    .showWarning();
        }
    }
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void sHandleNewPersonE() {
        SPersonE sTempPersone = new SPersonE();
        boolean sOkClickede = sMainAppe.sShowPersonEditDialogE(sTempPersone);
        if (sOkClickede) {
            sMainAppe.sGetPersonData().add(sTempPersone);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void sHandleEditPersonE() {
        SPersonE sSelectedPersone = sPersonTablee.getSelectionModel().getSelectedItem();
        if (sSelectedPersone != null) {
            boolean sOkClickede = sMainAppe.sShowPersonEditDialogE(sSelectedPersone);
            if (sOkClickede) {
                sShowPersonDetailse(sSelectedPersone);
            }

        } else {
            // Nothing selected.
            Dialogs.create()
                    .title("No Selection")
                    .masthead("No Person Selected")
                    .message("Please select a person in the table.")
                    .showWarning();
        }
    }

    public void setMainApp(SMainAppE mainApp) {
        this.sMainAppe = mainApp;

        // Add observable list data to the table
        sPersonTablee.setItems(mainApp.sGetPersonData());
    }
    public void sSetItemsE(ObservableList<SPersonE> personsList) {
        sPersonTablee.setItems(personsList);
    }
}