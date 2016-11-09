package dcc.evaluation;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	private static Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("�ɿ���Ԥ������������");

		initRootLayout();
		showEvaluationOverview();

	}
	/**
	 * ��ʾ�������ߵĳ�ʼ����
	 */
	public void showEvaluationOverview() {

		try {
			//��fxml�ļ��м����������߳�ʼ����
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/EvaluationOverview.fxml"));
			AnchorPane evaluationOverview = (AnchorPane)loader.load();

			//���������ߵĳ�ʼ���������ڵײ㲼�ֵ�����
			rootLayout.setCenter(evaluationOverview);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ��ʼ���ײ㲼��
	 */
	public void initRootLayout() {

		try {
			// ��fxml�ļ��м��صײ㲼��
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			//��ʾ���еײ㲼�ֵĳ���
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
	 *���½���Ŀ����
	 *
	 */
	public static void showNewTaskOverview(){

		try {
			//��fxml�ļ��м����½���Ŀ�Ի�
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/NewTaskOverview.fxml"));
			AnchorPane newTasksOverview = (AnchorPane)loader.load();

			//�����½���Ŀ�Ի���

			Stage dialogStage = new Stage();
			dialogStage.setTitle("�½���Ŀ");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(newTasksOverview);
			dialogStage.setScene(scene);

			//չʾ�½���Ŀ�Ի���ֱ���û��ر���
			dialogStage.showAndWait();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 *����ʷ��Ŀ����
	 *
	 */
	public static void showHistoricTasksOverview(){
		try {
			//��fxml�ļ��м����½���Ŀ�Ի�
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/HistoricTasksOverview.fxml"));
			AnchorPane HistoricTasksOverview = (AnchorPane)loader.load();

			//�����½���Ŀ�Ի���

			Stage dialogStage = new Stage();
			dialogStage.setTitle("��ʷ��Ŀ");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(HistoricTasksOverview);
			dialogStage.setScene(scene);

			//չʾ�½���Ŀ�Ի���ֱ���û��ر���
			dialogStage.showAndWait();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ����������
	 * @return
	 */
	public Stage getPrimaryStage(){
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
