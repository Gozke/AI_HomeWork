package edu.bme.mit.kpt7g6.NeuralNetwork.ActivationFunctions;

public class ReLuActivationFunction extends ActivationFunction {
	public static final ReLuActivationFunction INSTANCE = new ReLuActivationFunction();

	/**
	 * Returns 0 if value is negative value otherwise.
	 */
	@Override
	public double processValue(double value) {
		return Math.max(0, value);
	}

	protected static class DeriviatedReLuActivationFunction extends ActivationFunction {

		/**
		 * Returns 1 if value >= 1, 0 otherwise
		 */
		@Override
		public double processValue(double value) {
			return value > 0 ? 1 : 0;
		}

	}

}