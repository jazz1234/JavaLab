package sergey.ivanov.ist.work1.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.controlsfx.dialog.Dialogs;
import sergey.ivanov.ist.work1.IMainAppS;
import sergey.ivanov.ist.work1.model.IPersonS;
import sergey.ivanov.ist.work1.util.IDateUtilS;

/**
 * Created by yukkasarasti on 22.04.15.
 */
public class IPersonOverviewControllerS {
    @FXML
    private TableView<IPersonS> ipersonTables;
    @FXML
    private TableColumn<IPersonS, String> ifirstNameColumns;
    @FXML
    private TableColumn<IPersonS, String> ilastNameColumns;

    @FXML
    private Label ifirstNameLabels;
    @FXML
    private Label ilastNameLabels;
    @FXML
    private Label istreetLabels;
    @FXML
    private Label ipostalCodeLabels;
    @FXML
    private Label icityLabels;
    @FXML
    private Label ibirthdayLabels;

    private IMainAppS imainApps;

    public IPersonOverviewControllerS() {

    }

    @FXML
    private void initialize() {
        ifirstNameColumns.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        ilastNameColumns.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        ishowPersonDetailsS(null);
        ipersonTables.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> ishowPersonDetailsS(newValue)
        );
    }

    @FXML
    private void ihandleDeletePersonS() {
        int iselectedIndexs = ipersonTables.getSelectionModel().getFocusedIndex();
        if (iselectedIndexs >= 0)
            ipersonTables.getItems().remove(iselectedIndexs);
        else
            Dialogs.create()
                .title("No Selection")
                .masthead("No Person Selected")
                .message("Please select a person in the table")
                .showWarning();
    }

    public void setImainApps(IMainAppS imainApps) {
        this.imainApps = imainApps;
        ipersonTables.setItems(imainApps.getiPersonDatas());
    }

    @FXML
    private void handleNewPerson() {
        IPersonS itempPersons = new IPersonS();
        boolean iokClickeds = imainApps.ishowPersonEditDialog(itempPersons);
        if (iokClickeds)
            imainApps.getiPersonDatas().add(itempPersons);
    }

    @FXML
    private void handleEditPerson() {
        IPersonS iselectedPersons = ipersonTables.getSelectionModel().getSelectedItem();
        if (iselectedPersons != null) {
            boolean iokClicked = imainApps.ishowPersonEditDialog(iselectedPersons);
            if (iokClicked)
                ishowPersonDetailsS(iselectedPersons);

        } else
            Dialogs.create()
                    .title("No Selection")
                    .masthead("No Person Selected")
                    .message("Please select a person in the table.")
                    .showWarning();
    }

    private void ishowPersonDetailsS(IPersonS ipersons) {
        if (ipersons != null) {
            ifirstNameLabels.setText(ipersons.getFirstName());
            ilastNameLabels.setText(ipersons.getLastName());
            istreetLabels.setText(ipersons.getStreet());
            ipostalCodeLabels.setText(String.valueOf(ipersons.getPostalCode()));
            icityLabels.setText(ipersons.getCity());
            ibirthdayLabels.setText(IDateUtilS.iformatS(ipersons.getBirthday()));
        } else {
            ifirstNameLabels.setText("");
            ilastNameLabels.setText("");
            istreetLabels.setText("");
            ipostalCodeLabels.setText("");
            icityLabels.setText("");
            ibirthdayLabels.setText("");
        }
    }
}
