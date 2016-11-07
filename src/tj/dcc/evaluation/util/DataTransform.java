package tj.dcc.evaluation.util;

/**
 * 数组工具类
 *
 * @author daxu
 *
 */
public class DataTransform {
	/**
	 * 间隔时间转化为累积失效时间
	 *
	 * @param original
	 * @return
	 */
	public static double[] intervalToAccumate(double[] original) {
		if (ArrayUtil.isEmpty(original))
			return null;

		int len = original.length;
		double[] target = new double[len];

		target[0] = original[0];
		for (int i = 1; i < len; ++i) {
			target[i] = target[i - 1] + original[i];
		}
		return target;
	}

	/**
	 * 累积失效时间转化为间隔时间
	 *
	 * @param original
	 * @return
	 */

	public static double[] accumateTointerval(double[] original) {
		if (ArrayUtil.isEmpty(original))
			return null;

		int len = original.length;
		double[] target = new double[len];

		target[0] = original[0];
		for (int i = len - 1; i > 0; --i) {
			target[i] = original[i] - original[i - 1];
		}
		return target;
	}

	// public static void main(String []args){
	// double []time = new double[]{5,9,10,15,18,26,27,35,42,46,50,56,63,67,72};
	// double []time1 = new double[]
	// {5,14,24,39,57,
	// 83, 110,145,187,233,
	// 283,339,402,469,541};
	//
	//
	// double []timee = DataTransform.intervalToAccumate(time);//test case
	// double []timee1 = DataTransform.accumateTointerval(time1);
	// System.out.println("haha");
	//
	// }
}
