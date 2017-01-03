package dcc.evaluation.computation.model;

public class LVdemo {
	
	public static void main(String[] args) {
		LVdemo demo = new LVdemo();
		double[] DAT = new double[]{ 10, 8, 14, 17, 15, 22, 19, 27, 35, 40 };
		//SLAVMD(DAT, ESF, MAXIC, NS, N, PHIIND, BETA)
		double[] BETA = new double[2];
		double ALPHA = demo.SLAVMD(DAT, 0, 100000, DAT.length, 3, 1, BETA);
		System.out.println("The LittleWood-Verrall Model:");
		System.out.println("ALPHA = " + ALPHA);
		System.out.println("BETA1 = " + BETA[0]);
		System.out.println("BETA2 = " + BETA[1]);
	}
	
	public LVdemo() {
		
	}
	
	public double SLAVMD(double[] DAT, int ESF, int MAXIC, int NS, int N, int PHIIND, double[] BETA){
		//YIELD
		double ALPHA = 0;
		int COUNT;
		int RFLAG;
		double[] X = new double[2];//BETA0, BETA1
		double Z;
		
		//LOCAL GLOSSARY
		double[] XP = new double[3];
		double EPSD;
		double EPSG;
		double[] XMAX = new double[3];
		double[] XMIN = new double[3];
		double TMP;
		double SM1 = 0;
		double SM2 = 0;
		double SM3 = 0;
		double SM4 = 0;
		double PHI;
		double FX;
		double[] G = new double[3];
		double[][] H = new double[3][3];
		double[] Y = new double[3];
		double[][] U;
		double[] NEWSTP;
		double[] S = new double[3];
		double R;
		double RTMP = 0;
		int IRED;
		double FY = 0;
		double FUNUP = 0;
		double TMPMI;
		double TMPMX;
		double[] TMIN = new double[3];
		double[] TMAX = new double[3];
		double GPROD;
		double RNEW = 0;
		double FACT;
		double TAU;
		double NDIFF;
		double NGRAD;
		double HPROD;
		double ERR;
		
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
			FX = SLAVFN(DAT, ESF, N, NS, PHIIND, XP);
			G = SLAVGD(DAT, N, NS, PHIIND, XP);
			H = SLAVHS(DAT, N, NS, PHIIND, XP);
			//COMPUTE THE NEWTON STEP.
			U = FACTOR(H, N);
			NEWSTP = SOLVE(N, U, G);
			//COMPUTE THE DIAGONALS OF H INVERSE.
			for(int i = 1; i <= N; i++){
				for(int j = 1; j <= N; j++){
					Y[j-1] = 0;
				}
				Y[i-1]	= 1;
				Y = SOLVE(N, U, Y);
				S[i-1] = Math.sqrt(Y[i-1]);
			}
			R = 0;
			for(int i = 1; i <= N; i++){
				RTMP = Math.abs(NEWSTP[i-1] / S[i-1]);
				R = Math.max(R, RTMP);
			}
			R *= 0.5;
			while(RFLAG == -1){
				IRED = 0;
				if(COUNT != 1)
					H = SLAVHS(DAT, N, NS, PHIIND, XP);
				/**
				 * COMPUTE  THE  MINIMUM  WITHIN THE  TRUST  REGION OF THE
				 * QUADRATIC MODEL.
				 */
				do{
					//SET UP THE TRUST REGION BOUNDARIES.
					for(int i = 1; i <= N; i++){
						TMPMI = XP[i-1] -R * S[i-1];
						TMPMX = XP[i-1] + R * S[i-1];
						TMIN[i-1] = Math.max(XMIN[i-1], TMPMI);
						TMAX[i-1] = Math.min(XMAX[i-1], TMPMX);
					}
					//COMPUTE THE MINIMUM WITHIN THE TRUST REGION.
					Y = CONMIN(G, H, N, TMAX, TMIN, XP);
					FY = SLAVFN(DAT, ESF, N, NS, PHIIND, Y);
					//CHECK WHETHER  THE FUNCTION HAS  DECREASED  SUFFICIENTLY.
					GPROD = 0;
					for(int i = 1; i <= N; i++)
						GPROD += G[i-1] * (Y[i-1] - XP[i-1]);
					FUNUP = FX + 0.0001 * GPROD;
					if(FY > FUNUP){
						//REDUCE THE TRUST REGION AND TRY AGAIN.
						IRED = IRED + 1;
						if(Math.abs(IRED-1) < 1.0e-30){
							/**
							 *  MAKE SURE THE FIRST REDUCTION  IS ENOUGH TO
							 *  CAUSE A  CHANGE IN THE CONSTRAINED MINIMUM.
							 */
							RNEW = R;
							for(int i = 1; i <= N; i++){
								FACT = Math.abs(XP[i-1] - Y[i-1]);
								if(Math.abs(FACT) > 1.0e-30)
									if(FACT < (RNEW * S[i-1]))
										RNEW = FACT / S[i-1];
							}
							RTMP = R;
							if(RNEW < R)
								R = 0.9 * RNEW;
						}
						//REDUCE THE TRUST REGION RADIUS.
						if((Math.abs(IRED-1)<1.0e-30 && RNEW>=RTMP) ||
								(IRED>1 && IRED<=10)){
							TAU = -1 * GPROD / (2 * (FY - FX - GPROD));
							FACT = TAU;
							if(TAU <= 0.1)
								FACT = 0.1;
							if(TAU > 0.5)
								FACT = 0.5;
							R *= FACT;
						}
					}
				}while(IRED <= 10 && FY > FUNUP);
				//SET RFLAG IF TRUST REGION COULD NOT BE ADJUSTED PROPERLY.
				if(IRED > 10)
					RFLAG = 2;
				else{
					/**
					 * AN ACCEPTABLE POINT, Y HAS BEEN FOUND. SET RFLAG IF
					 * MAXIMUM ITERATIONS WAS REACHED.
					 */
					COUNT++;
					if(COUNT > MAXIC)
						RFLAG = 1;
					else{
						//EVALUATE THE GRADIENT AT THE NEW POINT.
						G = SLAVGD(DAT, N, NS, PHIIND, Y);
						/**
						 * TO  TEST FOR CONVERGENCE,  COMPUTE THE  NORM OF
						 * THE GRADIENT AND THE NORM OF THE DIFFERENCE BE-
						 * TWEEN THIS POINT AND THE PREVIOUS POINT.
						 */
						NDIFF = 0;
						NGRAD = 0;
						for(int i = 1; i <= N; i++){
							NDIFF += Math.pow((XP[i-1] - Y[i-1]), 2);
							NGRAD += Math.pow(G[i-1], 2);
						}
						NDIFF = Math.sqrt(NDIFF);
						NGRAD = Math.sqrt(NGRAD);
						//SET RFLAG IF CONVERGENCE OCCURRED.
						if(NDIFF < EPSD || NGRAD < EPSG)
							RFLAG = 0;
						else{
							//ADJUST THE TRUST REGION.
							HPROD = 0;
							for(int i = 1; i <= N; i++)
								for(int j = 1; j <= N; j++)
									HPROD += H[i-1][j-1] * (Y[i-1] - XP[i-1]) * (Y[j-1]- XP[j-1]);
							ERR = Math.abs(FX + GPROD + 0.5 * HPROD - FY);
							if(ERR <= (0.1 * Math.abs(FY)))
								R *= 2;
							if(ERR >= (0.75 * Math.abs(FY)))
								R *= 0.5;
							//SWAP POINTS AND FUNCTION VALUES.
							for(int i = 1; i <= N; i++)
								XP[i-1] = Y[i-1];
							FX = FY;
						}
					}
				}
			}
			//SET THE RESULTANT STATISTICS.
			ALPHA = XP[0];
			X[0] = XP[1];
			X[1] = XP[2];
			BETA[0] = XP[1];
			BETA[1] = XP[2];
		}
		//IF MAXIMUM LIKELIHOOD  OR  LEAST SQUARES, COMPUTE THE MINIMIZED FUNCTION VALUE.
		if(ESF < 3)
			Z = SLAVFN(DAT, ESF, N, NS, PHIIND, XP);
		/**
		 * RESET THE ERROR FLAG IF THE ESTIMATES DO NOT ALLOW FOR THE PRE-
		 * DICTIONS TO BE MADE.
		 */
		if(Math.abs(RFLAG) < 1.0e-30 && (X[0] + X[1]) < 0)
			RFLAG = 4;
		return ALPHA;
	}
	
	
	/**
	 * SOBROUTINE SLAVFN
	 * TO CALCULATE EITHER THE  LIKELIHOOD FUNCTION FOR X IF ESF=1, OR
	 * THE SUMS-OF-SQUARES IF ESF=2,  FOR THE  LITTLEWOOD AND  VERRALL
	 * BAYESIAN RELIABILITY GROWTH MODEL.
	 */
	private double SLAVFN(double DAT[], int ESF, int N,  int NS, int PHIIND, double[] X){
		double FX;
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
			double PHI = X[1] + X[2] * Math.pow(i, PHIIND);
			if(ESF == 1)
				FX -= X[0] * Math.log(PHI) + (X[0]+1) * Math.log(DAT[i-1]+PHI);
			else
				FX += Math.pow((DAT[i-1] - PHI / (X[0]-1)), 2);
		}
		return FX;
		//END SLAVFN
	}
	
	
	/**
	 * SLAVGD
	 * TO CALCULATE THE GRADIENT OF THE LIKELIHOOD FUNCTION FOR  X FOR
	 * THE  LITTLEWOOD AND VERRALL BAYESIAN  RELIABILITY GROWTH MODEL.
	 */
	private double[] SLAVGD(double DAT[], int N,  int NS, int PHIIND, double[] X){
		double[] G = new double[3];
		//INITIALIZE THE GRADIENT VALUES.
		G[0] = -1 * NS / X[0];
		G[1] = 0;
		G[2] = 0;
		//CALCULATE THE GRADIENT VALUES FOR THE  MAXIMUM LIKELIHOOD FUNCON
		for(int i = 1; i <= NS; i++){
			double EX = Math.pow(i, PHIIND);
			double PHI = X[1] + X[2] * EX;
			double TMP = DAT[i-1] + PHI;
			G[0] -= Math.log(PHI / TMP);
			G[1] -= (X[0] / PHI) + (X[0] + 1) / TMP;
			PHI = X[0] / PHI;
			TMP = (X[0] + 1) / TMP;
			G[2] -= (PHI - TMP) * EX;
		}
		return G;
		//END SVALGD
	}
	
	
	
	/**
	 * SLAVHS
	 * TO CALCULATE THE  HESSIAN OF THE  LIKELIHOOD FUNCTION FOR X FOR
	 * THE  LITTLEWOOD AND VERRALL BAYESIAN RELIABILITY GROWTH  MODEL.
	 */
	private double[][] SLAVHS(double DAT[], int N,  int NS, int PHIIND, double[] X) {
		double[][] H = new double[3][3];
		//INITIALIZE THE HESSIAN VALUES.
		H[0][0] = NS / Math.pow(X[0], 2);
		H[0][1] = 0;
		H[0][2] = 0;
		H[1][1] = 0;
		H[1][2] = 0;
		H[2][2] = 0;
		//CALCULATE THE HESSIAN VALUES FOR THE  MAXIMUM  LIKELIHOOD FUNCON
		for(int i = 1; i <= NS; i++){
			double EX = Math.pow(i, PHIIND);
			double PHI = X[1] + X[2] * EX;
			double PHINV = 1 / PHI;
			double TMP = 1 / (PHI + DAT[i-1]);
			H[0][1] -= (PHINV - TMP);
			H[0][2] -= (PHINV - TMP) * EX;
			H[1][1] += X[0] * Math.pow(PHINV, 2) - (X[0]+1)*Math.pow(TMP, 2);
			H[1][2] += X[0] * Math.pow(PHINV, 2) * EX - (X[0]+1)*Math.pow(TMP, 2) * EX;
			H[2][2] += X[0] * Math.pow(PHINV, 2) - (X[0]+1)*Math.pow(TMP, 2) * Math.pow(EX, 2);
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
		double[] D;
		double[] DUM1 = new double[3];
		double[]DUM2 = new double[3];
		D = EGNVAL(N, N, HTMP, DUM1, DUM2);
		/**
		 * WHEN THE HESSIAN IS NOT POSITIVE DEFINITE, REPLACE THE DIAGONAL
		 * ELEMENTS OF THE  HESSIAN WITH THE  HESSIAN  VALUE + EMIN.  THIS
		 * FORCES THE HESSIAN TO BE POSITIVE DEFINITE.
		 */
		if(D[0] <= 1.0e-10){
			double EMIN = Math.abs(D[0]) + D[N-1] * 0.0001;
			for(int i = 1; i <= N; i++){
				H[i-1][i-1] += EMIN;
			}
		}
		//END SLAVHS
		return H;
	}
	
	
	/**
	 * FACTOR
	 * TO COMPUTE THE UPPER TRIANGULAR FACTORS OF A SYMMETRIC POSITIVE
	 * DEFINITE MATRIX.
	 */
	private double[][] FACTOR(double[][] H, int N) {
		double[][] HTMP = new double[3][3];
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
			double ALPHA = 1 / U[i-1][i-1];
			for(int j = 1; j <= i-1; j++){
				U[j-1][i-1] = ALPHA * HTMP[j-1][i-1];
				double beta = U[j-1][i-1];
				for(int k = 1; k <= j; k++)
					HTMP[k-1][j-1] -= beta * U[k-1][i-1];
			}
		}
		//COMPUTE THE  UPPER  TRIANGULAR  FACTOR OF  THE FIRST ELEMENT OF MATRIX-H.
		U[0][0] = Math.sqrt(HTMP[0][0]);
		return U;
		//END FACTOR
	}
	
	
	/**
	 * SOLVE
	 * TO SOLVE THE SYSTEM OF EQUATIONS HX = Y FOR X.
	 */
	private double[] SOLVE(int N, double[][] U, double[] Y) {
		double[] X = new double[3];
		//SOLVE THE SYSTEM OF EQUATIONS HX = Y  FOR  X  USING  THE  UPPER
		//TRIANGULAR FACTORS-U OF MATRIX-H.
		for(int i = N; i >= 1; i--){
			X[i-1] = Y[i-1];
			if(i != N)
				for(int j = i+1; j <= N; j++)
					X[i-1] -= U[i-1][j-1] * X[j-1];
			X[i-1] /= U[i-1][i-1];
		}
		for(int i = 1; i <= N; i++){
			if(i != 1)
				for(int j = 1; j <= i-1; j++)
					X[i-1] -= U[j-1][i-1] * X[j-1];
			X[i-1] /= U[i-1][i-1];
		}
		//END SOLVE
		return X;
	}
	
	
	/**
	 * EGNVAL
	 * TO CALCULATE  THE  EIGENVALUES  OF  A  REAL  SYMMETRIC  MATRIX.
	 */
	private double[] EGNVAL(int NM, int N, double[][] A, double[] FV1, double[] FV2) {
		double[] W = new double[3];
		TRED1(NM, N, A, W, FV1, FV2);
		//CALL TQLRAT
		TQLRAT(N, FV2, W);
		return W;
		//END EGNVAL
	}
	
	
	/**
	 * TRED1
	 * TO REDUCE A REAL SYMMETRIC  MATRIX TO A SYMMETRICAL TRIDIAGONAL
	 * MATRIX
	 */
	private void TRED1(int NM, int N, double[][] A, double[] D, double[] E, double[] E2) {
		for(int i = 0; i < N; i++)
			D[i] = A[i][i];
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
					SCALE += Math.abs(A[i-1][k-1]);
				}
				if(Math.abs(SCALE) < 1.0e-30){
					E[i-1] = 0;
					E2[i-1] = 0;
				}
				else{
					for(int k = 1; k <= l; k++){
						A[i-1][k-1] /= SCALE;
						h += A[i-1][k-1] * A[i-1][k-1];
					}
					E2[i-1] = Math.pow(SCALE, 2) * h;
					double F = A[i-1][l-1];
					double G = Math.copySign(Math.sqrt(h), F);
					E[i-1] = SCALE * G;
					h -= F * G;
					A[i-1][l-1] = F - G;
					if(l != 1){
						F = 0;
						for(int j = 1; j <= l; j++){
							G = 0;
							//FORM ELEMENT OF A*U.
							for(int k = 1; k <= j; k++){
								G += A[j-1][k-1] * A[i-1][k-1];
							}
							int jp1 = j + 1;
							if(l >= jp1)
								for(int k = jp1; k <= l; k++)
									G += A[k-1][j-1] * A[i-1][k-1];
							//FORM ELEMENT OF P.
							E[j-1] = G / h;
							F += E[j-1] * A[i-1][j-1];
						}
						h = F / (h + h);
						//FORM REDUCED A.
						for(int j = 1; j <= l; j++){
							F = A[i-1][j-1];
							G = E[j-1] - h * F;
							E[j-1] = G;
							for(int k = 1; k <= j; k++){
								A[j-1][k-1] -= F * E[k-1] - G * A[i-1][k-1];
							}
						}				
					}
					for(int k = 1; k <= l; k++){
						A[i-1][k-1] = SCALE * A[i-1][k-1];
					}
				}
			}
			h = D[i-1];
			D[i-1] = A[i-1][i-1];
			A[i-1][i-1] = h;
		}
	}
	
	
	/**
	 * TQLRAT
	 * TO FIND  THE  EIGENVALUES  OF A  SYMMETRIC  TRIDIAGONAL MATRIX.
	 */
	private void TQLRAT(int N, double[] E2, double[] D) {
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
			E2[i-2] = E2[i-1];
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
			do{
				M++;
			}while(E2[M-1] > C && M < N);
			if(M != l){
				while(Math.abs(E2[l-1]) > 1.0e-30){
					j++;
					//FORM SHIFT.
					int l1 = l + 1;
					double S = Math.sqrt(E2[l-1]);
					double g = D[l-1];
					double P = (D[l1-1] -g) / (2 * S);
					double R = Math.sqrt(P * P + 1);
					D[l-1] = S / (P + Math.copySign(R, P));
					h = g - D[l-1];
					for(i = l1; i <= N; i++)
						D[i-1] -= h;
					F += h;
					//RATIONAL QL TRANSFORMATION.
					g = D[M-1];
					if(Math.abs(g) < 1.0e-30)
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
						if(Math.abs(g) < 1.0e-30)
							g = B;
						h = g * P / R;
					}
					E2[l-1] = S * g;
					D[l-1] = h;
					//GUARD AGAINST UNDERFLOW IN CONVERGENCE TEST.
					if(Math.abs(h) > 1.0e-30)
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
	}
	
	
	/**
	 * CONMIN
	 * TO FIND THE CONSTRAINED MINIMUM OF A  QUADRATIC FUNCTION WITHIN
	 * A TRUST REGION.
	 */
	private double[] CONMIN(double[] G, double[][] H, int N, double[] TMAX, double[] TMIN, double[] X) {
		int[] ICONS = new int[3];
		double[] Y = new double[3];
		int IRELES;
		int IFLAG;
		double[] XTMP = new double[3];
		double[] GQ = new double[3];
		//INITIALIZE THE CONSTRAINT VECTOR  AND  SET THE CURRENT POINT TO POINT X.
		for (int i = 1; i <= N; i++) {
			ICONS[i-1] = 0;
			Y[i-1] = X[i-1];
		}
		//INITIALIZE THE CONSTRAINT RELEASE FLAG.
		IRELES = 1;
		/**
		 * 死循环
		 * 20170102
		 */
		while(IRELES == 1){
			//INITIALIZE THE CONSTRAINT STATUS FLAG.
			IFLAG = 1;
			while(IFLAG == 1){
				//SAVE THE CURRENT CONSTRAINED POINT.
				for (int i = 1; i <= N; i++) {
					XTMP[i-1] = Y[i-1];
				}
				//FIND THE  CONSTRAINED MINIMUM ON THE SURFACE DEFINED BY THE CONSTRAINT VECTOR.
				CONSOP(G, H, ICONS, N, TMAX, TMIN, X, Y);
				//CHECK TO SEE IF CONSTRAINTS  ARE  VIOLATED BY THIS  NEW MINIMUM.
				IFLAG = CONSTR(N, TMAX, TMIN, XTMP, Y, ICONS);
			}
			//COMPUTE THE GRADIENT OF THE QUADRATIC FUNCTION.
			for (int i = 1; i <= N; i++) {
				GQ[i-1] = G[i-1];
				for(int j = 1; j <= N; j++)
					GQ[i-1] += H[i-1][j-1] * (Y[j-1] - X[j-1]);
			}
			//CHECK TO SEE IF CONSTRAINTS CAN BE RELEASED.
			IRELES = RELESE(GQ, N, ICONS);
		}
		return Y;
	}
	
	private void CONSOP(double[] G, double[][] H, int[] ICONS, int N, double[] TMAX, double[] TMIN,
			double[]X, double[] Y) {
		int IFLAG;
		double[] TMP1 = new double[3];
		double[][] U = new double[3][3];
		double[][] HTMP = new double[3][3];
		double[] DXCONS = new double[3];
		double[] TMP2 = new double[3];
		double[] TMP3 = new double[3];
		//DETERMINE IF ANY CONSTRAINTS EXIST.
		IFLAG = 0;
		for (int i = 1; i <= N; i++) {
			if(ICONS[i-1] != 0){
				IFLAG = 1;
			}
		}
		if(IFLAG == 0){
			//CONSTRAINTS DO NOT EXIST.  SET  MINIMUM  OF  THE  QUADRATIC
			//FUNCTION TO THE COMPUTED NEWTON POINT.<<SEE RESTRICTIONS <<
			U = FACTOR(H, N);
			TMP1 = SOLVE(N, U, G);
			for (int i = 1; i <= N; i++) 
				Y[i-1] = X[i-1] - TMP1[i-1];
		}else{
			//CONSTRAINTS DO EXIST. STORE  THE HESSIAN IN AN INTERMEDIATE MATRIX.
			for (int i = 1; i <= N; i++)
				for (int j = 1; j <= N; j++)
					HTMP[i-1][j-1] = H[i-1][j-1];
			//DETERMINE THE STEP TO THE BOUNDARY.
			for (int i = 1; i <= N; i++)
				if(ICONS[i-1] == 0)
					DXCONS[i-1] = 0;
				else
					DXCONS[i-1] = Y[i-1] - X[i-1];
			//COMPUTE THE PRODUCT OF THE HESSIAN AND THE STEP VECTOR.
			for (int i = 1; i <= N; i++){
				double SM = 0;
				for (int j = 1; j <= N; j++)
					SM += H[i-1][j-1] * DXCONS[j-1];
				TMP1[i-1] = SM;
			}
			//ADJUST THE GRADIENT FOR ALL  CONSTRAINED  ELEMENTS  OF  THE CONSTRAINT VECTOR.
			for (int i = 1; i <= N; i++)
				if(ICONS[i-1] != 0)
					TMP2[i-1] = 0;
				else
					TMP2[i-1] = G[i-1] + TMP1[i-1];
			//ADJUST THE HESSIAN FOR  ALL  CONSTRAINED  ELEMENTS  OF  THE CONSTRAINT VECTOR.
			for (int i = 1; i <= N; i++)
				if(ICONS[i-1] != 0){
					for (int j = 1; j <= N; j++){
						HTMP[i-1][j-1] = 0;
						HTMP[j-1][i-1] = 0;
					}
					HTMP[i-1][i-1] = 1.0e30;
				}
			// COMPUTE THE CONSTRAINED MINIMUM OF THE QUADRATIC FUNCTION.
			U = FACTOR(HTMP, N);
			TMP3 = SOLVE(N, U, TMP2);
			for (int i = 1; i <= N; i++)
				if(ICONS[i-1] == 1)
					Y[i-1] = TMAX[i-1];
				else
					if(ICONS[i-1] == -1)
						Y[i-1] = TMIN[i-1];
					else
						Y[i-1] = X[i-1] - TMP3[i-1];
		}
	}
	
	
	/**
	 * CONSTR
	 *  TO CONSTRAIN THE POINT Y TO BE ON THE LINE FROM X  TO  Y INSIDE
	 *  OR ON THE BOUNDARIES OF THE TRUST REGION.
	 */
	private int CONSTR(int N, double[] TMAX, double[] TMIN, double[] X, double[] Y, int[] ICONS) {
		int COMPY = 0;
		double[] DELP = new double[3];
		int IFLAG;
		double TMPNEW;
		//NITIALIZE THE NEWTON POINT ADJUSTMENT.
		double NEWT = 1;
		//DETERMINE IF THE COMPONENTS OF POINT Y HAVE TO BE CONSTRAINED.
		for (int i = 1; i <= N; i++){
			DELP[i-1] = Y[i-1] - X[i-1];
			if(Math.abs(DELP[i-1]) > 1.0e-30){
				if(DELP[i-1] < 0)
					TMPNEW = (TMIN[i-1] - X[i-1]) / DELP[i-1];
				else
					TMPNEW = (TMAX[i-1] - X[i-1]) / DELP[i-1];
				if(TMPNEW <= NEWT){
					NEWT = TMPNEW;
					COMPY = i;
				}
			}
		}
		if(Math.abs(NEWT -1) > 1.0e-30){
			//SET THE CONSTRAINTS STATUS  FLAG  TO  INDICATE  CONSTRAINTS DO EXIST.
			IFLAG = 1;
			//ADJUST THE COMPONENTS  OF Y BY THE NEWTON POINT ADJUSTMENT.
			for (int i = 1; i <= N; i++)
				Y[i-1] = X[i-1] + NEWT * DELP[i-1];
			/**
			 * SET THE CONSTRAINT VECTOR FOR THE COMPONENT OF Y WHICH  HAS
			 * VIOLATED ITS TRUST REGION BOUNDARIES BY THE  GREATEST  MAR-GIN.
			 */
			if(DELP[COMPY-1] < 0)
				ICONS[COMPY-1] = -1;
			else
				ICONS[COMPY-1] = 1;
		}else{
			//SET THE CONSTRAINT STATUS FLAG TO  INDICATE  CONSTRAINTS DO NOT EXIST.
			IFLAG = 0;
		}
		return IFLAG;
	}
	
	
	private int RELESE(double[] G, int N, int[] ICONS) {
		//INITIALIZE THE CONSTRAINT RELEASE FLAG.
		int IRELES = 0;
		//DETERMINE IF ANY CONSTRAINTS CAN BE RELEASED.
		for (int i = 1; i <= N; i++)
			if((Math.abs(ICONS[i-1]+1)<1.0e-30 && G[i-1]<0) ||
					(Math.abs(ICONS[i-1]-1)<1.0e-30) && G[i-1]>0){
				ICONS[i-1] = 0;
				IRELES = 1;
			}
		return IRELES;
	}
}

