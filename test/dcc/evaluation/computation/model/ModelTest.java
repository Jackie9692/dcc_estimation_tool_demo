package dcc.evaluation.computation.model;

public class ModelTest {

	/**
	 * J-M模型 期望结果：
	 * 缺陷总数的期望值N=31.2
	 * 失效率比例常数phi=0.00685
	 * 测试数据来源：Naval Tactical Data System——NOSA
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
	public void testSchneidewind() {
		double[] time = new double[] { 9,21,32,36,43,45,50,58,63,70,71,77,78,87,91,92,95,98,104,105,116,149,156,247,249,250 };//NTDS，不完全失效数据
		SchneidewindModel schneidewindModel = new SchneidewindModel(time);
		schneidewindModel.calculate(0.000001);//设置计算阈值为0.000001
		schneidewindModel.printResult();//输出计算结果到控制台
	}
}
