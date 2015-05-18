package sergey.ivanov.ist.work1;/**
 * Created by yukkasarasti on 22.04.15.
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;
import sergey.ivanov.ist.work1.model.IPersonListWrapperS;
import sergey.ivanov.ist.work1.model.IPersonS;
import sergey.ivanov.ist.work1.view.IPersonEditDialogControllerS;
import sergey.ivanov.ist.work1.view.IPersonOverviewControllerS;
import sergey.ivanov.ist.work1.view.IRootLayoutControllerS;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class IMainAppS extends Application {

    private Stage iprimaryStages;
    private BorderPane irootLayouts;
    private ObservableList<IPersonS> iPersonDatas = FXCollections.observableArrayList();

    public IMainAppS() {
        // Add some sample data
        getiPersonDatas().add(new IPersonS("Hans", "Muster"));
        getiPersonDatas().add(new IPersonS("Ruth", "Mueller"));
        getiPersonDatas().add(new IPersonS("Heinz", "Kurz"));
        getiPersonDatas().add(new IPersonS("Cornelia", "Meier"));
        getiPersonDatas().add(new IPersonS("Werner", "Meyer"));
        getiPersonDatas().add(new IPersonS("Lydia", "Kunz"));
        getiPersonDatas().add(new IPersonS("Anna", "Best"));
        getiPersonDatas().add(new IPersonS("Stefan", "Meier"));
        getiPersonDatas().add(new IPersonS("Martin", "Mueller"));
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.iprimaryStages = primaryStage;
        this.iprimaryStages.getIcons().add(new Image("file:resources/images/address_book.png"));
        this.getIprimaryStages().setTitle("AddressApp");
        iinitRootLayoutS();
        ishowPersonOverviewS();
    }

    private void ishowPersonOverviewS() {
        try {
            FXMLLoader iloaders = new FXMLLoader();
            iloaders.setLocation(IMainAppS.class.getResource("view/PersonOverview.fxml"));
            AnchorPane ipersonOverviews = iloaders.load();
            irootLayouts.setCenter(ipersonOverviews);
            IPersonOverviewControllerS iPersonOverviewControllers = iloaders.getController();
            iPersonOverviewControllers.setImainApps(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void iinitRootLayoutS() {
        try {
            FXMLLoader iloaders = new FXMLLoader();
            iloaders.setLocation(IMainAppS.class.getResource("view/RootLayout.fxml"));
            irootLayouts = iloaders.load();
            Scene iscenes = new Scene(irootLayouts);
            getIprimaryStages().setScene(iscenes);
            IRootLayoutControllerS irootLayoutControllers = iloaders.getController();
            irootLayoutControllers.setImainApps(this);
            getIprimaryStages().show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File ifiles = getPersonFilePath();
        if (ifiles != null)
            iloadPersonDataFromFile(ifiles);
    }

    public Stage getIprimaryStages() {
        return iprimaryStages;
    }

    public ObservableList<IPersonS> getiPersonDatas() {
        return iPersonDatas;
    }

    public boolean ishowPersonEditDialog(IPersonS ipersons) {
        try {
            FXMLLoader iloaders = new FXMLLoader();
            iloaders.setLocation(IMainAppS.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane ipages = iloaders.load();
            Stage idialogStages = new Stage();
            idialogStages.setTitle("Edit Person");
            idialogStages.initModality(Modality.WINDOW_MODAL);
            idialogStages.initOwner(iprimaryStages);
            Scene iscenes = new Scene(ipages);
            idialogStages.setScene(iscenes);
            IPersonEditDialogControllerS icontrollers = iloaders.getController();
            icontrollers.setDialogStage(idialogStages);
            icontrollers.setIpersons(ipersons);
            idialogStages.showAndWait();
            return icontrollers.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(IMainAppS.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setPersonFilePath(File file) {
        Preferences iprefss = Preferences.userNodeForPackage(IMainAppS.class);
        if (file != null) {
            iprefss.put("filePath", file.getPath());

            // Update the stage title.
            iprimaryStages.setTitle("AddressApp - " + file.getName());
        } else {
            iprefss.remove("filePath");

            // Update the stage title.
            iprimaryStages.setTitle("AddressApp");
        }
    }

    public void iloadPersonDataFromFile(File file) {
        try {
            JAXBContext icontexts = JAXBContext.newInstance(IPersonListWrapperS.class);
            Unmarshaller iums = icontexts.createUnmarshaller();
            IPersonListWrapperS iwrappers = (IPersonListWrapperS) iums.unmarshal(file);
            iPersonDatas.clear();
            iPersonDatas.addAll(iwrappers.getIpersoness());
            setPersonFilePath(file);
        } catch (JAXBException e) {
            Dialogs.create()
                .title("Error")
                .masthead("Could not load data from file:\n" + file.getPath())
                .showException(e);
        }
    }

    public void isavePersonDataToFileS(File file) {
        try {
            JAXBContext icontexts = JAXBContext.newInstance(IPersonListWrapperS.class);
            Marshaller ims = icontexts.createMarshaller();
            ims.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            IPersonListWrapperS iwrappers = new IPersonListWrapperS();
            iwrappers.setIpersoness(iPersonDatas);
            ims.marshal(iwrappers, file);
        } catch (JAXBException e) {
            Dialogs.create().title("Error")
                .masthead("Could not save data to file:\n" + file.getPath())
                .showException(e);
        }
    }
}
