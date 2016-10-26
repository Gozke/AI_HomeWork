package edu.bme.mit.kpt7g6.gibbs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.math3.distribution.MultivariateNormalDistribution;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class Main {
	private double beta;
	private double alfa_u;
	private double alfa_v;
	private RealMatrix U;
	private RealMatrix V;
	private RealMatrix H;
	private int numberOfBurnInSamples;
	private int numberOfIterations;

	public Main(RealMatrix H) {
		this.alfa_u = 1;
		this.alfa_v = 1;
		this.H = H;
		numberOfBurnInSamples = 1;
		numberOfIterations = 10;
	}

	public ApproximationResult findApproximation(int I, int J, int L, double beta) {
		this.beta = beta;
		RealMatrix sumU = new Array2DRowRealMatrix(L, I);
		RealMatrix sumV = new Array2DRowRealMatrix(L, J);
		U = initializeByPrior(L, I, alfa_u);
		V = initializeByPrior(L, J, alfa_v);
		
		for (int iterationIndex = 0; iterationIndex < numberOfBurnInSamples; iterationIndex++) {
			updateU();
			updateV();
		}
		
		// Separated to avoid branch prediction
		for(int i = 0; i<numberOfIterations;i++){
			updateU();
			updateV();
			sumU = sumU.add(U);
			sumV = sumV.add(V);			
		}
		
		return new ApproximationResult(
				sumU.scalarMultiply(1.0 / numberOfIterations),
				sumV.scalarMultiply(1.0 / numberOfIterations));
	}

	public void updateU() {
		for (int i = 0; i < U.getColumnDimension(); i++) {
			RealMatrix lambda = computeLambda(V, alfa_u);
			RealMatrix lambdaInverse = new LUDecomposition(lambda).getSolver().getInverse();
			RealVector fi = computeFi(H.getRowVector(i), V, lambdaInverse);
			MultivariateNormalDistribution distribution = new MultivariateNormalDistribution(fi.toArray(),
					lambdaInverse.getData());
			U.setColumn(i, distribution.sample());
		}
	}

	public void updateV() {
		for (int j = 0; j < V.getColumnDimension(); j++) {
			RealMatrix lambda = computeLambda(U, alfa_v);
			RealMatrix lambdaInverse = new LUDecomposition(lambda).getSolver().getInverse();
			RealVector fi = computeFi(H.getColumnVector(j), U, lambdaInverse);
			MultivariateNormalDistribution distribution = new MultivariateNormalDistribution(fi.toArray(),
					lambdaInverse.getData());
			V.setColumn(j, distribution.sample());
		}
	}

	public RealMatrix computeLambda(RealMatrix matrix, double alfa) {
		return matrix.multiply(matrix.transpose()).scalarMultiply(beta).add(Utils.createDiagonalMatrixOf(matrix.getRowDimension(), alfa));
	}

	public RealVector computeFi(RealVector vectorOfH, RealMatrix otherMatrix, RealMatrix lambdaInverse) {
		RealVector sum = otherMatrix.transpose().preMultiply(vectorOfH);
		return lambdaInverse.operate(sum.mapMultiplyToSelf(beta));
	}

	public RealMatrix initializeByPrior(int numberOfRows, int numberOfColumns, double alpha) {
		RealVector zeroVector = new ArrayRealVector(numberOfRows);
		RealMatrix result = new Array2DRowRealMatrix(numberOfRows, numberOfColumns);
		MultivariateNormalDistribution distr = new MultivariateNormalDistribution(zeroVector.toArray(),Utils.createDiagonalMatrixOf(numberOfRows, 1.0 / alpha).getData());
		for (int i = 0; i < numberOfColumns; i++) {
			result.setColumn(i, distr.sample());
		}
		return result;
	}

	public static class ApproximationResult {
		public final RealMatrix U;
		public final RealMatrix V;

		public ApproximationResult(RealMatrix U, RealMatrix V) {
			this.U = U;
			this.V = V;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader stdInReader = new BufferedReader(new InputStreamReader(System.in));
		double[] vectorDimensionsAndBeta = Utils.toDoubleArray(stdInReader.readLine(), ",");
		int I = (int) vectorDimensionsAndBeta[0];
		int J = (int) vectorDimensionsAndBeta[1];
		int L = (int) vectorDimensionsAndBeta[2];
		double beta = vectorDimensionsAndBeta[3];

		RealMatrix H = Utils.readMatrix(stdInReader, I, J);
		Main sampler = new Main(H);
		ApproximationResult approximation = sampler.findApproximation(I, J, L, beta);

		System.out.println(Utils.MATRIX_FORMATTER.format(approximation.U.transpose()));
		System.out.println();
		System.out.println(Utils.MATRIX_FORMATTER.format(approximation.V.transpose()));
		
		System.out.println("Hiba: " + Utils.calculateError(H,approximation.U.transpose().multiply(approximation.V)));
	}

}
