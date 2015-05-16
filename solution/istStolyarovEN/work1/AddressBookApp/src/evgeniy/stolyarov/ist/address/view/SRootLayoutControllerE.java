package evgeniy.stolyarov.ist.address.view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import org.controlsfx.dialog.Dialogs;

import evgeniy.stolyarov.ist.address.SMainAppE;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 *
 * @author Marco Jakob
 */
public class SRootLayoutControllerE {

    // Reference to the main application
    private SMainAppE sMainAppe;

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(SMainAppE mainApp) {
        this.sMainAppe = mainApp;
    }

    /**
     * Creates an empty address book.
     */
    @FXML
    private void sHandleNewE() {
        sMainAppe.sGetPersonData().clear();
        sMainAppe.sSetPersonFilePathE(null);
    }

    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void sHandleOpenE() {
        FileChooser sFileChoosere = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter sExtFiltere = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        sFileChoosere.getExtensionFilters().add(sExtFiltere);

        // Show save file dialog
        File sFilee = sFileChoosere.showOpenDialog(sMainAppe.getPrimaryStage());

        if (sFilee != null) {
            sMainAppe.sLoadPersonDataFromFileE(sFilee);
        }
    }

    /**
     * Saves the file to the person file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void sHandleSavee() {
        File sPersonFilee = sMainAppe.getPersonFilePath();
        if (sPersonFilee != null) {
            sMainAppe.sSavePersonDataToFileE(sPersonFilee);
        } else {
            sHandleSaveAsE();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void sHandleSaveAsE() {
        FileChooser sFileChoosere = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter sExtFiltere = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        sFileChoosere.getExtensionFilters().add(sExtFiltere);

        // Show save file dialog
        File sFilee = sFileChoosere.showSaveDialog(sMainAppe.getPrimaryStage());

        if (sFilee != null) {
            // Make sure it has the correct extension
            if (!sFilee.getPath().endsWith(".xml")) {
                sFilee = new File(sFilee.getPath() + ".xml");
            }
            sMainAppe.sSavePersonDataToFileE(sFilee);
        }
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void sHandleAboutE() {
        Dialogs.create()
                .title("AddressApp")
                .masthead("About")
                .message("Author: Stolyarov Evgeniy. IST-D-2. https://github.com/RickKross")
                .showInformation();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void sHandleExitE() {
        System.exit(0);
    }

    /**
     * Opens the birthday statistics.
     */
    @FXML
    private void sHandleShowBirthdayStatisticsE() {
        sMainAppe.sShowBirthdayStatisticsE();
    }
}