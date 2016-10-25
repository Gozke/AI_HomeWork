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
	private int numberOfUsefulSamples;

	
	public Main(double beta, double alfa_u, double alfa_v, RealMatrix H) {
		this.beta = beta;
		this.alfa_u = alfa_u;
		this.alfa_v = alfa_v;
		this.H = H;
		numberOfBurnInSamples = 20;
		numberOfIterations = 100;
		numberOfUsefulSamples = numberOfIterations - numberOfBurnInSamples;
	}
	
	public ApproximationResult findApproximation(int I, int J, int L){
		RealMatrix sumU = new Array2DRowRealMatrix(L, I);
		RealMatrix sumV = new Array2DRowRealMatrix(L, J);
		U = initializeByPrior(L, I, alfa_u);
		V = initializeByPrior(L, J, alfa_v);
		
		for(int iterationIndex = 0; iterationIndex < numberOfIterations+numberOfBurnInSamples; iterationIndex++){
			if(iterationIndex % 2 == 0){
				U = computeNewU();
			} else {
				V = computeNewV();
			}
			//U = newU;
			if(iterationIndex > numberOfBurnInSamples){
				sumU = sumU.add(U);
				sumV = sumV.add(V);
			}
		}
		
		return new ApproximationResult(sumU.scalarMultiply(1.0/numberOfUsefulSamples), sumV.scalarMultiply(1.0/numberOfBurnInSamples));
	}

	public RealMatrix computeNewU(){
		RealMatrix newU = new Array2DRowRealMatrix(U.getRowDimension(), U.getColumnDimension());
		for(int i = 0; i<U.getColumnDimension(); i++){
			RealMatrix lambda = computeLambdaU();
			RealMatrix lambdaInverse = new LUDecomposition(lambda).getSolver().getInverse();
			RealVector fi = computeFiU(i, lambdaInverse);
			MultivariateNormalDistribution distribution = new MultivariateNormalDistribution(fi.toArray(), lambdaInverse.getData());
			newU.setColumn(i, distribution.sample());
		}
		return newU;
	}
	
	public RealMatrix computeNewV(){
		RealMatrix newV = new Array2DRowRealMatrix(V.getRowDimension(), V.getColumnDimension());
		for(int j = 0; j<V.getColumnDimension(); j++){
			RealMatrix lambda = computeLambdaV();
			RealMatrix lambdaInverse = new LUDecomposition(lambda).getSolver().getInverse();
			RealVector fi = computeFiV(j, lambdaInverse);
			MultivariateNormalDistribution distribution = new MultivariateNormalDistribution(fi.toArray(), lambdaInverse.getData());
			newV.setColumn(j, distribution.sample());
		}
		return newV;
	}
	
	public RealMatrix computeLambdaU(){
		RealMatrix sum = new Array2DRowRealMatrix(U.getRowDimension(), U.getRowDimension()); // LxL
		for(int j = 0; j<V.getColumnDimension();j++){
			sum = sum.add(V(j).outerProduct(V(j))); // Vj * Vj^T
		}
		return sum.scalarMultiply(beta).add(Utils.createDiagonalMatrixOf(U.getRowDimension(), alfa_u));
	}
	
	public RealMatrix computeLambdaV(){
		RealMatrix sum = new Array2DRowRealMatrix(V.getRowDimension(), V.getRowDimension()); // LxL
		for(int i = 0; i<U.getColumnDimension(); i++){
			sum = sum.add(U(i).outerProduct(U(i)));
		}
		return sum.scalarMultiply(beta).add(Utils.createDiagonalMatrixOf(V.getRowDimension(), alfa_v));
	}
	
	public RealVector computeFiU(int i, RealMatrix lambdaInverse){
		RealVector sum = new ArrayRealVector(V.getRowDimension());
		for(int j = 0; j<V.getColumnDimension(); j++){
			sum = sum.add(V(j).mapMultiply(H(i,j))); // H_ij * Vj
		}
		return lambdaInverse.operate(sum.mapMultiply(beta));
	}
	
	public RealVector computeFiV(int j, RealMatrix lambdaInverse){
		RealVector sum = new ArrayRealVector(U.getRowDimension());
		for(int i = 0; i<U.getColumnDimension(); i++){
			sum = sum.add(U(i).mapMultiply(H(i,j)));
		}
		return lambdaInverse.operate(sum.mapMultiply(beta));
	}
	public RealVector U(int colIndex){
		return U.getColumnVector(colIndex);
	}
	
	public RealVector V(int colIndex){
		return V.getColumnVector(colIndex);
	}
	
	public double H(int row, int col){
		return H.getEntry(row, col);
	}
	
	public RealMatrix initializeByPrior(int numberOfRows, int numberOfColumns, double alpha){
		return new Array2DRowRealMatrix(numberOfRows, numberOfColumns);
	}
	
	public static class ApproximationResult{
		public final RealMatrix U;
		public final RealMatrix V;
		
		public ApproximationResult(RealMatrix U, RealMatrix V){
			this.U = U.copy();
			this.V = V.copy();
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader stdInReader = new BufferedReader(new InputStreamReader(System.in));
		double[] vectorDimensionsAndBeta = Utils.toDoubleArray(stdInReader.readLine(), ",");
		int I = (int) vectorDimensionsAndBeta[0];
		int J = (int) vectorDimensionsAndBeta[1];
		int L = (int) vectorDimensionsAndBeta[2];
		double beta = vectorDimensionsAndBeta[3];
		double alfa_u = 1;
		double alfa_v = 1;
		RealMatrix H = Utils.readMatrix(stdInReader, I, J);
		Main sampler = new Main(beta, alfa_u, alfa_v, H);
		ApproximationResult appResult = sampler.findApproximation(I, J, L);
				
		System.out.println(Utils.MATRIX_FORMATTER.format(appResult.U));
		System.out.println();
		System.out.println(Utils.MATRIX_FORMATTER.format(appResult.V));
		
		
		System.out.println();
		RealMatrix HKalap = appResult.U.transpose().multiply(appResult.V);
		System.out.println(Utils.MATRIX_FORMATTER.format(HKalap));
		
		/*
		RealMatrix U = new Array2DRowRealMatrix(2,5);
		U.setRow(0, new double[]{0.25,2.65,1.61,7.63,2.88});
		U.setRow(1, new double[]{3.79,2.31,5.40,3.88,6.00});
		
		RealMatrix V = new Array2DRowRealMatrix(2,6);
		V.setRow(0, new double[]{1.68,5.91,3.38,3.24,0.57,2.61});
		V.setRow(1, new double[]{2.37,7.10,5.43,0.05,0.01,3.14});
		System.out.println(Utils.MATRIX_FORMATTER.format(U.transpose().multiply(V)));*/
	}
	
}


