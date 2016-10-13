package edu.bme.mit.kpt7g6.NeuralNetwork.Structure;

import org.apache.commons.math3.linear.RealVector;

public interface Ilayer {

	RealVector getOutput();
	
	public int getNumerOfNuerons();

}
