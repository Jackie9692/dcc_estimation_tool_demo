package dcc.evaluation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

import dcc.evaluation.view.NewTaskOverviewController;
import javafx.application.Application;
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
			dialogStage.setTitle("新建任务");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(newTasksOverview);
			dialogStage.setScene(scene);
			dialogStage.setResizable(false);

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
			dialogStage.setTitle("历史任务");
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
	 * 打开"开发过程的软件可靠性预测-开发过程可靠性预测"数据输入界面
	 */
	public static void showDevelopmentReliabilityPredictDataInputOverview(String name){
		try {
			//从fxml文件中加载开发过程可靠性预测数据输入界面
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/DevelopmentReliabilityPredictDataInputOverview.fxml"));
			AnchorPane DevelopmentReliabilityPredictDataInputOverview = (AnchorPane)loader.load();
			
			//创建开发过程可靠性预测数据输入界面
			Stage dialogStage = new Stage();
			dialogStage.setTitle(name);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(DevelopmentReliabilityPredictDataInputOverview);
			dialogStage.setScene(scene);
			dialogStage.setResizable(false);
			
			//展示开发过程可靠性预测数据输入界面直到用户关闭它
			dialogStage.showAndWait();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 打开"开发过程的软件可靠性预测-软件缺陷早期预测"数据输入界面
	 */
	public static void showSoftwareDefectPredictDataInputOverview(String name){
		try {
			//从fxml文件中加载软件缺陷早期预测数据输入界面
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SoftwareDefectPredictDataInputOverview.fxml"));
			AnchorPane SoftwareDefectPredictDataInputOverview = (AnchorPane)loader.load();
			
			//创建软件缺陷早期预测数据输入界面
			Stage dialogStage = new Stage();
			dialogStage.setTitle(name);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(SoftwareDefectPredictDataInputOverview);
			dialogStage.setScene(scene);
			dialogStage.setResizable(false);
			
			//展示软件缺陷早期预测数据输入界面直到用户关闭它
			dialogStage.showAndWait();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 打开"测试阶段软件可靠性评估-自动选择模型"数据输入界面
	 */
	public static void showDefaultDetermineModelDataInputOverview(String name){
		try {
			//从fxml文件中加载自动选择模型数据输入界面
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/DefaultDetermineModelDataInputOverview.fxml"));
			AnchorPane DefaultDetermineModelDataInputOverview = (AnchorPane)loader.load();
			
			//创建自动选择模型数据输入界面
			Stage dialogStage = new Stage();
			dialogStage.setTitle(name);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(DefaultDetermineModelDataInputOverview);
			dialogStage.setScene(scene);
			dialogStage.setResizable(false);
			
			//展示自动选择模型数据输入界面直到用户关闭它
			dialogStage.showAndWait();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 打开"测试阶段软件可靠性评估-自动选择模型"评估结果界面
	 */
	public static void showDefaultDetermineModelResultOverview(){
		try {
			//从fxml文件中加载自动选择模型评估结果界面
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/DefaultDetermineModelResultOverview.fxml"));
			AnchorPane DefaultDetermineModelResultOverview = (AnchorPane)loader.load();
			
			//创建自动选择模型评估结果界面
			Stage dialogStage = new Stage();
			dialogStage.setTitle("评估结果");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(DefaultDetermineModelResultOverview);
			dialogStage.setScene(scene);
			dialogStage.setResizable(false);
			
			//展示自动选择模型评估结果界面直到用户关闭它
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
//	public static void showFileChooserOverview() throws FileNotFoundException, FileFormatException{
//		
//		
//		FileChooser fileChooser = new FileChooser();
//		fileChooser.setTitle("选择导入文件");
//		File file = fileChooser.showOpenDialog(primaryStage);
//		if(file != null){
//			FXMLLoader loader = new FXMLLoader();
//			NewTaskOverviewController ntoc = new NewTaskOverviewController();
//					//loader.getController();
//			ntoc.readExcel(file.getAbsolutePath());
//		}
//	}

	
	
	
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
