package dcc.evaluation.view;

import dcc.evaluation.view.model.DefectAmount;
import dcc.evaluation.view.model.ImportFileActions;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
	private NumberAxis xAxis;
	@FXML
	private NumberAxis yAxis;
	@FXML
	private LineChart<Number,Number> lineChart;

	@FXML
	private void initialize() {
		testCompleteFailureTable.setItems(defectData);

		testFailureTimesColumn
				.setCellValueFactory(new Callback<CellDataFeatures<DefectAmount, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<DefectAmount, String> p) {
						return p.getValue().mounthProperty();
					}
				});

		testTimeBetweenFailuresColumn
				.setCellValueFactory(new Callback<CellDataFeatures<DefectAmount, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<DefectAmount, String> p) {
						return p.getValue().defectAmountProperty();
					}
				});
		
		
		
//		 //defining the axes
// //       final NumberAxis xAxis = new NumberAxis();
// //       final NumberAxis yAxis = new NumberAxis();
//        xAxis.setLabel("时间");
//        yAxis.setLabel("故障数");
//        //creating the chart
//        final LineChart<Number,Number> lineChart = 
//                new LineChart<Number,Number>(xAxis,yAxis);
                
        lineChart.setTitle("可靠性曲线");
        //lineChart.setCreateSymbols(false);
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("实际曲线");
        //populating the series with data
//        series.getData().add(new XYChart.Data(1, 23));
//        series.getData().add(new XYChart.Data(2, 14));
//        series.getData().add(new XYChart.Data(3, 15));
//        series.getData().add(new XYChart.Data(4, 24));
//        series.getData().add(new XYChart.Data(5, 34));
//        series.getData().add(new XYChart.Data(6, 36));
//        series.getData().add(new XYChart.Data(7, 22));
//        series.getData().add(new XYChart.Data(8, 45));
//        series.getData().add(new XYChart.Data(9, 43));
//        series.getData().add(new XYChart.Data(10, 17));
//        series.getData().add(new XYChart.Data(11, 29));
//        series.getData().add(new XYChart.Data(12, 25));
        series.getData().add(new XYChart.Data(0, 0));
        int times = 1;
        double failureTime = 0;
        
        for(String s: al){
        	failureTime += Double.parseDouble(s);
        	 series.getData().add(new XYChart.Data(failureTime, times));
        	 times++;
        }
        //System.out.println(times);
        
        XYChart.Series series2 = new XYChart.Series();
        series2.getData().add(new XYChart.Data(0, 0));
        double simulationFailureTime = 0;
        
        for(int x = 1;x<times;x++){
        	simulationFailureTime += simulationResult(x);
        	 series2.getData().add(new XYChart.Data(simulationFailureTime, x));
        }
        series2.setName("拟合曲线");
        
        
        
        lineChart.getData().addAll(series,series2);

	}
	
	private double simulationResult(int times){
		double phi = 0.00684937486957505;
		double N = 31.215867430291837;
		double halfResult = phi*(N-times+1);
		double result = 1/halfResult;
		return result;
	}
}
