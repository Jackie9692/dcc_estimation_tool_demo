package dcc.evaluation.view;

import dcc.evaluation.view.model.DefectAmount;
import dcc.evaluation.view.model.ImportFileActions;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class DefaultDetermineModelResultOverviewController extends ImportFileActions {
	// 失效数据表格
	@FXML
	private TableView<DefectAmount> testCompleteFailureTable;
	@FXML
	private TableColumn<DefectAmount, String> testFailureTimesColumn;
	@FXML
	private TableColumn<DefectAmount, String> testTimeBetweenFailuresColumn;

	@FXML
	private void initialize() {
		testCompleteFailureTable.setItems(defectData);

		testFailureTimesColumn
				.setCellValueFactory(new Callback<CellDataFeatures<DefectAmount, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<DefectAmount, String> p) {
						// p.getValue() returns the Person instance for a
						// particular TableView row
						return p.getValue().mounthProperty();
					}
				});

		testTimeBetweenFailuresColumn
				.setCellValueFactory(new Callback<CellDataFeatures<DefectAmount, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<DefectAmount, String> p) {
						// p.getValue() returns the Person instance for a
						// particular TableView row
						return p.getValue().defectAmountProperty();
					}
				});

	}
}
