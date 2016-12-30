package dcc.evaluation.computation.model;

/**
 * 
 * Keen的DPPM模型
 *
 */
public class DevelopmentProcessPredictionModel {
	
	/**
	 * SEI的CMM等级及其对应的设计故障密度(每KSLOC的故障数)和首次交付后48个月的故障稳定水平
	 */
	private double[][] CMM = new double[][] { {0.5, 0.015}, {1.0, 0.03}, {2.0, 0.05}, {3.0, 0.07}, {5.0, 0.1} };
	
	private double KSLOC;//软件规模，千行代码数
	private int initCMM;//软件组织开发期的CMM等级
	private int matureCMM;//软件组织成熟期的CMM等级
	
	private double F;//成熟期的设计缺陷数目
	private double N;//稳定状态下的缺陷数目
	
	
}
