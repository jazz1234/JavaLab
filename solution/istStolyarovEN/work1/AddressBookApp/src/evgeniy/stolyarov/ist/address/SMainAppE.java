package evgeniy.stolyarov.ist.address;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import evgeniy.stolyarov.ist.address.model.SPersonE;
import evgeniy.stolyarov.ist.address.model.SPersonListWrapperE;
import evgeniy.stolyarov.ist.address.view.SBirthdayStatisticsControllerE;
import evgeniy.stolyarov.ist.address.view.SPersonEditDialogControllerE;
import evgeniy.stolyarov.ist.address.view.SPersonOverviewControllerE;
import evgeniy.stolyarov.ist.address.view.SRootLayoutControllerE;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class SMainAppE extends Application {

    private Stage sPrimaryStagee;
    private BorderPane sRootLayoute;

    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<SPersonE> sPersonDatae = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public SMainAppE() {
        // Add some sample data
        sPersonDatae.add(new SPersonE("Hans", "Muster"));
        sPersonDatae.add(new SPersonE("Ruth", "Mueller"));
        sPersonDatae.add(new SPersonE("Heinz", "Kurz"));
        sPersonDatae.add(new SPersonE("Cornelia", "Meier"));
        sPersonDatae.add(new SPersonE("Werner", "Meyer"));
        sPersonDatae.add(new SPersonE("Lydia", "Kunz"));
        sPersonDatae.add(new SPersonE("Anna", "Best"));
        sPersonDatae.add(new SPersonE("Stefan", "Meier"));
        sPersonDatae.add(new SPersonE("Martin", "Mueller"));
    }

    /**
     * Returns the data as an observable list of Persons.
     * @return
     */
    public ObservableList<SPersonE> sGetPersonData() {
        return sPersonDatae;
    }


    @Override
    public void start(Stage primaryStage) {
        this.sPrimaryStagee = primaryStage;
        this.sPrimaryStagee.setTitle("AddressApp");
        this.sPrimaryStagee.getIcons().add(new Image("file:resources/images/address_book_32.png"));

        sInitRootLayoutE();

        sShowPersonOverviewE();
    }

    /**
     * Initializes the root layout.
     */
    public void sInitRootLayoutE() {
        try {
            // Load root layout from fxml file.
            FXMLLoader sLoadere = new FXMLLoader();
            sLoadere.setLocation(SMainAppE.class.getResource("view/SRootLayoutE.fxml"));
            sRootLayoute = (BorderPane) sLoadere.load();

            // Show the scene containing the root layout.
            sPrimaryStagee.setScene(new Scene(sRootLayoute));

            // Give the controller access to the main app.
            SRootLayoutControllerE sControllere = sLoadere.getController();
            sControllere.setMainApp(this);

            sPrimaryStagee.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened person file.
        File file = getPersonFilePath();
        if (file != null) {
            sLoadPersonDataFromFileE(file);
        }
    }


    /**
     * Shows the person overview inside the root layout.
     */
    public void sShowPersonOverviewE() {
        try {
            // Load person overview.
            FXMLLoader sLoadere = new FXMLLoader();
            sLoadere.setLocation(SMainAppE.class.getResource("view/SPersonOverviewE.fxml"));
            AnchorPane sPersonOverviewe = (AnchorPane) sLoadere.load();

            // Set person overview into the center of root layout.
            sRootLayoute.setCenter(sPersonOverviewe);

            // Give the controller access to the main app.
            SPersonOverviewControllerE sControllere = sLoadere.getController();
            sControllere.setMainApp(this);        ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @param sPersone the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean sShowPersonEditDialogE(SPersonE sPersone) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader sLoadere = new FXMLLoader();
            sLoadere.setLocation(SMainAppE.class.getResource("view/SPersonEditDialogE.fxml"));
            AnchorPane sPagee = (AnchorPane) sLoadere.load();

            // Create the dialog Stage.
            Stage sDialogStagee = new Stage();
            sDialogStagee.setTitle("Edit Person");
            sDialogStagee.initModality(Modality.WINDOW_MODAL);
            sDialogStagee.initOwner(sPrimaryStagee);
            sDialogStagee.setScene(new Scene(sPagee));

            // Set the person into the controller.
            SPersonEditDialogControllerE controller = sLoadere.getController();
            controller.sSetDialogStageE(sDialogStagee);
            controller.sSetPersonE(sPersone);

            // Show the dialog and wait until the user closes it
            sDialogStagee.showAndWait();

            return controller.sIsOkClickedE();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Opens a dialog to show birthday statistics.
     */
    public void sShowBirthdayStatisticsE() {
        try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader sLoadere = new FXMLLoader();
            sLoadere.setLocation(SMainAppE.class.getResource("view/SBirthdayStatisticsE.fxml"));
            AnchorPane page = (AnchorPane) sLoadere.load();
            Stage sDialogStagee = new Stage();
            sDialogStagee.setTitle("Birthday Statistics");
            sDialogStagee.initModality(Modality.WINDOW_MODAL);
            sDialogStagee.initOwner(sPrimaryStagee);
            sDialogStagee.setScene(new Scene(page));

            // Set the persons into the controller.
            SBirthdayStatisticsControllerE sControllere = sLoadere.getController();
            sControllere.setPersonData(sPersonDatae);

            sDialogStagee.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     * @return
     */
    public File getPersonFilePath() {
        Preferences sPrefse = Preferences.userNodeForPackage(SMainAppE.class);
        String sFilePathe = sPrefse.get("filePath", null);
        if (sFilePathe != null) {
            return new File(sFilePathe);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     *
     * @param sFilee the file or null to remove the path
     */
    public void sSetPersonFilePathE(File sFilee) {
        Preferences sPrefse = Preferences.userNodeForPackage(SMainAppE.class);
        if (sFilee != null) {
            sPrefse.put("filePath", sFilee.getPath());

            // Update the stage title.
            sPrimaryStagee.setTitle("AddressApp - " + sFilee.getName());
        } else {
            sPrefse.remove("filePath");

            // Update the stage title.
            sPrimaryStagee.setTitle("AddressApp");
        }
    }

    /**
     * Loads person data from the specified file. The current person data will
     * be replaced.
     *
     * @param sFilee
     */
    public void sLoadPersonDataFromFileE(File sFilee) {
        try {
            JAXBContext context = JAXBContext.newInstance(SPersonListWrapperE.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            SPersonListWrapperE sWrappere = (SPersonListWrapperE) um.unmarshal(sFilee);

            sPersonDatae.clear();
            sPersonDatae.addAll(sWrappere.getPersons());

            // Save the file path to the registry.
            sSetPersonFilePathE(sFilee);

        } catch (Exception e) { // catches ANY exception
            Dialogs.create()
                    .title("Error")
                    .masthead("Could not load data from file:\n" + sFilee.getPath())
                    .showException(e);
        }
    }

    /**
     * Saves the current person data to the specified file.
     *
     * @param sFilee
     */
    public void sSavePersonDataToFileE(File sFilee) {
        try {
            JAXBContext context = JAXBContext.newInstance(SPersonListWrapperE.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            SPersonListWrapperE sWrappere = new SPersonListWrapperE();
            sWrappere.setPersons(sPersonDatae);

            // Marshalling and saving XML to the file.
            m.marshal(sWrappere, sFilee);

            // Save the file path to the registry.
            sSetPersonFilePathE(sFilee);
        } catch (Exception e) { // catches ANY exception
            Dialogs.create().title("Error")
                    .masthead("Could not save data to file:\n" + sFilee.getPath())
                    .showException(e);
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return sPrimaryStagee;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
