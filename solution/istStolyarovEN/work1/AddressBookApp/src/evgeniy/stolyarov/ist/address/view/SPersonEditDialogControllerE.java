package evgeniy.stolyarov.ist.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.controlsfx.dialog.Dialogs;

import evgeniy.stolyarov.ist.address.model.SPersonE;
import evgeniy.stolyarov.ist.address.util.SDateUtilE;

/**
 * Dialog to edit details of a person.
 *
 * @author Marco Jakob
 */
public class SPersonEditDialogControllerE {

    @FXML
    private TextField sFirstNameFielde;
    @FXML
    private TextField sLastNameFielde;
    @FXML
    private TextField sStreetFielde;
    @FXML
    private TextField sPostalCodeFielde;
    @FXML
    private TextField sCityFielde;
    @FXML
    private TextField sBirthdayFielde;


    private Stage sDialogStagee;
    private SPersonE sPersone;
    private boolean sOkClickede = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void sSetDialogStageE(Stage dialogStage) {
        this.sDialogStagee = dialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param person
     */
    public void sSetPersonE(SPersonE person) {
        this.sPersone = person;

        sFirstNameFielde.setText(person.getFirstName());
        sLastNameFielde.setText(person.getLastName());
        sStreetFielde.setText(person.getStreet());
        sPostalCodeFielde.setText(Integer.toString(person.getPostalCode()));
        sCityFielde.setText(person.getCity());
        sBirthdayFielde.setText(SDateUtilE.sFormate(person.getBirthday()));
        sBirthdayFielde.setPromptText("dd.mm.yyyy");
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean sIsOkClickedE() {
        return sOkClickede;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void sHandleOkE() {
        if (sIsInputValidE()) {
            sPersone.setFirstName(sFirstNameFielde.getText());
            sPersone.setLastName(sLastNameFielde.getText());
            sPersone.setStreet(sStreetFielde.getText());
            sPersone.setPostalCode(Integer.parseInt(sPostalCodeFielde.getText()));
            sPersone.setCity(sCityFielde.getText());
            sPersone.setBirthday(SDateUtilE.sParsee(sBirthdayFielde.getText()));

            sOkClickede = true;
            sDialogStagee.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void sHandleCancelE() {
        sDialogStagee.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean sIsInputValidE() {
        String sErrorMessagee = "";

        if (sFirstNameFielde.getText() == null || sFirstNameFielde.getText().length() == 0) {
            sErrorMessagee += "No valid first name!\n";
        }
        if (sLastNameFielde.getText() == null || sLastNameFielde.getText().length() == 0) {
            sErrorMessagee += "No valid last name!\n";
        }
        if (sStreetFielde.getText() == null || sStreetFielde.getText().length() == 0) {
            sErrorMessagee += "No valid street!\n";
        }

        if (sPostalCodeFielde.getText() == null || sPostalCodeFielde.getText().length() == 0) {
            sErrorMessagee += "No valid postal code!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(sPostalCodeFielde.getText());
            } catch (NumberFormatException e) {
                sErrorMessagee += "No valid postal code (must be an integer)!\n";
            }
        }

        if (sCityFielde.getText() == null || sCityFielde.getText().length() == 0) {
            sErrorMessagee += "No valid city!\n";
        }

        if (sBirthdayFielde.getText() == null || sBirthdayFielde.getText().length() == 0) {
            sErrorMessagee += "No valid birthday!\n";
        } else {
            if (!SDateUtilE.sValidDateE(sBirthdayFielde.getText())) {
                sErrorMessagee += "No valid birthday. Use the format dd.mm.yyyy!\n";
            }
        }

        if (sErrorMessagee.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Dialogs.create()
                    .title("Invalid Fields")
                    .masthead("Please correct invalid fields")
                    .message(sErrorMessagee)
                    .showError();
            return false;
        }
    }
}