package dcc.evaluation.view;



import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;



public class NewTaskOverviewController {
	//��������׶εİ�ť
	@FXML
	private RadioButton rbSystemType1;
	@FXML
	private RadioButton rbSystemType2;
	@FXML
	private RadioButton rbSystemType3;
	@FXML
	private RadioButton rbSystemType4;
	@FXML
	private RadioButton rbSystemType5;
	@FXML
	private RadioButton rbSystemType6;
	@FXML
	private RadioButton rbDevelopmentEnvironment1;
	@FXML
	private RadioButton rbDevelopmentEnvironment2;
	@FXML
	private RadioButton rbDevelopmentEnvironment3;
	
	//��ƽ׶εİ�ť
	//���������İ�ť
	@FXML
	private CheckBox cbQualityEvaluation1;
	@FXML
	private CheckBox cbQualityEvaluation2;
	@FXML
	private CheckBox cbQualityEvaluation3;
	@FXML
	private CheckBox cbQualityEvaluation4;
	@FXML
	private CheckBox cbQualityEvaluation5;
	@FXML
	private CheckBox cbQualityEvaluation6;
	@FXML
	private CheckBox cbQualityEvaluation7;
	@FXML
	private CheckBox cbQualityEvaluation8;
	@FXML
	private CheckBox cbQualityEvaluation9;
	@FXML
	private CheckBox cbQualityEvaluation10;
	@FXML
	private CheckBox cbQualityEvaluation11;
	@FXML
	private CheckBox cbQualityEvaluation12;
	@FXML
	private CheckBox cbQualityEvaluation13;
	//�쳣��������
	@FXML
	private CheckBox cbExceptionManagement1;
	@FXML
	private CheckBox cbExceptionManagement2;
	@FXML
	private CheckBox cbExceptionManagement3;
	@FXML
	private CheckBox cbExceptionManagement4;
	@FXML
	private CheckBox cbExceptionManagement5;
	@FXML
	private CheckBox cbExceptionManagement6;
	@FXML
	private CheckBox cbExceptionManagement7;
	@FXML
	private CheckBox cbExceptionManagement8;
	@FXML
	private CheckBox cbExceptionManagement9;
	@FXML
	private CheckBox cbExceptionManagement10;
	//����������
	@FXML
	private CheckBox cbLimbernessEvaluation1;
	@FXML
	private CheckBox cbLimbernessEvaluation2;
	@FXML
	private CheckBox cbLimbernessEvaluation3;
	@FXML
	private CheckBox cbLimbernessEvaluation4;
	

  
	//�������̵�����ɿ���Ԥ�������-�������̿ɿ��Է���-��������׶�
	//����ƽ�������ܶ�A
	double a;
	//���忪��������������������D
	double d;
	//��������׶εĴ����ܶ�
	double requirementAnalysisResult;
	//��������׶εĴ����ܶ�
	
	public double requirementAnalysis(){
		
		//ȷ��ƽ�������ܶ�A(����/��)
		if(rbSystemType1.isSelected()){
			a = 0.0128;
		}else if(rbSystemType2.isSelected()){
			a = 0.0092;
		}else if(rbSystemType3.isSelected()){
			a = 0.0078;
		}else if(rbSystemType4.isSelected()){
			a = 0.0018;
		}else if(rbSystemType5.isSelected()){
			a = 0.0085;
		}else if(rbSystemType6.isSelected()){
			a = 0.0123;
		}
		
		//����������������������D
		if(rbDevelopmentEnvironment1.isSelected()){
			d = 0.76;
		}else if(rbDevelopmentEnvironment2.isSelected()){
			d = 1.0;
		}else if(rbDevelopmentEnvironment3.isSelected()){
			d = 1.3;
		}
		
		//���ؽ��������׶εĴ����ܶ� 	
		return requirementAnalysisResult = a*d;
		
	}
	
	//�������̵�����ɿ���Ԥ�������-�������̿ɿ��Է���-��ƽ׶�
	//��������������ѡ���б�ѡ�е�ѡ�����
	int qe = 0;
	//�����쳣����������ѡ���б�ѡ�е�ѡ�����
	int em = 0;
	//���������������ѡ���б�ѡ�е�ѡ�����
	int le = 0;
	//��ʼ����������SQ��ֵ
	double SQ = 1.0;
	//��ʼ����������SA��ֵ
	double SA = 0.9;
	//��ʼ������������ST��ֵ
	double ST = 1.1;
	//������ƽ׶ζ����ʧЧ��Ԥ����������� D
	double D;
	//���������ƽ׶εĴ����ܶ�
	double designPhaseResult;
	
	//��������׶εĴ����ܶ�
	public double designPhase(){
		
		ArrayList<CheckBox> qualityEvaluation = new ArrayList<CheckBox>();
		qualityEvaluation.add(cbQualityEvaluation1);
		qualityEvaluation.add(cbQualityEvaluation2);
		qualityEvaluation.add(cbQualityEvaluation3);
		qualityEvaluation.add(cbQualityEvaluation4);
		qualityEvaluation.add(cbQualityEvaluation5);
		qualityEvaluation.add(cbQualityEvaluation6);
		qualityEvaluation.add(cbQualityEvaluation7);
		qualityEvaluation.add(cbQualityEvaluation8);
		qualityEvaluation.add(cbQualityEvaluation9);
		qualityEvaluation.add(cbQualityEvaluation10);
		qualityEvaluation.add(cbQualityEvaluation11);
		qualityEvaluation.add(cbQualityEvaluation12);
		qualityEvaluation.add(cbQualityEvaluation13);
		for(CheckBox cb : qualityEvaluation){
			if(cb.isSelected()){
				qe++;
			}
		}
		
		
		if(qe<6){
			SQ = 1.1;
		}
		
		
		ArrayList<CheckBox> exceptionManagement = new ArrayList<CheckBox>();
		exceptionManagement.add(cbExceptionManagement1);
		exceptionManagement.add(cbExceptionManagement2);
		exceptionManagement.add(cbExceptionManagement3);
		exceptionManagement.add(cbExceptionManagement4);
		exceptionManagement.add(cbExceptionManagement5);
		exceptionManagement.add(cbExceptionManagement6);
		exceptionManagement.add(cbExceptionManagement7);
		exceptionManagement.add(cbExceptionManagement8);
		exceptionManagement.add(cbExceptionManagement9);
		exceptionManagement.add(cbExceptionManagement10);
		for(CheckBox cb : exceptionManagement){
			if(cb.isSelected()){
				em++;
			}
		}
		
		
		if(em == 3){
			SA = 1;
		}else if(em > 3){
			SA = 1.1;
		}
		
		
		ArrayList<CheckBox> limbernessEvaluation = new ArrayList<CheckBox>();
		limbernessEvaluation.add(cbLimbernessEvaluation1);
		limbernessEvaluation.add(cbLimbernessEvaluation2);
		limbernessEvaluation.add(cbLimbernessEvaluation3);
		limbernessEvaluation.add(cbLimbernessEvaluation4);
		for(CheckBox cb : limbernessEvaluation){
			if(cb.isSelected()){
				le++;
			}
		}
		
		
		if(le ==4){
			ST = 1.0;
		}
		
		//�����ƽ׶ζ����ʧЧ��Ԥ����������� D
		D = SA*ST*SQ ;
		
		//���ؽ���������ƽ׶εĴ����ܶ�
		
		
		return designPhaseResult = D*requirementAnalysisResult;
		
		
		
	}
	

	@FXML
	private void evaluateIsClicked(){
		System.out.println(requirementAnalysis());
		System.out.println(designPhase());
	}
	
	
	
	

	
}
