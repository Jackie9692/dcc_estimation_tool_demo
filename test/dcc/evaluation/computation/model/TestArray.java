package dcc.evaluation.computation.model;

public class TestArray {
	
	private void run(double[] A){
		A[0]=11;
	}
	public static void main(String[] args) {
		TestArray t = new TestArray();
		double [] A = {1,2,3};
		t.run(A);
		System.out.println(A[0]);
	}
}
