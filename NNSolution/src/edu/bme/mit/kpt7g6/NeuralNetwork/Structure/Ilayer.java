package edu.bme.mit.kpt7g6.NeuralNetwork.Structure;

import org.apache.commons.math3.linear.RealVector;

import edu.bme.mit.kpt7g6.NeuralNetwork.ActivationFunctions.ActivationFunction;

public interface Ilayer {

	RealVector getOutput();
	
	public int getNumerOfNuerons();

	public void setActivationFunction(ActivationFunction newActivationFunction);
}
