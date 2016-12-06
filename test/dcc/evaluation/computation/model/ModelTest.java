package dcc.evaluation.computation.model;

import java.util.ArrayList;

import dcc.evaluation.computation.model.*;

public class ModelTest {

	/**
	 * J-M模型 期望结果：
	 * 缺陷总数的期望值N=31.2
	 * 失效率比例常数phi=0.00685
	 * 测试数据来源：Naval Tactical Data System——NOSA
	 */
	@org.junit.Test
	public void testJMModel() {
		
		double[] time1 = new double[] { 9, 12, 11, 4, 7, 2, 5, 8, 5, 7, 1, 6, 1, 9, 4, 1, 3, 3, 6, 1, 11, 33, 7, 91, 2, 1 };//NTDS，完全失效数据
		JMModel jmModel = new JMModel(time1);
		jmModel.calculate(0.000001);//设置计算阈值为0.000001
		jmModel.printResult();//输出计算结果到控制台
	}

	/**
	 * G-O模型 期望结果：
	 * 缺陷总数的期望值a=142.32
	 * 失效率比例常数b=0.1246
	 * 测试数据来源： Musa J D. Software Reliability Data[M].  A real-time, command and control system,
	 * 			The delivered object instructions for this system was developed by Bell Laboratories.
	 */
	@org.junit.Test
	public void testGOModel() {
		double[] time = new double[] { 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25 };//NTDS，完全失效数据
		int[] number = new int[] { 27,43,54,64,75,85,84,89,92,93,97,104,106,111,116,122,122,127,128,129,131,132,134,135,136 };
		GOModel goModel = new GOModel(time, number);
		goModel.calculate(0.000001);//设置计算阈值为0.001
		goModel.printResult();//输出计算结果到控制台
	}

	/**
	 * M-O模型 期望结果：
	 * 缺陷总数的期望值b0=42.3
	 * 失效率比例常数b1=0.000262
	 * 测试数据来源：Musa J D. 软件可靠性——度量、预计和应用[M]. T1系统失效数据
	 */
	@org.junit.Test
	public void testMOModel(){
		double []time = new double[]{3,33,146,227,342,351,353,444,556,571,709,759,836,860,968,1056,1726,1846,1872,1986,2311,2366,2608,
				2676,3098,3278,3288,4434,5034,5049,5085,5089,5089,5097,5324,5389,5565,5623,6080,6380,6477,6740,7192,7447,7644,7837,
				7843,7922,8738,10089,10237,10258,10491,10625,10982,11175,11411,11442,11811,12559,12559,12791,13121,13486,14708,15251,15261,15277,15806,
				16185,16229,16358,17168,17458,17758,18287,18568,18728,19556,20567,21012,21308,23063,24127,25910,26770,27753,28460,28439,29361,30085,32408,
				35338,36799,37642,37654,37915,39715,40580,42015,42045,42188,42296,42296,45406,46653,47596,48296,49171,49416,50145,52042,52489,52875,53321,
				53443,54433,55381,56463,56485,56560,57042,62551,62651,62661,63732,64103,64893,71043,74364,75409,76057,81542,82702,84566,88682};
		double terminateTime = 91208;
		MOModel moModel = new MOModel(time, terminateTime);
		moModel.calculate(0.000001);//设置计算阈值为0.00000001
		moModel.printResult();//输出计算结果到控制台
	}

	/**
	 * Schneidewind模型 期望结果：
	 * 初始故障率a=0.1817663
	 * 故障率比例常数b=0.00585515
	 * 测试数据来源：NTDS(制造阶段失效数据)
	 */
	@org.junit.Test
	public void testSchneidewindModel() {
		double[] time = new double[] { 9,21,32,36,43,45,50,58,63,70,71,77,78,87,91,92,95,98,104,105,116,149,156,247,249,250 };//NTDS，不完全失效数据
		SchneidewindModel schneidewindModel = new SchneidewindModel(time);
		schneidewindModel.calculate(0.000001);//设置计算阈值为0.000001
		schneidewindModel.printResult();//输出计算结果到控制台
	}

	@org.junit.Test
	public void testWeibullModel(){
		double[] time = new double[]{ 2,3,7,8,9,10,11,18,21,33,35,37,44,45,47,48,49,50,51,52,53,55,56,57,63,76,83,91,106 };
		WeibullModel weibullModel = new WeibullModel(time);
		weibullModel.calculate(0.000001);
		weibullModel.printResult();
	}

	/**
	 * LV 模型不动点法测试
	 */
	@org.junit.Test
	public void testLVModel(){
		double[] time = new double[]{3.04, 0.315, 1.39, 3.04, 28.4, 6.22, 18.6, 7.03, 68.5, 15.0,
									24.2, 608.0, 30.3, 21.3, 118.0, 116.0, 51.0, 98.0, 141.0, 36.5,
									73.6, 35.3, 14.0, 1360.0, 114.0, 4440.0, 6580.0, 408.0, 3070.0, 6230.0,
									11.2, 829.0, 1370.0, 1350.0, 2290.0, 1520.0, 4400.0, 15200.0, 12900.0, 6790.0,
									13700.0, 302000.0, 13600.0, 4180.0, 5200.0, 59900.0, 374000.0, 8170.0, 58200.0, 875000.0,
									82800.0, 56900.0, 420000.0, 166000.0, 60300.0, 811000.0, 446000.0, 2070000.0, 175000.0, 1820000.0,
									1699000.0, 381000.0, 1730000.0, 654000.0, 5070000.0, 3120000.0, 6330000.0, 668000.0, 66100.0, 198000.0,
									1790000.0, 3410000.0, 1820000.0, 60900.00, 7310000.0, 6090000.0, 22700000.0, 7590000.0, 23700000.0, 7490000.0};



		LVModel model = new LVModel(time);
		model.calculate1();
//		model.printResult();
	}
}
