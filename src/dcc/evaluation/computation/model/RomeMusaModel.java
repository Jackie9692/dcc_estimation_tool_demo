package dcc.evaluation.computation.model;

import java.text.DecimalFormat;

/**
 * 
 * Rome实验室模型和Musa模型
 *
 */
public class RomeMusaModel {
	
	public RomeMusaModel() {
		
	}
	
	private double[] A = new double[] { 0.0018, 0.0085, 0.0123, 0.0092 };//平均错误密度(错误/行)
	private double[] D = new double[] { 0.76, 1.0, 1.3 };//开发环境决定的修正因子对照表
	private double[] SA = new double[] { 0.9, 1.0, 1.1 };//异常管理评估标准
	private double[] ST = new double[] { 1, 1.1 };//可塑性评估标准
	private double[] SQ = new double[] { 1.0, 1.1 };//质量评估标准
//	private int [] Putnam = new int[10];//Putnam开发里程碑
	
	private double K = 4.2e-7;//模型系数，通过历史经验数据的统计分析得出k=4.2e-7
	private double ER = 4;//代码扩展比率-由编程语言确定，默认均值为4
	
	/**
	 * 
	 * @param i：参数A（平均错误密度(错误/行)）的下标
	 * @param j：参数D（开发环境决定的修正因子对照表）的下标
	 * @return 需求阶段的缺陷密度(错误/行)
	 */
	public double predictRequirement(int i, int j){
		return A[i]*D[j];
	}
	
	/**
	 * 
	 * @param i：参数SA（异常管理评估标准）的下标
	 * @param j：参数ST（可塑性评估标准）的下标
	 * @param k：参数SQ（质量评估标准）的下标
	 * @param requirement
	 * @return
	 */
	public double predictDesign(int i, int j, int k, double requirement){
		return SA[i]*ST[j]*SQ[k] * requirement;
	}
	
	/**
	 * 
	 * @param t:进行Putnam预测时，软件已经开发的时间(自然月)
	 * @param SLOC:已完成的软件源代码行数
	 * @param td:达到里程碑7时所需的开发时间(自然月)
	 * @param design:设计阶段的错误密度(错误/行)
	 * @return 编码阶段的错误密度(错误/行)
	 */
	public double predictCoding(int t, int SLOC, int td, double design){
		double sum = 0;
		for (int i = 1; i <= (int)t; i++) {
			sum += design*SLOC/1000/(td*td) * i * Math.exp( -3*i*i / (td*td) );
		}
		return design * (1-sum);
	}
	
	
	/**
	 * 
	 * @param R：指令执行的平均速率(行/秒)
	 * @param SLOC:已完成的软件源代码行数
	 * @param faultRate：预测阶段的错误密度(错误/行)
	 * @return 早期失效率预测值（时间单位同R）
	 */
	public double predictFailureRateEarly(int R, int SLOC, double faultRate){
		return K * R  * faultRate / ER;
	}
	
	public static void main(String[] args) {
		RomeMusaModel model = new RomeMusaModel();
		double r1 = model.predictRequirement(0, 0);
		double r2 = model.predictDesign(0, 0, 0, r1);
		double r3 = model.predictCoding(3, 15000, 5 ,r2);
		double r = model.predictFailureRateEarly(8000, 15000, r3);
		System.out.printf("r1=%.4f\n", r1);
		System.out.printf("r2=%.4f\n", r2);
		System.out.printf("r3=%.4f\n", r3);
		System.out.println("r =" + new DecimalFormat("#.####E0").format(r));
	}
	
}
