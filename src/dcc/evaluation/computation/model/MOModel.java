package dcc.evaluation.computation.model;

/**
 * M-O 模型
 * @author daxu
 */
public class MOModel extends CommonModelParent implements ModelInterface{

	/**
	 * 接收完全失效数据的模型构造器
	 * @param time 累计失效时间
	 * @param number 累计失效时间内发生的失效数
	 */
	public MOModel(double[] time, double terminateTime) {
		super(time, terminateTime);
	}
	
	/**
	 * 计算软件可靠性度量指标
	 * @param deviation 误差阈值
	 * 二分法求解
	 */
	public void calculate(double deviation){
		double b0,b1=0;
		double left = 0.00000001;//b1初始值
		double right = 1;
		double middle= (left+right)/2;
		double fleft = fvalue(left);
		double fright = fvalue(right);
		double fmiddle = fvalue(middle);
		double terminateTime = this.failureDate.terminateTime;
		
		if(fleft*fright>0){
			System.out.println("b1在0~1间没有解或有偶数个解");
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
			b1=middle;
			fleft = fvalue(left);
			fright = fvalue(right);
			fmiddle = fvalue(middle);
		}

		b0 = this.failureDate.time.length / Math.log(1 + b1 * terminateTime);
		this.estimationResults.failureRate = b0 * b1 / (b1 * terminateTime + 1);
		this.estimationResults.mttf = (1 + b1 * terminateTime) / (b1 * (b0 - 1));
		this.estimationResults.reliability = "{" + (b1 * terminateTime + 1) + "/["  + b1 + "*(" + terminateTime + 
											"+t)" + "+1]}^" + b0;
		this.estimationResults.meanFunction = b0 + "ln[" + b1 + "(" + terminateTime + "+t)+1]";
		
		System.out.println("===================================================");
		System.out.println("M-O软件可靠性增长模型参数计算结果");
		System.out.println("b0= " + b0);
		System.out.println("b1= " + b1);
		System.out.println("===================================================");
	}

	/**
	 * 求f(x)函数值
	 */
	public double fvalue(double x){
		int n = this.failureDate.time.length;
		double tn = this.failureDate.terminateTime;
		double result = 0;
		double sum=0;
		
		for(int i=0; i<n;++i){
			sum+=1/(1+x*this.failureDate.time[i]);
		}
		
		result = sum/x - n*tn/(1+x*tn)/Math.log(1+x*tn);
		
		return result;
	}

	/**
	 * 输出评估结果到控制台
	 */
	@Override
	public void printResult() {
		System.out.println("===================================================");
		System.out.println("M-O软件可靠性增长模型评估结果");
		System.out.println("失效数据样本大小： " + this.failureDate.time.length);
		System.out.println("缺陷总数的期望值累计失效数均值函数: " + this.estimationResults.meanFunction);
		System.out.println(this.failureDate.time.length + "次可靠性增长后的失效率: " + this.estimationResults.failureRate);
		System.out.println(this.failureDate.time.length + "次可靠性增长后的可靠度函数: " + this.estimationResults.reliability);
		System.out.println(this.failureDate.time.length + "次可靠性增长后的平均失效前时间: " + this.estimationResults.mttf);
		System.out.println("===================================================");
	}
}
