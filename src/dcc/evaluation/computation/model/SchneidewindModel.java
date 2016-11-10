package dcc.evaluation.computation.model;

/**
 * Schneidewind 模型
 */
public class SchneidewindModel extends CommonModelParent implements ModelInterface{
	
	/**
	 * 接收完全失效数据的模型构造器
	 * @param time 失效间隔时间
	 */
	public SchneidewindModel(double[] time) {
		super(time);
	}
	
	/**
	 * 计算软件可靠性度量指标
	 * @param deviation 误差阈值
	 */
	@Override
	public void calculate(double deviation) {
		int n = (int)this.failureDate.time[this.failureDate.time.length-1];//失效间隔个数，间隔长度取1
		double a = 0;
		double b = 0;
		
		double left = 0.000001;
		double right = 1;
		double middle = (left + right) / 2;
		double fleft = fvalue(left);
		double fright = fvalue(right);
		double fmiddle = fvalue(middle);
		
		if(fleft*fright>0){
			System.out.println("N在0~1间没有解或有偶数个解");
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
			b=middle;
			fleft = fvalue(left);
			fright = fvalue(right);
			fmiddle = fvalue(middle);
		}
		
		a = b * this.failureDate.time.length /(1 - Math.pow(Math.E, -1 * b * n));
		this.estimationResults.failureRate = a * Math.pow(Math.E, -1 * b * n);
		this.estimationResults.meanFunction = a/b + "[1-exp(-" + b + "t)]";
		this.estimationResults.reliability = a/b + "[" + Math.pow(Math.E, -1 * b * n) + "-exp(-" + b + "("+ n + "+t))]";
		this.estimationResults.mttf = 1 / this.estimationResults.failureRate;
		
		System.out.println("===================================================");
		System.out.println("Schneidewind软件可靠性增长模型参数计算结果");
		System.out.println("a= " + a);
		System.out.println("b= " + b);
		System.out.println("===================================================");
	}
	
	/**
	 * 求f(x)函数值
	 */
	public double fvalue(double x){
		int n = (int)this.failureDate.time[this.failureDate.time.length-1];//失效间隔个数，间隔长度取1
		double  left, right, result;
		
		left = 1 / (Math.pow(Math.E, x) - 1) - n / (Math.pow(Math.E, x * n) - 1);
		right = 0;
		
		for (int i = 0; i < this.failureDate.time.length; i++) {
			right += this.failureDate.time[i] - 1;
		}
		
		right /= this.failureDate.time.length;
		result = left - right;
		
		return result;
	}
	
	/**
	 * 输出评估结果到控制台
	 */
	@Override
	public void printResult() {
		System.out.println("===================================================");
		System.out.println("Schneidewind软件可靠性增长模型评估结果");
		System.out.println("失效数据样本大小： " + this.failureDate.time.length);
		System.out.println("缺陷总数的期望值累计失效数均值函数: " + this.estimationResults.meanFunction);
		System.out.println(this.failureDate.time.length + "次可靠性增长后的失效率: " + this.estimationResults.failureRate);
		System.out.println(this.failureDate.time.length + "次可靠性增长后的可靠度函数: " + this.estimationResults.reliability);
		System.out.println(this.failureDate.time.length + "次可靠性增长后的平均失效前时间: " + this.estimationResults.mttf);
		System.out.println("===================================================");
	}
}
