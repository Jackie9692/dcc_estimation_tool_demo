package dcc.evaluation.computation.model;

import org.junit.Test;

public class TestSoftwareLifteProcessModel {
	@Test
	public void testOne(){
		double lambda = 0.007817;
		double SLOC = 120000;
		double td = 5;
		double t = 6;
		double K = 4.2e-7;
		double ER = 3;
		double B = 10.6;
		int R = 8000;
		double result;
		
		//编码阶段
		double sum = 0;
		for (int i = 1; i <= (int)t; i++) {
			sum += lambda*SLOC/1000/(td*td) * i * Math.exp( -3*i*i/(td*td) );
			System.out.println("sum:" + sum);
		}
		
		System.out.println("\nsum:" + sum);
		result = lambda * ( 1 - sum );
		System.out.println("lambda:" + result);
		
		//早期预测
		double lambda1 = K * lambda * SLOC * SLOC * ER;
//		double lambda1 = R * K / ER * lambda * SLOC / SLOC;
		System.out.println("\nlambda1:" + lambda1);
	}
}
