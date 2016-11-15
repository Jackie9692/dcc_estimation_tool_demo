package dcc.evaluation.computation.test;

public class BinaryNonlinearEquationsSolution {
	@org.junit.Test
	public void test(){
		double x1,x2;
		double x1_0,x2_0;
		double x1_1,x2_1;
		double delta_x1,delta_x2;
		double function_F,function_G;
		double F_x1,F_x2;
		double G_x1,G_x2;
		double Error = 1e-10;
		double Error_cout;
		int Steps;
		int Max_Steps=100000;
		
		x1 = 0.5;
		x2 = 0.5;
		x1_0 = x1;
		x2_0 = x2;
		
		for(Steps = 1; Steps <= Max_Steps; Steps++){
			function_F = x1_0*x1_0-10.0*x1_0+x2_0*x2_0+8.0;
			function_G = x1_0*x2_0*x2_0+x1_0-10.0*x2_0+8.0;
			
			F_x1 = 2.0*x1_0-10.0;
			F_x2 = 2.0*x2_0;
			G_x1 = x1_0*x1_0+1.0;
			G_x2 = 2.0*x1_0*x2_0-10.0;
			
			delta_x1 = -function_F/(F_x1+F_x2);
			delta_x2 = -function_G/(G_x1+G_x2);
			
			x1_1 = x1_0 + delta_x1;
			x2_1 = x2_0 + delta_x2;
			
			x1_0 = x1_1;
			x2_0 = x2_1;
					
			Error_cout = Math.abs(delta_x1) + Math.abs(delta_x2);
			
			if(Error_cout<Error) break;
		}
			System.out.println("  Run steps  is  " + Steps);
			System.out.println("x1 is " + x1_0);
			System.out.println("x2 is " + x2_0);
	}
}
