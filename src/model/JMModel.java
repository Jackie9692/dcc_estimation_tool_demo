package model;

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
		
		while (true) {// 迭代法计算N
			double sum1 = 0, sum2 = 0, sum3 = 0;
			double result = 0;
			
			for (int i = 1; i <= n; ++i) {
				sum1 += 1.0 / (N - i + 1);
				sum2 += this.failureDate.time[i-1];
				sum3 += (i - 1) * this.failureDate.time[i-1];
			}

			result = sum1 - n *sum2/ (N*sum2 - sum3);
			
			if (deviation >= Math.abs(result)) {//监测计算阈值
				this.estimationResults.expectFaultNum = N;//软件的失效总数的期望值
				phi = n / (N*sum2 -sum3);//失效率比例常数
				this.estimationResults.failureRate = phi * (N - n);//第n个失效发生后的失效率
				this.estimationResults.reliability = "exp{-" + this.estimationResults.failureRate + "t}";//第n个失效发生后的可靠度函数
				if(this.estimationResults.failureRate != 0)
					this.estimationResults.mttf = 1 / (this.estimationResults.failureRate);//第n个失效发生后的平均失效前时间
				
				System.out.println("===================================================");
				System.out.println("J-M软件可靠性增长模型参数计算结果");
				System.out.println("N= " + N);
				System.out.println("phi= " + phi);
				System.out.println("===================================================");
				
				break;
			} 

			N += 0.0001;//迭代步长
		}
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
		System.out.println(this.failureDate.time.length + "次可靠性增长后的失效率: " + this.estimationResults.failureRate);
		System.out.println(this.failureDate.time.length + "次可靠性增长后的可靠度函数: " + this.estimationResults.reliability);
		System.out.println(this.failureDate.time.length + "次可靠性增长后的平均失效前时间: " + this.estimationResults.mttf);
		System.out.println("===================================================");
	}
}
