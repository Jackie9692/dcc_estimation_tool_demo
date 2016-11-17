package dcc.evaluation.view;

import dcc.evaluation.MainApp;
import javafx.fxml.FXML;
import javafx.stage.Window;

public class EvaluationOverviewController {
	// 引用主程序
//	private MainApp mainApp;
//	private Window primaryStage;

	/**
	 * 构造方法
	 */
	public EvaluationOverviewController() {

	}

	/**
	 * 当用户点击新建项目按钮时，打开新建项目对话框以新建项目
	 */
	@FXML
	private void HandleNewTask() {
		 MainApp.showNewTaskOverview();


//		 try {
//		 //��fxml�ļ��м����½���Ŀ�Ի�
//		 FXMLLoader loader = new FXMLLoader();
//		 loader.setLocation(MainApp.class.getResource("view/NewTaskOverview.fxml"));
//		 AnchorPane newTasksOverview = (AnchorPane)loader.load();
//
//		 //�����½���Ŀ�Ի���
//
//		 Stage dialogStage = new Stage();
//		 dialogStage.setTitle("�½���Ŀ");
//		 dialogStage.initModality(Modality.WINDOW_MODAL);
//		 //dialogStage.initOwner(primaryStage);
//		 Scene scene = new Scene(newTasksOverview);
//		 dialogStage.setScene(scene);
//
//		 //չʾ�½���Ŀ�Ի���ֱ���û��ر���
//		 dialogStage.showAndWait();
//
//
//		 } catch (IOException e) {
//		 // TODO Auto-generated catch block
//		 e.printStackTrace();
//		 }

	}
		@FXML
		private void HandleHistoricTasks(){
			MainApp.showHistoricTasksOverview();
		}

//	/**
//	 * ��������������������Լ�
//	 *
//	 * @param mainApp
//	 */
//	public void setMainApp(MainApp mainApp) {
//		this.mainApp = mainApp;
//	}

}
