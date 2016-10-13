package edu.bme.mit.kpt7g6.NeuralNetwork.Structure;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import edu.bme.mit.kpt7g6.NeuralNetwork.ActivationFunctions.ActivationFunction;

public class Layer implements Ilayer {
	private Ilayer previousLayer;
	public final int numberOfNeurons;
	
	/**
	 * The activation function to apply to each output value 
	 */
	private final ActivationFunction activationFunction;

	/**
	 * This vector stores the biases of the neurons in this layer. The nth
	 * element is the bias value of the nth neuron.
	 */
	private RealVector biases;

	/**
	 * This matrix stores the w values for the inputs of the neurons in this
	 * layer.
	 */
	private RealMatrix weights;

	/**
	 * This method constructs a layer of neurons the neural network. The weight and bias values are initialized to 0.
	 * 
	 * @param numberOfNeurons the number of in the layer
	 * @param previousLayer reference to the previous layer of the neural network. (May not be  {@code null}.)
	 * @param activationFunction the activation function to apply to the output values. (May not be  {@code null}.)
	 */
	public Layer(int numberOfNeurons, Ilayer previousLayer, ActivationFunction activationFunction) {
		super();
		this.previousLayer = previousLayer;
		this.numberOfNeurons = numberOfNeurons;
		this.activationFunction = activationFunction;
		this.weights = new  Array2DRowRealMatrix(numberOfNeurons, previousLayer.getNumerOfNuerons());
		this.biases = new  ArrayRealVector(numberOfNeurons);
	}

	/**
	 * Sets the reference to the previous layer in the network. Called when the layer in appended to a neural network.
	 * 
	 * @param prevLayere reference to the previous layer in the network.
	 */
	public void setPreviousLayer(Ilayer prevLayere){
		previousLayer = prevLayere;
	}
	
	/**
	 * Sets the input weights of the index-th neuron.
	 * 
	 * @param index
	 *            A zero based index of the neuron in the layer.
	 * @param weights
	 *            the array of weights. The nth element in the array will be set
	 *            as the nth input weight of the neuron.
	 * @throws IllegalArgumentException If the index is >= {@link #numberOfNeurons} or the weights array contains less elements than the number of neurons in the previous layer
	 */
	public void setWeightsOfNeuron(int index, double[] weights) throws IllegalArgumentException {
		if (index >= numberOfNeurons) {
			throw new IllegalArgumentException(
					"This layer only has " + numberOfNeurons + " neurons! Only index values between 1-" + numberOfNeurons + " are exceptable");
		}
		
		this.weights.setRow(index, weights);
	}
	
	/**
	 * Sets the bias of a the index-th neuron
	 * @param index A zero based index of the neuron in the layer.
	 * @param biasValue the new bias value
	 * @throws IllegalArgumentException If the index is >= {@link #numberOfNeurons}
	 */
	public void setBiasOfNeuron(int index, double biasValue) throws IllegalArgumentException{
		if (index >= numberOfNeurons) {
			throw new IllegalArgumentException(
					"This layer only has " + numberOfNeurons + " neurons! Only index values between 1-" + numberOfNeurons + " are exceptable");
		}
		
		this.biases.setEntry(index, biasValue);
	}

	@Override
	public RealVector getOutput() {
		RealVector s = biases.add(weights.operate(previousLayer.getOutput()));
		return s.mapToSelf(activationFunction);
	}

	@Override
	public int getNumerOfNuerons() {
		return numberOfNeurons;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int neuronIndex = 0; neuronIndex<numberOfNeurons; neuronIndex++){
			for(double w : weights.getRow(neuronIndex)){
				sb.append(w).append(',');
			}
			sb.append(biases.getEntry(neuronIndex));
			if(neuronIndex < numberOfNeurons-1){
				sb.append('\n');
			}
		}
		
		return sb.toString();
	}
	
	
}
