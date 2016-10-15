package edu.bme.mit.kpt7g6.NeuralNetwork.Structure;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import edu.bme.mit.kpt7g6.NeuralNetwork.ActivationFunctions.ActivationFunction;

public class InputLayer implements Ilayer {
	private RealVector inputValues;
	private int numberOfInputs;
	
	public InputLayer(int numberOfInputs) {
		this.numberOfInputs = numberOfInputs;
		inputValues = new ArrayRealVector(numberOfInputs);
	}

	public void setInputValues(double[] valuesFlatten){
		inputValues = new ArrayRealVector(valuesFlatten);
	}
	
	public RealVector getOutput() {
		return inputValues;
	}

	@Override
	public int getNumerOfNuerons() {
		return numberOfInputs;
	}

	@Override
	public void setActivationFunction(ActivationFunction newActivationFunction) {
		
	}
}
