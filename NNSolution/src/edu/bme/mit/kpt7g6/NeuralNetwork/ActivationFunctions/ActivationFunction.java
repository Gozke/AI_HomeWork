package edu.bme.mit.kpt7g6.NeuralNetwork.ActivationFunctions;

import org.apache.commons.math3.analysis.UnivariateFunction;

public abstract class ActivationFunction implements UnivariateFunction{
	
	/**
	 * Applies this function to the given value.
	 * @param value value to apply function to.
	 * @return f(value)
	 */
	public abstract double processValue(double value);

	/**
	 * Returns the derivative of this function.
	 * 
	 * @return this'()
	 */
	public ActivationFunction derivative(){
		return this;
	}
	
	@Override
	public double value(double x) {
		return processValue(x);
	}
	
	
}
