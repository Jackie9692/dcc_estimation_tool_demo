package dcc.evaluation.computation.test;

import dcc.evaluation.computation.model.*;

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
		jmModel.calculate(0.000001);//设置计算阈值为0.000001
		jmModel.printResult();//输出计算结果到控制台
	}

	/**
	 * G-O模型 期望结果：
	 * 缺陷总数的期望值a=142.32
	 * 失效率比例常数b=0.1246
	 */
	@org.junit.Test
	public void testGOModel() {
		double[] time = new double[] { 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25 };//NTDS，完全失效数据
		int[] number = new int[] { 27,43,54,64,75,85,84,89,92,93,97,104,106,111,116,122,122,127,128,129,131,132,134,135,136 };
		GOModel goModel = new GOModel(time, number);
		goModel.calculate(0.001);//设置计算阈值为0.001
		goModel.printResult();//输出计算结果到控制台
	}
}
