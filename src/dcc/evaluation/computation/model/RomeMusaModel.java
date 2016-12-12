package dcc.evaluation.computation.model;

public class RomeMusaModel {
	private double[] A = new double[] { 0.0018, 0.0085, 0.0123, 0.0092 };//平均错误密度(错误/行)
	private double[] D = new double[] { 0.76, 1.0, 1.3 };//开发环境决定的修正因子对照表
	private double[] SA = new double[] { 0.9, 1.0, 1.1 };//异常管理评估标准
	private double[] ST = new double[] { 1, 1.1 };//可塑性评估标准
	private double[] SQ = new double[] { 1.0, 1.1 };//质量评估标准
	private int [] Putnam = new int[10];//Putnam开发里程碑
	
	private int t;//进行Putnam预测时，软件已经开发的时间(自然月)
	private int SLOC;//已完成的软件源代码行数
	private int td;//达到里程碑7时所需的开发时间
	
	int R;//指令执行的平均速率(行/秒)
	double K = 4.2e-7;//模型系数，通过历史经验数据的统计分析得出k=4.2e-7
	double ER;//代码扩展比率-由编程语言确定，默认均值为4
	
	/**
	 * Rome实验室早期缺陷密度(错误/行)预测模型 
	 */
	public double predictRequirement(int i, int j){
		return A[i]*D[j];
	}
	
	
	public double predictDesign(int i, int j, int k, double requirement){
		return SA[i]*ST[j]*SQ[k] * requirement;
	}
	
	
	public double predictCoding(double design){
		double sum = 0;
		for (int i = 1; i <= (int)t; i++) {
			sum += design*SLOC/1000/(td*td) * i * Math.exp( -3*i*i / (td*td) );
		}
		return design * (1-sum);
	}
	
	
	/**
	 * Musa早期失效率预计模型
	 * faultRate:错误/行，可由Rome实验室模型获得
	 */
	public double predictFailureRateEarly(int SLOC, double faultRate){
		return R * K / ER * faultRate * SLOC / SLOC;
	}
	
}
