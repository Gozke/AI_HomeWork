package edu.bme.mit.kpt7g6.NeuralNetwork.ActivationFunctions;

public class LinearActivationFunction extends ActivationFunction {
	public final static LinearActivationFunction INSTANCE = new LinearActivationFunction();
	public final static DerivedLinearActivationFunction DERIVED_INSTANCE = new DerivedLinearActivationFunction();

	@Override
	public double processValue(double value) {
		return value;
	}

	@Override
	public ActivationFunction derivative() {
		return DERIVED_INSTANCE;
	}

	public static class DerivedLinearActivationFunction extends ActivationFunction{
		@Override
		public double processValue(double value) {
			return 1;
		}
	}
}