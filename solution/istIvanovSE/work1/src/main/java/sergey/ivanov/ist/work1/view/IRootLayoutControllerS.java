package sergey.ivanov.ist.work1.view;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import sergey.ivanov.ist.work1.IMainAppS;

import java.io.File;

/**
 * Created by yukkasarasti on 22.04.15.
 */
public class IRootLayoutControllerS {

    private IMainAppS imainApps;

    public void setImainApps(IMainAppS imainApps) {
        this.imainApps = imainApps;
    }

    @FXML
    private void ihandleNewS() {
        imainApps.getiPersonDatas().clear();
        imainApps.setPersonFilePath(null);
    }

    @FXML
    private void ihandleOpenS() {
        FileChooser ifileChoosers = new FileChooser();
        FileChooser.ExtensionFilter iextensionFilters = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        ifileChoosers.getExtensionFilters().add(iextensionFilters);
        File ifiles = ifileChoosers.showOpenDialog(imainApps.getIprimaryStages());
        if (ifiles != null)
            imainApps.iloadPersonDataFromFile(ifiles);
    }

    @FXML
    private void ihandleSaveS() {
        File ipersonFiles = imainApps.getPersonFilePath();
        if (ipersonFiles != null)
            imainApps.isavePersonDataToFileS(ipersonFiles);
        else
            ihandleSaveAsS();
    }

    @FXML
    private void ihandleSaveAsS() {
        FileChooser ifileChoosers = new FileChooser();
        FileChooser.ExtensionFilter iextensionFilters = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        ifileChoosers.getExtensionFilters().add(iextensionFilters);
        File ifiles = ifileChoosers.showSaveDialog(imainApps.getIprimaryStages());
        if (ifiles != null) {
            if (!ifiles.getPath().endsWith(".xml"))
                ifiles = new File(ifiles.getPath() + ".xml");
            imainApps.isavePersonDataToFileS(ifiles);
        }
    }

    @FXML
    private void ihandleExitS() {
        System.exit(0);
    }
}
