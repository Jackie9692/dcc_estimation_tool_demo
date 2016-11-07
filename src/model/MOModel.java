package model;

import util.DataTransform;

/**
 * M-O 模型
 * @author daxu
 */
public class MOModel extends CommonModelParent implements ModelInterface{

	/**
	 * 接收不完全失效数据的模型构造器
	 * @param time 累计失效时间
	 * @param number 累计失效时间内发生的失效数
	 */
	public MOModel(double[] time, double[] number) {
		super(time, number);
	}
	
	/**
	 * 计算软件可靠性度量指标
	 * @param deviation 误差阈值
	 */
	@Override
	public void calculate(double deviation) {
		double b0,b1=0;

		double left = 0.03;
		double right = 0;

		int n = this.failureDate.time.length;
		double tn = this.failureDate.time[n-1];

		while(true){//迭代法计算
			double sum=0;
			for(int i=0; i<n;++i){
				sum+=1/(1+left*this.failureDate.time[i]);
			}
			right = (float) (sum*(1+left*tn)*Math.log(1+left*tn))/n/tn;

			if(Math.abs(left-right)<deviation){
				b1 = left;
				b0 = n/Math.log(1+b1*tn);
				System.out.println("b1:"+b1+" b0:"+ b0);
				break;
			}
			left = right;
		}
	}

	/**
	 * 二分法求解
	 */
	public void dichoCaculate(double deviation){
		double b0,b1=0;

		//b1初始值
		double left = 0.03001;
		double right = 0.031001;
		double middle= (left+right)/2;
//		double middle = 0.03;

		double fleft = fvalue(left);
		double fright = fvalue(right);
		double fmiddle = fvalue(middle);

		System.out.println("left:"+left+" right:"+right+" middle:"+middle);
		System.out.println("fleft:"+fleft+" fright:"+fright+" fmiddle:"+fmiddle);

		System.out.println(fvalue(0.03049));
		if(fleft*fright>0)
			return ;
		while(true){
			if(fleft*fmiddle<0){
				right = middle;
			}else{
				left = middle;
			}
			if(right-left<deviation)
				break;
			middle= (left+right)/2;
			fmiddle = fvalue(middle);
		}

		System.out.println(b1);
	}

	/**
	 * 二分法求函数值
	 */
	public double fvalue(double x){
		int n = this.failureDate.time.length;
		double tn = this.failureDate.time[n-1];
		double result = 0;

		double sum=0;
		for(int i=0; i<n;++i){
			sum+=1/(1+x*this.failureDate.time[i]);
		}
//		result = (sum*(1+x*tn)*Math.log(1+x*tn))/n/tn-x;
		result = sum/x - n*tn/(1+x*tn)/Math.log(1+x*tn);
//		System.out.println("result:" + result);
		return result;
	}

	/**
	 * 输出评估结果到控制台
	 */
	@Override
	public void printResult() {
		System.out.println(this.estimationResults.expectFaultNum);;
	}

	public static void main(String[] args) {
		// failure date set
//		double []time = new float[]{5,9,10,15,18,26,27,35,42,46,50,56,63,67,72,75,78,85,88,98,
//				100,111,125,129,137,142,155,165,181,204};
		double []time = new double[]{5,9,10,15,18,26,27,35,42,46,50,56,63,67,72};

		double []number = new double[]{1,2,3};

		//choose a model
		MOModel moModel = new MOModel(DataTransform.accumateTointerval(time), number);
		moModel.dichoCaculate(0.001);
	}
}
