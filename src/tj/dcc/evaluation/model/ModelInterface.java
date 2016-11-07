package tj.dcc.evaluation.model;

/**
 * 模型函数调用接口
 * @author daxu
 */
public interface ModelInterface {
	
	/**
	 * 计算软件可靠性度量指标
	 */
	void calculate(double deviation);

	/**
	 * 输出评估结果到控制台
	 */
	void printResult();
}
