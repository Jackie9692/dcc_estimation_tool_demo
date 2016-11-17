package dcc.evaluation.computation.model;

/**
 * Weibull 模型
 */
public class WeibullModel extends CommonModelParent implements ModelInterface{
	
	/**
	 * 接收不完全失效数据的模型构造器
	 * @param time 失效间隔时间
	 */
	public WeibullModel(double[] time) {
		super(time);
	}
	
	@Override
	public void calculate(double deviation) {
		int n = this.failureDate.time.length;//失效数据个数
		double a = 0; //形状参数
		double b; //寿命参数
		
		double left = 0;//形状参数下限
		double right = 100;//形状参数上限预设
		double middle = (left + right) / 2;
		double fleft = fvalue(left);
		double fright = fvalue(right);
		double fmiddle = fvalue(middle);
		
		int sum = 0;
		
		if(fleft*fright>0){
			System.out.println("N在" + 0 + "~" + 10000 + "间没有解或有偶数个解");
			return;
		}
		
		while(true){
			System.out.println("left: "+ fleft);
			System.out.println("right: "+ fright);
			if(fleft*fmiddle<0){
				right = middle;
			}else{
				left = middle;
			}
			if(Math.abs(fright-fleft)<deviation)
				break;
			middle= (left+right)/2;
			a=middle;
			fleft = fvalue(left);
			fright = fvalue(right);
			fmiddle = fvalue(middle);
		}
		
		for (int i = 0; i < n; i++) {
			sum += Math.pow(this.failureDate.time[i], a);
		}
		
		b = (double)n/sum;
		
		System.out.println("a = " + a);
		System.out.println("b = " + Math.pow(1/b, 1/a));
	}
	
	public double fvalue(double x){
		double result;
		int n = this.failureDate.time.length;//失效数据收集点的个数
		double sum1 = 0, sum2 = 0, sum3 = 0;
		
		for (int i = 0; i < n; i++) {
			sum1 += Math.log(this.failureDate.time[i]);
			sum2 += Math.pow(this.failureDate.time[i], x);
			sum3 += Math.log(this.failureDate.time[i]) * Math.pow(this.failureDate.time[i], x);
		}
		
		result = n/x + sum1 - (n/sum2) * sum3;
		
		return result;
	}
	
	@Override
	public void printResult() {
		
	}
}
