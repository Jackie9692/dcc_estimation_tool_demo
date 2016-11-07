package model;

/**
 * 失效数据类
 * @author daxu
 */
class FailureDataSet{
	
	public double []time;
	public double []number;

	/**
	 * 完全失效时间构造器
	 * @param time: 失效间隔时间
	 */
	public FailureDataSet(double []time){
		this.time = time;
	}
	
	/**
	 * 不完全失效时间构造器
	 * @param time: 失效间隔时间
	 * @param number: 失效间隔时间内发生的失效数
	 */
	public FailureDataSet(double []time, double []number){
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
	 * 不完全失效时间模型父类构造器
	 * @param time 失效间隔时间
	 * @param number 失效间隔时间内发生的失效数
	 */
	public CommonModelParent(double []time, double []number){
		this.failureDate = new FailureDataSet(time, number);
		this.estimationResults = new EstimationResults();
	}
}
