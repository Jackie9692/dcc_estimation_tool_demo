package dcc.evaluation.computation.model;

import java.lang.Math;

import org.junit.Test;

/**
 * G-O 模型
 */
public class LVModel extends CommonModelParent implements ModelInterface{

	/**
	 * 接收不完全失效数据的模型构造器
	 * @param time 累计失效时间
	 * @param number 累计失效时间内的累计失效数
	 */
	public LVModel(double[] time) {
		super(time);
	}

	/**
	 * 计算软件可靠性度量指标
	 * @param deviation 误差阈值
	 */
	@Override
	public void calculate(double deviation) {
		int n = this.failureDate.time.length;//失效数据收集点的个数
		double a = 0;
		double b = 0;
		double left = 0.00000001;
		double right = 1;
		double middle = (left + right) / 2;
		double fleft = fvalue(left);
		double fright = fvalue(right);
		double fmiddle = fvalue(middle);

		if(fleft*fright>0){
			System.out.println("b在0~1间没有解或有偶数个解");
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

		a= this.failureDate.number[n-1] /( 1 - Math.pow(Math.E, (-1) * b * this.failureDate.time[n-1]) );
		this.estimationResults.expectFaultNum = a;
		this.estimationResults.residualFaultNum = a - this.failureDate.time[n-1];
		this.estimationResults.failureRate = a * b * Math.pow(Math.E, (-1) * b * this.failureDate.time[n-1]);
		this.estimationResults.reliability = "exp[-" + (Math.pow(Math.E, (-1) * b * this.failureDate.time[n-1]) * a) +
												"(1-exp(-" + b + "t)]";
		this.estimationResults.mttf = 1 / this.estimationResults.failureRate;
		System.out.println("===================================================");
		System.out.println("LV软件可靠性增长模型参数计算结果");
		System.out.println("a= " + a);
		System.out.println("b= " + b);
		System.out.println("===================================================");
	}

	/**
	 * 求f(x)函数值
	 */
	public double fvalue(double x){
		double left, right, result;
		int n = this.failureDate.time.length;//失效数据收集点的个数

		left = (this.failureDate.number[n-1] /
				(1 - Math.pow(Math.E, (-1) * x * this.failureDate.time[n-1]))) *
				this.failureDate.time[n-1] *
				Math.pow(Math.E, (-1) * x * this.failureDate.time[n-1]);
		right = 0;

		right += (this.failureDate.number[0] - 0) *
				 (this.failureDate.time[0] * Math.pow(Math.E, (-1) * x * this.failureDate.time[0]) /
				  (1 - Math.pow(Math.E, (-1) * x * this.failureDate.time[0])));
		for (int i = 0; i < n-1; i++) {
			right += (this.failureDate.number[i+1] - this.failureDate.number[i]) *
					 (this.failureDate.time[i+1] * Math.pow(Math.E, (-1) * x * this.failureDate.time[i+1]) -
					  this.failureDate.time[i] * Math.pow(Math.E, (-1) * x * this.failureDate.time[i])) /
					  (Math.pow(Math.E, (-1) * x * this.failureDate.time[i]) -
					   Math.pow(Math.E, (-1) * x * this.failureDate.time[i+1]));
		}

		result = left - right;

		return result;
	}

	/**
	 * 输出评估结果到控制台
	 */
	@Override
	public void printResult() {
		System.out.println("===================================================");
		System.out.println("LV软件可靠性增长模型评估结果");
		System.out.println("失效数据样本大小： " + this.failureDate.time.length);
		System.out.println("缺陷总数的期望值: " + this.estimationResults.expectFaultNum);
		System.out.println("软件中的剩余缺陷数: " + this.estimationResults.residualFaultNum);
		System.out.println(this.failureDate.time.length + "次可靠性增长后的失效率: " + this.estimationResults.failureRate);
		System.out.println(this.failureDate.time.length + "次可靠性增长后的可靠度函数: " + this.estimationResults.reliability);
		System.out.println(this.failureDate.time.length + "次可靠性增长后的平均失效前时间: " + this.estimationResults.mttf);
		System.out.println("===================================================");
	}

	/**
	 * 不动点法计算数值解
	 */
	public void calculate1(){
		double deviation = 0.000001;

//		double x1 = 2;
//		double x2 = 0.2;
		double x1 = -0.0068;
		double x2 =  0.7676;

		double fx1 = getValue1(x1, x2);
		double fx2 = getValue2(x1, x2);

		while(true){
			System.out.println(fx1 + "," + fx2);
			if((Math.abs(fx1-x1)<deviation)&&(Math.abs(fx2-x2)<deviation)){
				break;
			}
			x1 = fx1;
			x2 = fx2;
			fx1 = getValue1(x1, x2);
			fx2 = getValue2(x1, x2);
		}

	}

	public double getValue1(double x1, double x2){
		double result = 0.0;
		int n = this.failureDate.time.length;
		double time[] = this.failureDate.time;

		double sum1 = 0, sum2 = 0, sum3 = 0, sum4 = 0;
		for(int i=1; i<=n; ++i){
			double temp1 = x1 + x2*i + time[i-1];
			double temp2 = x1 + x2*i;
			sum1 += 1/temp1;
			sum2 += i/temp1;
			sum3 += 1/temp2;
			sum4 += i/temp2;
		}
		result = sum1/sum3 - sum2/sum4 + x1;
		return result;
	}

	public double getValue2(double x1, double x2){
		double result = 0.0;
		int n = this.failureDate.time.length;
		double time[] = this.failureDate.time;

		double sum1 = 0, sum2 = 0, sum3 = 0, sum4 = 0;
		for(int i=1; i<=n; ++i){
			double temp1 = x1 + x2*i + time[i-1];
			double temp2 = x1 + x2*i;
			sum1 += 1/temp2;
			sum2 += 1/temp1;
			sum3 += Math.log(temp1);
			sum4 += Math.log(temp2);
		}
		result = n*(sum1/sum2-1) - sum3 + sum4 + x2;
		return result;
	}




}
