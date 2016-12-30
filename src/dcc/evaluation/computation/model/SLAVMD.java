package dcc.evaluation.computation.model;

public class SLAVMD {
	
	//GIVEN
	private double[] DAT;
	private int ESF;
	private int MAXIC;
	private int NS;
	private int N = 3; 
	private int PHIIND;
	private double[] BETA;
	
	//YIELD
	public double ALPHA;
	public int COUNT;
	public int RFLAG;
	public double[] X = new double[2];//BETA0, BETA1
	public double Z;
	
	//LOCAL GLOSSARY
	private double[] XP = new double[3];
	private double EPSD;
	private double EPSG;
	private double[] XMAX = new double[3];
	private double[] XMIN = new double[3];
	private double TMP;
	private double SM1 = 0;
	private double SM2 = 0;
	private double SM3 = 0;
	private double SM4 = 0;
	private double PHI;
	private double FX;
	private double[] G = new double[3];
	private double[][] H = new double[3][3];
	private double[] Y = new double[3];
	
	public SLAVMD() {
		
	}
	
	public void calculate(){
		if(ESF > 1){
			/**
			 * COMPUTE  CLOSED FORM  SOLUTION OF BETA(0)  AND BETA(1) FOR
			 * LEAST SQUARES OR INITIAL ESTIMATES OF  BETA(0) AND BETA(1)
			 * FOR MAXIMUM LIKELIHOOD.
			 */
			RFLAG = 0;
			for (int i = 1; i <= NS; i++) {
				SM1 += Math.pow(Math.pow(i, PHIIND), 2);
				SM2 += DAT[i-1];
				SM3 += Math.pow(i, PHIIND);
				SM4 += DAT[i-1] * Math.pow(i, PHIIND);
			}
			X[0] = (SM1*SM2-SM3*SM4) / (NS*SM1-Math.pow(SM3, 2));
			X[1] = (NS*SM4-SM3*SM2) / (NS*SM1-Math.pow(SM3, 2));
			if(ESF == 2){
				ALPHA = 2;
				XP[0] = 2;
				XP[1] = X[0];
				XP[2] = X[1];
			}
		}else{
			/**
			 * COMPUTE ESTIMATES OF ALPHA, BETA(0), AND BETA(1) FOR MAXI-
			 * MUM LIKELIHOOD USING THE TRUST REGION ALGORITHM.  BEGIN BY
			 * SETTING  THE COUNTER,  RETURN STATUS FLAG,  FUNCTION VALUE
			 * DIFFERENCE AND GRADIENT VALUE DIFFERENCE.
			 */
			COUNT = 1;
			RFLAG = -1;
			EPSD = 1.0e-4;
			EPSG = 1.0e-8;
			//SET THE UPPER AND LOWER BOUNDS FOR THE SOLUTION.
			XMAX[0] = 1.0e10;
			XMAX[1] = 1.0e10;
			XMAX[2] = 1.0e10;
			XMIN[0] = 1.0e-10;
			XMIN[1] = 1.0e-10;
			XMIN[2] = 1.0e-10;
			//SET THE INITIAL GUESSES FOR BETA(0) AND BETA(1).
			XP[1] = BETA[0];
			if(XP[1] < XMIN[1])
				XP[1] = XMIN[1];
			else
				if(XP[1] > XMAX[1])
					XP[1] = XMAX[1];
			XP[2] = BETA[1];
			if(XP[2] < XMIN[2])
				XP[2] = XMIN[2];
			else
				if(XP[2] > XMAX[2])
					XP[2] = XMAX[2];
			//COMPUTE THE INITIAL GUESS FOR ALPHA.
			TMP = 0;
			SM1 = 0;
			for(int i = 1; i <= NS; i++){
				PHI = XP[1] + XP[2] * Math.pow(i, PHIIND);
				TMP = PHI / (DAT[i-1] + PHI);
				SM1 += Math.log(TMP);
			}
			XP[0] = -1 * NS / SM1;
			if(XP[0] < XMIN[0])
				XP[0] = XMIN[0];
			else
				if(XP[0] > XMAX[0])
					XP[0] = XMAX[0];
			//INITIALIZE THE TRUST REGION.
			/**
			 * SOBROUTINE SLAVFN
			 * TO CALCULATE EITHER THE  LIKELIHOOD FUNCTION FOR X IF ESF=1, OR
			 * THE SUMS-OF-SQUARES IF ESF=2,  FOR THE  LITTLEWOOD AND  VERRALL
			 * BAYESIAN RELIABILITY GROWTH MODEL.
			 */
			//INITIALIZE THE MINIMIZED FUNCTION VALUE.
			if(ESF == 1)
				FX = -1 * NS * Math.log(X[0]);
			else
				FX = 0;
			/**
			 * CALCULATE THE MINIMIZED FUNCTION  VALUE FOR THE MAXIMUM LIKELI-
			 * HOOD FUNCTION OR LEAST SQUARES FUNCTION.
			 */
			for(int i = 1; i <= NS; i++){
				PHI = XP[1] + XP[2] * Math.pow(i, PHIIND);
				if(ESF == 1)
					FX -= XP[0] * Math.log(PHI) + (XP[0]+1) * Math.log(DAT[i-1]+PHI);
				else
					FX += Math.pow((DAT[i-1] - PHI / (XP[0]-1)), 2);
			}
			//END SLAVFN
			/**
			 * SLAVGD
			 * TO CALCULATE THE GRADIENT OF THE LIKELIHOOD FUNCTION FOR  X FOR
			 * THE  LITTLEWOOD AND VERRALL BAYESIAN  RELIABILITY GROWTH MODEL.
			 */
			//INITIALIZE THE GRADIENT VALUES.
			G[0] = -1 * NS / XP[0];
			G[1] = 0;
			G[2] = 0;
			//CALCULATE THE GRADIENT VALUES FOR THE  MAXIMUM LIKELIHOOD FUNCON
			for(int i = 1; i <= NS; i++){
				double EX = Math.pow(i, PHIIND);
				PHI = XP[1] + XP[2] * EX;
				TMP = DAT[i-1] + PHI;
				G[0] -= Math.log(PHI / TMP);
				G[1] -= (XP[0] / PHI) + (XP[0] + 1) / TMP;
				PHI = XP[0] / PHI;
				TMP = (XP[0] + 1) / TMP;
				G[2] -= (PHI - TMP) * EX;
			}
			//END SVALGD
			/**
			 * SLAVHS
			 * TO CALCULATE THE  HESSIAN OF THE  LIKELIHOOD FUNCTION FOR X FOR
			 * THE  LITTLEWOOD AND VERRALL BAYESIAN RELIABILITY GROWTH  MODEL.
			 */
			//INITIALIZE THE HESSIAN VALUES.
			H[0][0] = NS / Math.pow(XP[0], 2);
			H[0][1] = 0;
			H[0][2] = 0;
			H[1][1] = 0;
			H[1][2] = 0;
			H[2][2] = 0;
			//CALCULATE THE HESSIAN VALUES FOR THE  MAXIMUM  LIKELIHOOD FUNCON
			for(int i = 1; i <= NS; i++){
				double EX = Math.pow(i, PHIIND);
				PHI = XP[1] + XP[2] * EX;
				double PHINV = 1 / PHI;
				TMP = 1 / (PHI + DAT[i-1]);
				H[0][1] -= (PHINV - TMP);
				H[0][2] -= (PHINV - TMP) * EX;
				H[1][1] += XP[0] * Math.pow(PHINV, 2) - (XP[0]+1)*Math.pow(TMP, 2);
				H[1][2] += XP[0] * Math.pow(PHINV, 2) * EX - (XP[0]+1)*Math.pow(TMP, 2) * EX;
				H[2][2] += XP[0] * Math.pow(PHINV, 2) - (XP[0]+1)*Math.pow(TMP, 2) * Math.pow(EX, 2);
			}
			//STORE THE SYMMETRICAL HESSIAN VALUES.
			H[1][0] = H[0][1];
			H[2][0] = H[0][2];
			H[2][1] = H[1][2];
			//COMPUTE THE EIGENVALUES OF THE HESSIAN.
			double[][] HTMP = new double[3][3];
			for(int i = 0; i < N; i++)
				for(int j = 0; j < N; j++)
					HTMP[i][j] = H[i][j];
			/**
			 * EGNVAL
			 * TO CALCULATE  THE  EIGENVALUES  OF  A  REAL  SYMMETRIC  MATRIX.
			 */
			//CALL TERD1
			/**
			 * TERD1
			 * TO REDUCE A REAL SYMMETRIC  MATRIX TO A SYMMETRICAL TRIDIAGONAL
			 * MATRIX
			 */
			double[] D = new double[3];
			double[] E = new double[3];
			double[] E2 = new double[3];
			for(int i = 0; i < N; i++)
				D[i] = HTMP[i][i];
			//COMPUTE TRIDIAGONAL MATRIX.
			for(int ii = 1 ; ii <= N; ii++){
				int i = N + 1 - ii;
				int l = i - 1;
				double h = 0;
				double SCALE = 0;
				if(l < 1){
					E[i-1] = 0;
					E2[i-1] = 0;
				}else{
					//SCALE ROW.
					for(int k = 1; k <= l; k++){
						SCALE += Math.abs(HTMP[i-1][k-1]);
					}
					if(Math.abs(SCALE) < 1.0e-10){
						E[i-1] = 0;
						E2[i-1] = 0;
					}
					else{
						for(int k = 1; k <= l; k++){
							HTMP[i-1][k-1] /= SCALE;
							h += HTMP[i-1][k-1] * HTMP[i-1][k-1];
						}
						E2[i-1] = Math.pow(SCALE, 2) * h;
						double F = HTMP[i-1][l-1];
						double G = Math.copySign(Math.sqrt(h), F);
						E[i-1] = SCALE * G;
						h -= F * G;
						HTMP[i-1][l-1] = F - G;
						if(l != 1){
							F = 0;
							for(int j = 1; j <= l; j++){
								G = 0;
								//FORM ELEMENT OF A*U.
								for(int k = 1; k <= j; k++){
									G += HTMP[j-1][k-1] * HTMP[i-1][k-1];
								}
								int jp1 = j + 1;
								if(l >= jp1)
									for(int k = jp1; k <= l; k++)
										G += HTMP[k-1][j-1] * HTMP[i-1][k-1];
								//FORM ELEMENT OF P.
								E[j-1] = G / h;
								F += E[j-1] * HTMP[i-1][j-1];
							}
							h = F / (h + h);
							//FORM REDUCED A.
							for(int j = 1; j <= l; j++){
								F = HTMP[i-1][j-1];
								G = E[j-1] - h * F;
								E[j-1] = G;
								for(int k = 1; k <= j; k++){
									HTMP[j-1][k-1] -= F * E[k-1] - G * HTMP[i-1][k-1];
								}
							}				
						}
						for(int k = 1; k <= l; k++){
							HTMP[i-1][k-1] = SCALE * HTMP[i-1][k-1];
						}
					}
				}
				h = D[i-1];
				D[i-1] = HTMP[i-1][i-1];
				HTMP[i-1][i-1] = h;
			}
			//CALL TQLRAT
			/**
			 * TQLRAT
			 * TO FIND  THE  EIGENVALUES  OF A  SYMMETRIC  TRIDIAGONAL MATRIX.
			 */
			//COMPUTE RELATIVE MACHINE PRECISION.
			double EPS = 1;
			double D1MACH;
			double TOL;
			do{
				D1MACH = EPS;
				EPS /= 2;
				TOL = 1 + EPS;
			}while(TOL > 1);
			double MACHEP = D1MACH;
			for(int i = 2; i <= N; i++){
				E2[i-2] = E[i-1];
			}
			double F = 0;
			double B = 0;
			E2[N-1] = 0;
			int l = 0;
			while(l < N){
				int IFLAG = 0;
				l += 1;
				int i = 0;
				int j = 0;
				double h = MACHEP * (Math.abs(D[l-1]) + Math.sqrt(E2[l-1]));
				double C = 0;
				if(B <= h){
					B = h;
					C = B * B;
				}
				//LOOK FOR SMALL SQUARED SUB-DIAGONAL ELEMENT.
				int M = l - 1;
				while(E2[M-1] > C && M < N)
					M++;
				if(M != l){
					while(Math.abs(E2[l-1]) < 1.0e-10){
						j++;
						//FORM SHIFT.
						int l1 = l + 1;
						double S = Math.sqrt(E2[l-1]);
						double g = D[l-1];
						double P = (D[l1] -g) / (2 * S);
						double R = Math.sqrt(P * P + 1);
						D[l-1] = S / (P + Math.copySign(R, P));
						h = g - D[l-1];
						for(i = l1; i <= N; i++)
							D[i-1] -= h;
						F += h;
						//RATIONAL QL TRANSFORMATION.
						g = D[M-1];
						if(Math.abs(g) < 1.0e-10)
							g = B;
						h = g;
						S = 0;
						int MML = M - l;
						for(int ii = 1; ii <= MML; ii++){
							i = M - ii;
							P = g * h;
							R = P + E2[i-1];
							E2[i] = S * R;
							S = E2[i-1] / R;
							D[i] = h + S * (h + D[i-1]);
							g = D[i-1] -E2[i-1] / g;
							if(Math.abs(g) < 1.0e-10)
								g = B;
							h = g * P / R;
						}
						E2[l-1] = S * g;
						D[l-1] = h;
						//GUARD AGAINST UNDERFLOW IN CONVERGENCE TEST.
						if(Math.abs(h) < 1.0e-10)
							if(Math.abs(E2[l-1]) > Math.abs(C/h))
								E2[l-1] = h * E2[l-1];
					}
				}
				double P = D[l-1] + F;
				//ORDER EIGENVALUES.
				if(l != 1)
					for(int ii = 2; ii <= l; ii++){
						if(IFLAG == 0){
							i = l + 2 - ii;
							if(P < D[i-2])
								D[i-1] = D[i-2];
							else
								IFLAG = 1;
						}
					}
				if(IFLAG == 0)
					i = 1;
				D[i-1] = P;
			}
			//END TQLRAT
			//END EGNVAL
			//CONTINUE SLAVHS
			/**
			 * WHEN THE HESSIAN IS NOT POSITIVE DEFINITE, REPLACE THE DIAGONAL
			 * ELEMENTS OF THE  HESSIAN WITH THE  HESSIAN  VALUE + EMIN.  THIS
			 * FORCES THE HESSIAN TO BE POSITIVE DEFINITE.
			 */
			if(Math.abs(D[0]) < 1.0e-10){
				double EMIN = Math.abs(D[0]) + D[N-1] * 0.0001;
				for(int i = 1; i <= N; i++){
					H[i-1][i-1] += EMIN;
				}
			}
			//END SLAVHS
			/**
			 * FACTOR
			 * TO COMPUTE THE UPPER TRIANGULAR FACTORS OF A SYMMETRIC POSITIVE
			 * DEFINITE MATRIX.
			 */
			//STORE THE SYMMETRIC POSITIVE DEFINITE MATRIX-H IN HTMP.
			for(int i = 1; i <= N; i++)
				for(int j = 1; j <= N; j++)
					HTMP[i-1][j-1] = H[i-1][j-1];
			/**
			 * COMPUTE THE UPPER TRIANGULAR FACTORS OF MATRIX-H AND  STORE  IN
			 * THE UPPER TRIANGULAR PORTION OF MATRIX-U.
			 */
			double[][] U = new double[3][3];
			for(int i = N; i >=2; i--){
				U[i-1][i-1] = Math.sqrt(HTMP[i-1][i-1]);
				ALPHA = 1 / U[i-1][i-1];
				for(int j = 1; j <= i-1; j++){
					U[j-1][i-1] = ALPHA * HTMP[j-1][i-1];
					double beta = U[j-1][i-1];
					for(int k = 1; k <= j; k++)
						HTMP[k-1][j-1] -= beta * U[k-1][i-1];
				}
			}
			//COMPUTE THE  UPPER  TRIANGULAR  FACTOR OF  THE FIRST ELEMENT OF MATRIX-H.
			U[0][0] = Math.sqrt(HTMP[0][0]);
			//END FACTOR
			/**
			 * SOLVE
			 * TO SOLVE THE SYSTEM OF EQUATIONS HX = Y FOR X.
			 */
			//SOLVE THE SYSTEM OF EQUATIONS HX = Y  FOR  X  USING  THE  UPPER
			//TRIANGULAR FACTORS-U OF MATRIX-H.
			for(int i = N; i >= 1; i--){
				X[i-1] = G[i-1];
				if(i != N)
					for(int j = i+1; j <= N; j++)
						X[i-1] -= U[i-1][j-1] * X[j-1];
				X[i-1] /= U[i-1][i-1];
			}
			for(int i = 1; i <= N; i++){
				if(i != 1)
					for(int j = 1; j <= i-1; j++)
						X[i] -= U[j-1][i-1] * X[j-1];
				X[i-1] /= U[i-1][i-1];
			}
			//END SOLVE
			//CONTINUE SLAVMD
			//COMPUTE THE DIAGONALS OF H INVERSE.
			for(int i = 1; i <= N; i++){
				for(int j = 1; j <= N; j++){
					Y[j-1] = 0;
				}
				Y[i-1]	= 1;
				/**
				 * SOLVE
				 * TO SOLVE THE SYSTEM OF EQUATIONS HX = Y FOR X.
				 */
				//SOLVE THE SYSTEM OF EQUATIONS HX = Y  FOR  X  USING  THE  UPPER
				//TRIANGULAR FACTORS-U OF MATRIX-H.
				for(int I = N; I >= 1; I--){
					X[I-1] = Y[I-1];
					if(I != N)
						for(int J = I+1; J <= N; J++)
							X[I-1] -= U[I-1][J-1] * X[J-1];
					X[I-1] /= U[I-1][I-1];
				}
				for(int I = 1; I <= N; I++){
					if(I != 1)
						for(int J = 1; J <= I-1; J++)
							X[I] -= U[J-1][I-1] * X[J-1];
					X[I-1] /= U[I-1][I-1];
				}
				for(int I = 1; I <= N; I++){
					
				}
				//END SOLVE
			}
		}
	}
}
