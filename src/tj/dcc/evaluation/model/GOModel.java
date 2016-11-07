package tj.dcc.evaluation.model;

import java.lang.Math;

/**
 * G-O 模型
 */
public class GOModel extends CommonModelParent implements ModelInterface{
	
	/**
	 * 接收不完全失效数据的模型构造器
	 * @param time 累计失效时间
	 * @param number 累计失效时间内的累计失效数
	 */
	public GOModel(double[] time, int[] number) {
		super(time, number);
	}
	
	/**
	 * 计算软件可靠性度量指标
	 * @param deviation 误差阈值
	 */
	@Override
	public void calculate(double deviation) {
		double left, right, result, a, b;
		int n = this.failureDate.time.length;//失效数据收集点的个数
		
		a = 0;
		b = 0;
		
		while(true){
			left = (this.failureDate.number[n-1] /
					(1 - Math.pow(Math.E, (-1) * b * this.failureDate.time[n-1]))) *
					this.failureDate.time[n-1] * 
					Math.pow(Math.E, (-1) * b * this.failureDate.time[n-1]);
			
			right = 0;
			
			right += (this.failureDate.number[0] - 0) *
					 (this.failureDate.time[0] * Math.pow(Math.E, (-1) * b * this.failureDate.time[0]) /
					  (1 - Math.pow(Math.E, (-1) * b * this.failureDate.time[0])));
			for (int i = 0; i < n-1; i++) {
				right += (this.failureDate.number[i+1] - this.failureDate.number[i]) *
						 (this.failureDate.time[i+1] * Math.pow(Math.E, (-1) * b * this.failureDate.time[i+1]) -
						  this.failureDate.time[i] * Math.pow(Math.E, (-1) * b * this.failureDate.time[i])) /
						  (Math.pow(Math.E, (-1) * b * this.failureDate.time[i]) -
						   Math.pow(Math.E, (-1) * b * this.failureDate.time[i+1]));
			}
			
			result = left - right;
			
			if(deviation >= Math.abs(result)){
				a= this.failureDate.number[n-1] /( 1 - Math.pow(Math.E, (-1) * b * this.failureDate.time[n-1]) );
				this.estimationResults.expectFaultNum = a;
				this.estimationResults.residualFaultNum = a - this.failureDate.time[n-1];
				this.estimationResults.failureRate = a * b * Math.pow(Math.E, (-1) * b * this.failureDate.time[n-1]);
				this.estimationResults.reliability = "exp[-" + (Math.pow(Math.E, (-1) * b * this.failureDate.time[n-1]) * a) +
														"(1-exp(-" + b + "t)]";
				this.estimationResults.mttf = 1 / this.estimationResults.failureRate;
				System.out.println("===================================================");
				System.out.println("G-O软件可靠性增长模型参数计算结果");
				System.out.println("a= " + a);
				System.out.println("b= " + b);
				System.out.println("===================================================");
				
				break;
			}
			else
				b += 0.000001;
		}
	}
	
	/**
	 * 输出评估结果到控制台
	 */
	@Override
	public void printResult() {
		System.out.println("===================================================");
		System.out.println("G-O软件可靠性增长模型评估结果");
		System.out.println("失效数据样本大小： " + this.failureDate.time.length);
		System.out.println("缺陷总数的期望值: " + this.estimationResults.expectFaultNum);
		System.out.println("软件中的剩余缺陷数: " + this.estimationResults.residualFaultNum);
		System.out.println(this.failureDate.time.length + "次可靠性增长后的失效率: " + this.estimationResults.failureRate);
		System.out.println(this.failureDate.time.length + "次可靠性增长后的可靠度函数: " + this.estimationResults.reliability);
		System.out.println(this.failureDate.time.length + "次可靠性增长后的平均失效前时间: " + this.estimationResults.mttf);
		System.out.println("===================================================");
	}
}
