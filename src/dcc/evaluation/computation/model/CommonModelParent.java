package dcc.evaluation.computation.model;

/**
 * 失效数据类
 * @author daxu
 */
class FailureDataSet{
	
	public double []time;
	public int []number;
	public double terminateTime;//测试终止时间

	/**
	 * 完全失效时间构造器
	 * @param time: 失效间隔时间
	 */
	public FailureDataSet(double []time){
		this.time = time;
		this.terminateTime = time[time.length-1];//默认测试终止时间为最后一次失效发生的时间
	}
	
	/**
	 * 完全失效时间构造器
	 * @param time: 失效间隔时间
	 * @param terminateTime： 测试终止时间
	 */
	public FailureDataSet(double []time, double terminateTime){
		this.time = time;
		this.terminateTime = terminateTime;
	}
	
	/**
	 * 不完全失效时间构造器
	 * @param time: 失效间隔时间
	 * @param number: 失效间隔时间内发生的失效数
	 */
	public FailureDataSet(double []time, int []number){
		this.time = time;
		this.number = number;
	}
}

/**
 * 软件可靠性度量指标类
 * @author daxu
 */
class EstimationResults{
	public double mttf;//平均失效前时间
	public String reliability;//可靠度函数
	public double failureRate;//失效率
	public double residualFaultNum;//剩余失效数
	public double expectFaultNum;//失效总数期望值
	public String meanFunction;//累计失效数均值函数
}

/**
 * 经典模型父类
 * @author daxu
 */
public class CommonModelParent {
	public FailureDataSet failureDate;//失效数据
	public EstimationResults estimationResults;//评估结果
	
	/**
	 * 完全失效时间模型父类构造器
	 * @param time 失效间隔时间
	 */
	public CommonModelParent(double []time){
		this.failureDate = new FailureDataSet(time);
		this.estimationResults = new EstimationResults();
	}
	
	/**
	 * 完全失效时间模型父类构造器
	 * @param time 失效间隔时间
	 */
	public CommonModelParent(double []time, double terminateTime){
		this.failureDate = new FailureDataSet(time, terminateTime);
		this.estimationResults = new EstimationResults();
	}

	/**
	 * 不完全失效时间模型父类构造器
	 * @param time 失效间隔时间
	 * @param number 失效间隔时间内发生的失效数
	 */
	public CommonModelParent(double []time, int []number){
		this.failureDate = new FailureDataSet(time, number);
		this.estimationResults = new EstimationResults();
	}
}
