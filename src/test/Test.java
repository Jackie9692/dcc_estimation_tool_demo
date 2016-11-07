package test;

import model.JMModel;

public class Test {
	
	/**
	 * J-M模型 期望结果：
	 * 缺陷总数的期望值N=31.2
	 * 失效率比例常数phi=0.00685
	 */
	@org.junit.Test
	public void testJMModel() {
		double[] time = new double[] { 9, 12, 11, 4, 7, 2, 5, 8, 5, 7, 1, 6, 1, 9, 4, 1, 3, 3, 6, 1, 11, 33, 7, 91, 2, 1 };//NTDS，完全失效数据
		JMModel jmModel = new JMModel(time);
		jmModel.calculate(0.0001);//设置计算阈值为0.0001，阈值的数量级最好不小于迭代步长(0.0001)
		jmModel.printResult();//输出计算结果到控制台
	}
	
}
