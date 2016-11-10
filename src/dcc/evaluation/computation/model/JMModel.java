package dcc.rsrept.controller.model;

/**
 * J-M 模型
 * @author daxu
 */
public class JMModel extends CommonModelParent implements ModelInterface {

	/**
	 * 接收完全失效数据的模型构造器
	 * @param time 失效间隔时间
	 */
	public JMModel(double[] time) {
		super(time);
	}

	/**
	 * 计算软件可靠性度量指标
	 * @param deviation 误差阈值
	 */
	@Override
	public void calculate(double deviation) {
		int n = this.failureDate.time.length;//失效数据个数
		double N = n;// 估计故障期望数N
		double phi;	//失效率比例常数
		
		double left = n;//故障总数下限
		double right = 1000000;//故障总数上限预设
		double middle = (left + right) / 2;
		double fleft = fvalue(left);
		double fright = fvalue(right);
		double fmiddle = fvalue(middle);
		
		if(fleft*fright>0){
			System.out.println("N在" + n + "~" + right + "间没有解或有偶数个解");
			return;
		}
		
		while(true){
			if(fleft*fmiddle<0){
				right = middle;
			}else{
				left = middle;
			}
			if(Math.abs(fright-fleft)<deviation)
				break;
			middle= (left+right)/2;
			N=middle;
			fleft = fvalue(left);
			fright = fvalue(right);
			fmiddle = fvalue(middle);
		}

		double sum2 = 0, sum3 = 0;
		
		for (int i = 1; i <= n; ++i) {
			sum2 += this.failureDate.time[i-1];
			sum3 += (i - 1) * this.failureDate.time[i-1];
		}
		
		phi = n / (N*sum2 -sum3);//失效率比例常数
		this.estimationResults.expectFaultNum = N;//软件的失效总数的期望值
		this.estimationResults.residualFaultNum = N -n;
		this.estimationResults.failureRate = phi * (N - n);//第n个失效发生后的失效率
		this.estimationResults.reliability = "exp[-" + this.estimationResults.failureRate + "t]";//第n个失效发生后的可靠度函数
		if(this.estimationResults.failureRate != 0)
			this.estimationResults.mttf = 1 / (this.estimationResults.failureRate);//第n个失效发生后的平均失效前时间
		
		System.out.println("===================================================");
		System.out.println("J-M软件可靠性增长模型参数计算结果");
		System.out.println("N= " + N);
		System.out.println("phi= " + phi);
		System.out.println("===================================================");
	}
	
	
	/**
	 * 求f(x)函数值
	 */
	public double fvalue(double x){
		double  result;
		int n = this.failureDate.time.length;//失效数据收集点的个数
		
		double sum1 = 0, sum2 = 0, sum3 = 0;
		
		for (int i = 1; i <= n; ++i) {
			sum1 += 1.0 / (x - i + 1);
			sum2 += this.failureDate.time[i-1];
			sum3 += (i - 1) * this.failureDate.time[i-1];
		}

		result = sum1 - n *sum2/ (x*sum2 - sum3);
		
		return result;
	}

	/**
	 * 输出评估结果到控制台
	 */
	@Override
	public void printResult() {
		System.out.println("===================================================");
		System.out.println("J-M软件可靠性增长模型评估结果");
		System.out.println("失效数据样本大小： " + this.failureDate.time.length);
		System.out.println("缺陷总数的期望值: " + this.estimationResults.expectFaultNum);
		System.out.println("软件中的剩余缺陷数: " + this.estimationResults.residualFaultNum);
		System.out.println(this.failureDate.time.length + "次可靠性增长后的失效率: " + this.estimationResults.failureRate);
		System.out.println(this.failureDate.time.length + "次可靠性增长后的可靠度函数: " + this.estimationResults.reliability);
		System.out.println(this.failureDate.time.length + "次可靠性增长后的平均失效前时间: " + this.estimationResults.mttf);
		System.out.println("===================================================");
	}
}
