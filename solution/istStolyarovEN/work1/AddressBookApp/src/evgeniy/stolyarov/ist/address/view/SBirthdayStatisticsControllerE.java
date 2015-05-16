package evgeniy.stolyarov.ist.address.view;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import evgeniy.stolyarov.ist.address.model.SPersonE;

/**
 * The controller for the birthday statistics view.
 *
 * @author Marco Jakob
 */
public class SBirthdayStatisticsControllerE {

    @FXML
    private BarChart<String, Integer> sBarCharte;

    @FXML
    private CategoryAxis xAxis;

    private ObservableList<String> sMonthNamese = FXCollections.observableArrayList();

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Get an array with the English month names.
        String[] sMonthse = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        // Convert it to a list and add it to our ObservableList of months.
        sMonthNamese.addAll(Arrays.asList(sMonthse));

        // Assign the month names as categories for the horizontal axis.
        xAxis.setCategories(sMonthNamese);
    }

    /**
     * Sets the persons to show the statistics for.
     *
     * @param sPersonse
     */
    public void setPersonData(List<SPersonE> sPersonse) {
        // Count the number of people having their birthday in a specific month.
        int[] sMonthCountere = new int[12];
        for (SPersonE p : sPersonse) {
            int sMonthe = p.getBirthday().getMonthValue() - 1;
            sMonthCountere[sMonthe]++;
        }

        XYChart.Series<String, Integer> sSeriese = new XYChart.Series<>();

        // Create a XYChart.Data object for each month. Add it to the series.
        for (int i = 0; i < sMonthCountere.length; i++) {
            sSeriese.getData().add(new XYChart.Data<>(sMonthNamese.get(i), sMonthCountere[i]));
        }

        sBarCharte.getData().add(sSeriese);
    }
}