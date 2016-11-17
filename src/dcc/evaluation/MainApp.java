package dcc.evaluation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

import dcc.evaluation.computation.model.DefectAmount;
import dcc.evaluation.view.InputFileController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	private static Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("可靠性预测与评估工具");

		initRootLayout();
		showEvaluationOverview();

	}
	/**
	 * 显示评估工具的初始界面
	 */
	public void showEvaluationOverview() {

		try {
			//从fxml文件中加载评估工具初始布局
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/EvaluationOverview.fxml"));
			AnchorPane evaluationOverview = (AnchorPane)loader.load();

			//将评估工具的初始布局设置在底层布局的中心
			rootLayout.setCenter(evaluationOverview);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 初始化底层布局
	 */
	public void initRootLayout() {

		try {
			// 从fxml文件中加载底层布局
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			//显示带有底层布局的场景
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			//scene.getStylesheets().add(MainApp.class.getResource("view/EvaluationOverview.css").toExternalForm());
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 打开新建项目界面
	 *
	 */
	public static void showNewTaskOverview(){

		try {
			//从fxml文件中加载新建项目对话
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/NewTaskOverview.fxml"));
			AnchorPane newTasksOverview = (AnchorPane)loader.load();

			//创建新建项目对话框

			Stage dialogStage = new Stage();
			dialogStage.setTitle("新建项目");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(newTasksOverview);
			dialogStage.setScene(scene);

			//展示新建项目对话框直到用户关闭它
			dialogStage.showAndWait();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 *打开历史项目界面
	 *
	 */
	public static void showHistoricTasksOverview(){
		try {
			//从fxml文件中加载新建项目对话
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/HistoricTasksOverview.fxml"));
			AnchorPane HistoricTasksOverview = (AnchorPane)loader.load();

			//创建新建项目对话框

			Stage dialogStage = new Stage();
			dialogStage.setTitle("历史项目");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(HistoricTasksOverview);
			dialogStage.setScene(scene);

			//展示新建项目对话框直到用户关闭它
			dialogStage.showAndWait();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 *打开导入文件框
	 * @throws FileFormatException 
	 * @throws FileNotFoundException 
	 *
	 */
	public static void showFileChooserOverview() throws FileNotFoundException, FileFormatException{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("选择导入文件");
		File file = fileChooser.showOpenDialog(primaryStage);
		if(file != null){
			InputFileController ifc = new InputFileController();
			ifc.readExcel(file.getAbsolutePath());
		}
	}
	

	
	
	
	/**
	 * 返回主场景
	 * @return
	 */
	public Stage getPrimaryStage(){
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
