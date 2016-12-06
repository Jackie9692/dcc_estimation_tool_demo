package dcc.evaluation.computation.model;

import org.junit.Test;

public class TestSoftwareLifteProcessModel {
	@Test
	public void testOne(){
		double lambda = 0.007817;
		double SLOC = 120000;
		double td = 5;
		double t = 6;
		double result;
		
		double sum = 0;
		for (int i = 1; i <= (int)t; i++) {
			sum += lambda*SLOC/1000/(td*td) * i * Math.exp( -3*i*i/(td*td) );
			System.out.println("sum:" + sum);
		}
//		System.out.println("\nsum:" + sum);
		
//		double tmp = t * lambda*SLOC/1000/(td*td) * t * Math.exp( -3*t*t/(td*td) );
		
//		double N = lambda * SLOC / 1000;
//		int count = (int)t*1 + 1;
//		double sum = 0;
//		while(count>1){
//			double tmp = -3 * Math.pow(count-1, 2) / Math.pow(td, 2);
//			sum = N / Math.pow(td, 2) * (count-1) * Math.exp(tmp) + sum;
//			count = count - 1;
//		}
		
		System.out.println("\nsum:" + sum);
		result = lambda * ( 1 - sum );
		System.out.println("lambda:" + result);
	}
}
