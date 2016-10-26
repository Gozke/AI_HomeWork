package edu.bme.mit.kpt7g6.gibbs;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DiagonalMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealMatrixFormat;
import org.apache.commons.math3.linear.RealMatrixPreservingVisitor;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.RealVectorFormat;

public class Utils {
	public static final RealMatrixFormat MATRIX_FORMATTER = new RealMatrixFormat("", "", "", "", "\n", ",");
	public static final RealVectorFormat VECTOR_FORMATTER = new RealVectorFormat("", "", ",");
	public static final RealMatrixPreservingVisitor SQUARE_AND_SUM_VISITOR = new SquareAndAdd();
	
	public static RealMatrix readMatrix(BufferedReader reader, int noRows, int noColumns) throws IOException{
		double[][] elements = new double[noRows][noColumns];
		
		for(int rIndex = 0; rIndex < noRows; rIndex++){
			elements[rIndex] = toDoubleArray(reader.readLine(), ",");
		}
		return new Array2DRowRealMatrix(elements);
	}
	
	public static RealVector readVector(BufferedReader reader) throws IOException{
		return new ArrayRealVector(toDoubleArray(reader.readLine(), ","));
	}
	
	public static double[] toDoubleArray(String s, String delimiter){
		String[] tokens = s.split(delimiter);
		double[] res = new double[tokens.length];
		
		for(int i = 0; i < tokens.length; i++){
			res[i] = Double.parseDouble(tokens[i]);
		}
		return res;
	}
	
	/**
	 * Creates a diagonal matrix of size dim X dim that contains scaler in it's diagonal.
	 *  
	 * @param dim the dimension of the matrix to be created
	 * @param scalar the scaler value to store in the diagonal
	 * @return the created diagonal matrix
	 */
	public static RealMatrix createDiagonalMatrixOf(int dim, double scalar){
		double[] values = new double[dim];
		Arrays.fill(values, scalar);
		return new DiagonalMatrix(values);
	}
	
	public static double calculateError(RealMatrix expected, RealMatrix actual){
		RealMatrix difference = expected.subtract(actual);
		double errorSquareSum = difference.walkInOptimizedOrder(SQUARE_AND_SUM_VISITOR);
		return errorSquareSum / (difference.getRowDimension()*difference.getColumnDimension());
	}
	
	public static class SquareAndAdd implements RealMatrixPreservingVisitor {
		private double sum;
		
		@Override
		public void visit(int row, int column, double value) {
			sum += value*value;
		}
		
		@Override
		public void start(int rows, int columns, int startRow, int endRow, int startColumn, int endColumn) {
			sum = 0;
		}
		
		@Override
		public double end() {
			return sum;
		}
	}
}
