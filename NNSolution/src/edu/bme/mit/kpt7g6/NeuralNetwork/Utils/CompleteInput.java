package edu.bme.mit.kpt7g6.NeuralNetwork.Utils;

import java.util.Arrays;
import java.util.function.Function;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

/**
 * This class represents a complete input to the neural network: a pair of input and expected value vector 
 * 
 * @author U550545
 *
 */
public class CompleteInput {
	private RealVector inputVector;
	private RealVector expectedOutput;
	
	public CompleteInput(RealVector inputVector, RealVector expectedOutput) {
		super();
		this.inputVector = inputVector;
		this.expectedOutput = expectedOutput;
	}

	public RealVector getInputVector() {
		return inputVector;
	}

	public RealVector getExpectedOutput() {
		return expectedOutput;
	}

	public static class ArrayToInputMapper implements Function<double[], CompleteInput>{
		private int numberOfInputs; 
		
		
		public ArrayToInputMapper(int numberOfInputs) {
			super();
			this.numberOfInputs = numberOfInputs;
		}

		@Override
		public CompleteInput apply(double[] values) {
			RealVector input = new ArrayRealVector(Arrays.copyOf(values, numberOfInputs));
			RealVector expectedOutput = new ArrayRealVector(Arrays.copyOfRange(values, numberOfInputs, values.length));
			return new CompleteInput(input, expectedOutput);
		}
		
	}
}
