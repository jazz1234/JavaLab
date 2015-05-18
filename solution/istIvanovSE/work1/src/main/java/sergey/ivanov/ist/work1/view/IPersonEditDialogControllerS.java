package sergey.ivanov.ist.work1.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;
import sergey.ivanov.ist.work1.model.IPersonS;
import sergey.ivanov.ist.work1.util.IDateUtilS;

/**
 * Created by yukkasarasti on 22.04.15.
 */
public class IPersonEditDialogControllerS {

    @FXML
    private TextField ifirstNameFields;
    @FXML
    private TextField ilastNameFields;
    @FXML
    private TextField istreetFields;
    @FXML
    private TextField ipostalCodeFields;
    @FXML
    private TextField icityFields;
    @FXML
    private TextField ibirthdayFields;

    private Stage idialogStages;
    private IPersonS ipersons;
    private boolean iokClickeds;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.idialogStages = dialogStage;
    }


    public void setIpersons(IPersonS ipersons) {
        this.ipersons = ipersons;
        ifirstNameFields.setText(ipersons.getFirstName());
        ilastNameFields.setText(ipersons.getLastName());
        istreetFields.setText(ipersons.getStreet());
        ipostalCodeFields.setText(String.valueOf(ipersons.getPostalCode()));
        icityFields.setText(ipersons.getCity());
        ibirthdayFields.setPromptText("dd.mm.yyyy");
    }

    public boolean isOkClicked() {
        return iokClickeds;
    }

    @FXML
    private void ihandleOkS() {
        if (isInputValid()) {
            ipersons.setFirstName(ifirstNameFields.getText());
            ipersons.setLastName(ilastNameFields.getText());
            ipersons.setStreet(istreetFields.getText());
            ipersons.setPostalCode(Integer.parseInt(ipostalCodeFields.getText()));
            ipersons.setCity(icityFields.getText());
            ipersons.setBirthday(IDateUtilS.iparseS(ibirthdayFields.getText()));
            iokClickeds = true;
            idialogStages.close();
        }
    }

    @FXML
    private void ihandleCancelS() {
        idialogStages.close();
    }

    private boolean isInputValid() {
        String ierrorMessages = "";
        if (ifirstNameFields.getText() == null || ifirstNameFields.getText().length() == 0)
            ierrorMessages += "No valid first name!\n";
        if (ilastNameFields.getText() == null || ilastNameFields.getText().length() == 0)
            ierrorMessages += "No valid last name!\n";
        if (istreetFields.getText() == null || istreetFields.getText().length() == 0)
            ierrorMessages += "No valid street!\n";
        if (ipostalCodeFields.getText() == null || ipostalCodeFields.getText().length() == 0)
            ierrorMessages += "No valid postal code!\n";
        else
            try {
                Integer.parseInt(ipostalCodeFields.getText());
            } catch (NumberFormatException e) {
                ierrorMessages += "No valid postal code (must be an integer)!\n";
            }
        if (icityFields.getText() == null || icityFields.getText().length() == 0)
            ierrorMessages += "No valid city!\n";
        if (ibirthdayFields.getText() == null || ibirthdayFields.getText().length() == 0)
            ierrorMessages += "No valid birthday!\n";
        else
            if (!IDateUtilS.ivalidDateS(ibirthdayFields.getText()))
                ierrorMessages += "No valid birthday. Use the format dd.mm.yyyy!\n";
        if (ierrorMessages.length() == 0)
            return true;
        else {
            Dialogs.create()
                    .title("Invalid Fields")
                    .masthead("Please correct invalid fields")
                    .message(ierrorMessages)
                    .showError();
            return false;
        }
    }
}
